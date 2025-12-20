package com.ecommerce.sb_ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private  Long addressId;

    private String street;


    private  String buildingName;


    private  String city;


    private  String country;


    private  String pincode;

    private  String state;

    }



