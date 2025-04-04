package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto {

    @JsonProperty("name")
    private Map<String, Object> name;

    @JsonProperty("capital")
    private List<String> capital;

    private String region;
    private String subregion;

    @Positive(message = "Population must be positive")
    private long population;

    @JsonProperty("flags")
    private Map<String,String> flags;

    public CountryDto(){}

    public CountryDto( Map<String, Object> name, List<String> capital, String region, String subregion, long population, Map<String, String> flags) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.flags = flags;
    }

    public String getName() {
        return name != null && name.get("common") instanceof String ? (String) name.get("common") : "N/A";
    }

    public void setName(Map<String, Object> name) {
        this.name = name;
    }

    public String getCapital() {
        return (capital != null && !capital.isEmpty()) ? capital.get(0) : "N/A";
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFlagUrl() {
        return flags != null ? flags.get("png") : "N/A";
    }

    public void setFlagUrl(Map<String, String> flags) {
        this.flags = flags;
    }
}
