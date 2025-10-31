package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(long productId, MultipartFile image) throws IOException;
}
