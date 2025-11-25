package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Cart;
import com.ecommerce.sb_ecom.model.CartItem;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.CartDTO;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.repositories.CartItemRepository;
import com.ecommerce.sb_ecom.repositories.CartRepository;
import com.ecommerce.sb_ecom.repositories.ProductRepository;
import com.ecommerce.sb_ecom.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements  CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart =createCart();

        Product  product =productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
       CartItem cartItem= cartItemRepository.findCartItemByProductIdAndCartId(
             cart.getCartId(),
                productId

        );
         if (cartItem !=null){
             throw new APIException("Product"+product.getProducts()+"already exists in the cart");
         }
          if (product.getQuantity()==0){
              throw  new APIException(product.getProductName()+"is not available");
          }
           if (product.getQuantity() < quantity){
               throw  new APIException("Please, Make an Order of the "+product.getProductName()+" lese than or equal to the Quantity"+product.getQuantity());
           }
           CartItem newCartItem=new CartItem();

           newCartItem.setProduct(product);
           newCartItem.setCart(cart);
           newCartItem.setQuantity(quantity);
           newCartItem.setDiscount(product.getDiscount());
           newCartItem.setProductPrice(product.getSpecialPrice());

           cartItemRepository.save(newCartItem);

           product.setQuantity(product.getQuantity());
           cart.setTotalPrice(cart.getTotalPrice()+product.getSpecialPrice()*quantity);

           cartRepository.save(cart);

           CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        List<CartItem>cartItems=cart.getCartItems();
        Stream<ProductDTO>productDTOStream=cartItems.stream().map(item -> {
            ProductDTO map=modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return  map;
        });

        cartDTO.setProducts(productDTOStream.toList());

            return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart>carts=cartRepository.findAll();
        if(carts.size()==0){
            throw new APIException("No carts Exist");
        }
        List<CartDTO>cartDTOS=carts.stream().map(cart -> {
            CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
            List<ProductDTO>products=cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class))
                    .collect(Collectors.toList());

            cartDTO.setProducts(products);
            return cartDTO;


        }).collect(Collectors.toList());

        return cartDTOS;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart =cartRepository.findCartByEmailAndCartId(emailId,cartId);
        if(cart==null){
            throw  new ResourceNotFoundException("Cart","cartId",cartId);
        }
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        cart.getCartItems().forEach(c->c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO>products=cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    private  Cart createCart(){
        Cart userCart= cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null){
            return  userCart;
        }
         Cart cart =new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart=cartRepository.save(cart);
        return  newCart;
    }
}
