package br.com.j38.springdataredis.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle", schema = "market_place")
public class Vehicle {
    @Id
    String plate;
    
    String color;
    String model;
    int modelYear;
    Timestamp createdAt;

    @Column(nullable = true) // Permite valores nulos
    Timestamp updatedAt;

    boolean isActive;
    String store;
}
