package com.mree.demo.mavidev.ws;

import com.mree.demo.mavidev.common.ApiResponse;
import com.mree.demo.mavidev.common.model.CityDto;
import com.mree.demo.mavidev.common.request.CityCreateDto;
import com.mree.demo.mavidev.common.request.CityUpdateDto;
import com.mree.demo.mavidev.common.ws.ServiceUri;
import com.mree.demo.mavidev.service.CityService;
import com.mree.demo.mavidev.util.RequestBodyValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ServiceUri.CITY)
@RequiredArgsConstructor
public class CityController extends BaseController<CityDto, CityCreateDto, CityUpdateDto, CityService> {

    private final CityService cityService;
    private final RequestBodyValidationHelper validationHelper;

    @Override
    public CityService getService() {
        return cityService;
    }

    @Override
    public RequestBodyValidationHelper getValidationHelper() {
        return validationHelper;
    }

    @GetMapping(ServiceUri.COUNTRY_ID_PARAM)
    public ApiResponse<List<CityDto>> getByCountryId(@PathVariable UUID countryId) {
        return ApiResponse.success(cityService.getByCountryId(countryId));
    }
}
