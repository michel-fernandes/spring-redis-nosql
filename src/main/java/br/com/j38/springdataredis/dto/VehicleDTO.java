package br.com.j38.springdataredis.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {
    @Pattern(regexp = "[A-Z]{3}\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2}", message = "Invalid plate format")
    @NotNull
    private String plate;
    @NotNull
    private String color;
    @NotNull
    private String model;
    @NotNull
    @Min(value = 1980)
    private int year;
    @NotNull
    private String store;
    @NotNull
    private boolean isActive;
}
