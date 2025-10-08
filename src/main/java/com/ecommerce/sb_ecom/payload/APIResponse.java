package com.ecommerce.sb_ecom.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIResponse {
    private String message;
    private boolean status;

    // Explicit constructor
    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}