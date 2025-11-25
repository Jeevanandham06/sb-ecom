package com.ecommerce.sb_ecom.repositories;

import com.ecommerce.sb_ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
   @Query("SELECT ci FROM CartItem ci where ci.cart.id=?1 AND ci.product.id=?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId,Long productId);
}
