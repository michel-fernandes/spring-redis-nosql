package br.com.j38.springdataredis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VehicleController {

    VehicleService vehicleService;
    VehicleMapper vehicleMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        Vehicle createdVehicle = vehicleService.createOrUpdateVehicle(vehicle);
        return vehicleMapper.toDTO(createdVehicle);
    }

    @PatchMapping("/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO updateVehicle(@PathVariable String plate, @RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleService.updateVehicle(plate, vehicleMapper.toEntity(vehicleDTO));

        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return vehicleMapper.toDTO(vehicle);
    }

    @GetMapping("/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO getByPlate(@PathVariable String plate) {
        Vehicle vehicle = vehicleService.findByPlate(plate);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return vehicleMapper.toDTO(vehicle);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        return vehicles.stream().map(vehicleMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/store/{storeName}")
    public List<VehicleDTO> getVehiclesByStore(@PathVariable String storeName) {
        List<Vehicle> vehicles = vehicleService.findVehiclesByStore(storeName);
        return vehicles.stream().map(vehicleMapper::toDTO).collect(Collectors.toList());
    }
}
