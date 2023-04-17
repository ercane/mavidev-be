package com.mree.demo.mavidev.exception;


import com.mree.demo.mavidev.common.ApiResponseCode;

public class CountryAlreadyExistException extends BaseException {

    public CountryAlreadyExistException() {
        super(ApiResponseCode.COUNTRY_ALREADY_EXIST);
    }
}
