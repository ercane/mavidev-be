package com.mree.demo.mavidev.ws;

import com.mree.demo.mavidev.common.*;
import com.mree.demo.mavidev.common.ref.BaseEnum;
import com.mree.demo.mavidev.common.ws.ServiceUri;
import com.mree.demo.mavidev.service.BaseService;
import com.mree.demo.mavidev.util.RequestBodyValidationHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


public abstract class BaseController<D extends BaseDto, C extends BaseCreateDto, U extends BaseUpdateDto, S extends BaseService> {

    public abstract S getService();

    public abstract RequestBodyValidationHelper getValidationHelper();

    @GetMapping(ServiceUri.ID_PARAM)
    public ApiResponse<BaseDto> get(@PathVariable UUID id) {
        return ApiResponse.success(getService().get(id));
    }

    @GetMapping
    public ApiResponse<List<D>> getAll() {
        return ApiResponse.success(getService().getAll());
    }

    @GetMapping(ServiceUri.STATUS_PARAM)
    public ApiResponse<List<D>> getByStatus(@PathVariable String status) {
        return ApiResponse.success(getService().getByStatus(status));
    }

    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody C dto) {
        getValidationHelper().validate(dto);
        return ApiResponse.success(getService().create(dto));
    }

    @PutMapping
    public ApiResponse<Boolean> update(@RequestBody U dto) {
        getValidationHelper().validate(dto);
        return ApiResponse.success(getService().update(dto));
    }

    @DeleteMapping(ServiceUri.ID_PARAM)
    public ApiResponse delete(@PathVariable UUID id) {
        getService().delete(id);
        return ApiResponse.success(ApiResponseCode.SUCCESS.getMessage());
    }

}
