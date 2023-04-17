package com.mree.demo.mavidev.exception;


import com.mree.demo.mavidev.common.ApiResponseCode;

public class CityAlreadyExistException extends BaseException {

    public CityAlreadyExistException() {
        super(ApiResponseCode.CITY_ALREADY_EXIST);
    }
}
