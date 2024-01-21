package br.com.j38.springdataredis;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VehicleService {
    VehicleRedisRepository vehicleRedisRepository;
    VehicleRepository vehicleRepository;

    public Vehicle createOrUpdateVehicle(Vehicle vehicle) {
        //setCreatedAt() com a data UTC atual
        vehicle.setCreatedAt(LocalDateTime.from(Instant.now().truncatedTo(ChronoUnit.MILLIS)));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        vehicleRedisRepository.save(savedVehicle);
        return savedVehicle;
    }

    public Vehicle updateVehicle(String plate, Vehicle vehicleChanged) {
        //Busca o veículo no Redis, se não encontrar busca no banco de dados
        Optional<Vehicle> existingVehicle = vehicleRedisRepository.findById(plate);

        if (existingVehicle.isEmpty())
            existingVehicle = vehicleRepository.findById(plate);

        if (existingVehicle.isPresent()) {
            var vehicleToUpdate = new Vehicle();
            BeanUtils.copyProperties(vehicleChanged, vehicleToUpdate);
            vehicleToUpdate.setCreatedAt(existingVehicle.get().getCreatedAt());
            vehicleToUpdate.setUpdatedAt(LocalDateTime.from(Instant.now().truncatedTo(ChronoUnit.MILLIS)));

            Vehicle updatedVehicle = vehicleRepository.save(vehicleToUpdate);
            vehicleRedisRepository.save(updatedVehicle);
            return updatedVehicle;
        }

        return null;
    }

    public Vehicle findByPlate(String plate) {
        return vehicleRedisRepository.findById(plate)
                .orElseGet(() -> vehicleRepository.findById(plate).orElse(null));
    }

    public List<Vehicle> findAllVehicles() {
        //retornar todos os veículos do Redis, caso não tenha nenhum, retornar todos do banco de dados
        Iterable<Vehicle> vehicles = vehicleRedisRepository.findAll();

        if (vehicles.iterator().hasNext()) {
            List<Vehicle> vehicleList = new ArrayList<>();
            vehicles.forEach(vehicleList::add);
            return vehicleList;
        } else
            return vehicleRepository.findAll();
    }

    public List<Vehicle> findVehiclesByStore(String store) {
        //retornar todos os veículos do Redis com store, caso não tenha nenhum, retornar todos do banco de dados
        Set<Vehicle> vehicles = vehicleRedisRepository.findByStore(store);

        if (vehicles.iterator().hasNext()) {
            return new ArrayList<>(vehicles);
        } else
            return vehicleRepository.findByStore(store);
    }
}
