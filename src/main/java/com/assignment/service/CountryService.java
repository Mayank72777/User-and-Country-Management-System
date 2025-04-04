package com.assignment.service;

import com.assignment.dto.CountryDto;
import com.assignment.entity.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface CountryService {

    List<CountryDto> fetchAndStoreCountries();

    Page<CountryEntity> getAllCountries(Pageable pageable);

    CountryEntity getCountryById(Long id);

    CountryEntity saveCountry(CountryEntity country);

    CountryEntity updateCountry(Long id, CountryEntity updateCountry);

    void deleteCountry(Long id);
}
