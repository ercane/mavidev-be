package com.mree.demo.mavidev.exception;


import com.mree.demo.mavidev.common.ApiResponseCode;

public class CityNotFoundException extends BaseException {

    public CityNotFoundException() {
        super(ApiResponseCode.CITY_NOT_FOUND);
    }
}
