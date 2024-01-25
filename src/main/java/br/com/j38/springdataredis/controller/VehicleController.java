package br.com.j38.springdataredis.controller;

import br.com.j38.springdataredis.dto.VehicleDTO;
import br.com.j38.springdataredis.mapper.VehicleMapper;
import br.com.j38.springdataredis.service.VehicleService;
import br.com.j38.springdataredis.entity.Vehicle;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleController {

    final VehicleService vehicleService;
    final VehicleMapper vehicleMapper;
    static final String VEHICLE_NOT_FOUND = "Vehicle not found";

    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        Vehicle createdVehicle = vehicleService.createOrUpdateVehicle(vehicle);
        return vehicleMapper.toDTO(createdVehicle);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleService.updateVehicle(vehicleMapper.toEntity(vehicleDTO));

        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VEHICLE_NOT_FOUND);
        }
        return vehicleMapper.toDTO(vehicle);
    }

    @GetMapping("/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO getByPlate(@PathVariable String plate) {
        Vehicle vehicle = vehicleService.findByPlate(plate);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VEHICLE_NOT_FOUND);
        }
        return vehicleMapper.toDTO(vehicle);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();

        if (vehicles == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VEHICLE_NOT_FOUND);
        }
        return vehicles.stream().map(vehicleMapper::toDTO).toList();
    }

    @GetMapping("/store/{storeName}")
    public List<VehicleDTO> getVehiclesByStore(@PathVariable String storeName) {
        List<Vehicle> vehicles = vehicleService.findVehiclesByStore(storeName);
        return vehicles.stream().map(vehicleMapper::toDTO).toList();
    }
}
