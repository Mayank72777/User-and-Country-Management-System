package com.assignment.controller;

import com.assignment.entity.CountryEntity;
import com.assignment.service.CountryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "Country Management", description = "APIs for having countries ")
@RestController
@RequestMapping("/api/countries")
@Validated
public class CountryController {

    @Autowired
    private CountryServiceImpl countryService;

    @Operation(summary = "Fetching data from countries url website and storing it into database")
    @PostMapping("/import")
    public ResponseEntity<?> importCountries(){
        countryService.fetchAndStoreCountries();
        return new ResponseEntity<>("Countries Imported Successfully", HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all countries with pagination")
    @GetMapping
    public ResponseEntity<Page<CountryEntity>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CountryEntity> countries = countryService.getAllCountries(pageable);

        return new ResponseEntity<>(countries,HttpStatus.OK);
    }

    @Operation(summary = "Retrieve country by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CountryEntity> getCountryById(@PathVariable Long id){
        return new ResponseEntity<>(countryService.getCountryById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add new country details")
    @PostMapping
    public ResponseEntity<CountryEntity> saveCountry(@Valid @RequestBody CountryEntity country){
        return new ResponseEntity<>(countryService.saveCountry(country),HttpStatus.OK);
    }

    @Operation(summary = "Update existing country details by ID and updated country details")
    @PutMapping("/{id}")
    public ResponseEntity<CountryEntity> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryEntity country){
        return new ResponseEntity<>(countryService.updateCountry(id, country), HttpStatus.OK);
    }

    @Operation(summary = "Delete existing country by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCountry(@PathVariable Long id){
        countryService.deleteCountry(id);
        return  new ResponseEntity(Map.of("message", "Country deleted successfully."), HttpStatus.OK);
    }

}
