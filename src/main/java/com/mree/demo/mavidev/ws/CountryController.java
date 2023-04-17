package com.mree.demo.mavidev.ws;

import com.mree.demo.mavidev.common.model.CountryDto;
import com.mree.demo.mavidev.common.request.CountryCreateDto;
import com.mree.demo.mavidev.common.request.CountryUpdateDto;
import com.mree.demo.mavidev.common.ws.ServiceUri;
import com.mree.demo.mavidev.service.CountryService;
import com.mree.demo.mavidev.util.RequestBodyValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ServiceUri.COUNTRY)
@RequiredArgsConstructor
public class CountryController extends BaseController<CountryDto, CountryCreateDto, CountryUpdateDto, CountryService> {

    private final CountryService countryService;
    private final RequestBodyValidationHelper validationHelper;

    @Override
    public CountryService getService() {
        return countryService;
    }

    @Override
    public RequestBodyValidationHelper getValidationHelper() {
        return validationHelper;
    }
}
