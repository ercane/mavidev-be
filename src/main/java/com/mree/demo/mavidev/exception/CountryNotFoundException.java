package com.mree.demo.mavidev.exception;


import com.mree.demo.mavidev.common.ApiResponseCode;

public class CountryNotFoundException extends BaseException {

    public CountryNotFoundException() {
        super(ApiResponseCode.COUNTRY_NOT_FOUND);
    }
}
