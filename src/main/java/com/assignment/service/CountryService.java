package com.assignment.service;


import com.assignment.dto.CountryDto;
import com.assignment.entity.CountryEntity;
import com.assignment.exception.ResourceNotFoundException;
import com.assignment.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@Slf4j
public class CountryService {

   private final CountryRepository countryRepository;
   private final RestClient restClient;

   public CountryService(CountryRepository countryRepository){
       this.countryRepository = countryRepository;
       this.restClient = RestClient.create();
   }

    // fetch data from API
    @Transactional
   public List<CountryDto> fetchAndStoreCountries(){
       String apiUrl = "https://restcountries.com/v3.1/all";

       try {
////           log.info("Fetching countries from API : {}", apiUrl);
//           String response = restClient.get()
//                   .uri(apiUrl)
//                   .retrieve()
//                   .body(String.class);
//           System.out.println("API Response: " + response);

           CountryDto[] countries = restClient.get()
                   .uri(apiUrl)
                   .retrieve()
                   .body(CountryDto[].class);
           System.out.println("Deserialized Country Count: " + (countries != null ? countries.length : 0));
           if (countries != null && countries.length > 0) {
               List<CountryEntity> countryEntities = List.of(countries).stream()
                       .map(dto -> new CountryEntity(
                               null,
                                  dto.getName(),
                                  dto.getCapital(),
                                  dto.getRegion(),
                                  dto.getSubregion(),
                                  dto.getPopulation(),
                                  dto.getFlagUrl()
                       ))
                       .collect(Collectors.toList());

               countryRepository.saveAll(countryEntities);
//               System.out.println("Successfully stored {} countries in the database"+ countryEntities.size()+" countries.");

           }
           return List.of(countries);

       }catch (RestClientResponseException e) {
           throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error fetching country data",e);
       }

   }

   public Page<CountryEntity> getAllCountries(Pageable pageable){
       return countryRepository.findAll(pageable);
   }

   public CountryEntity getCountryById(Long id){
       return countryRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID : "+ id));
   }

   @Transactional
   public CountryEntity saveCountry(CountryEntity country){
       return countryRepository.save(country);
   }

   @Transactional
    public CountryEntity updateCountry(Long id, CountryEntity updateCountry){
       CountryEntity country = getCountryById(id);
       country.setName(updateCountry.getName());
       country.setCapital(updateCountry.getCapital());
       country.setRegion(updateCountry.getRegion());
       country.setSubregion(updateCountry.getSubregion());
       country.setPopulation(updateCountry.getPopulation());
       country.setFlagUrl(updateCountry.getFlagUrl());

       return countryRepository.save(country);
   }

   @Transactional
    public void deleteCountry(Long id){
       CountryEntity country = getCountryById(id);
       countryRepository.delete(country);
   }
}
