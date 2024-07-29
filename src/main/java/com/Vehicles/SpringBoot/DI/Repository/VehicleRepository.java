package com.Vehicles.SpringBoot.DI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Vehicles.SpringBoot.DI.Model.Vehicle;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * El repositorio VehicleRepository proporciona operaciones CRUD para la entidad Vehicle.
 * Extiende JpaRepository para heredar métodos para trabajar con la persistencia de datos.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

    /**
     * Encuentra una página de vehículos por su modelo.
     *
     * @param vehicleModel el modelo del vehículo a buscar
     * @param pageable información de paginación
     * @return una página de vehículos con el modelo especificado
     */
	  Page<Vehicle> findByVehicleModel(String vehicleModel, Pageable pageable);
	  
	  /**
	     * Encuentra una página de vehículos por su placa.
	     *
	     * @param vehicleLicensePlate la placa del vehículo a buscar
	     * @param pageable información de paginación
	     * @return una página de vehículos con la placa especificada
	     */
	  Page<Vehicle> findByVehicleLicensePlate(String vehicleLicensePlate, Pageable pageable);
	  
	  /**
	     * Encuentra una página de vehículos por su marca.
	     *
	     * @param vehicleBrand la marca del vehículo a buscar
	     * @param pageable información de paginación
	     * @return una página de vehículos con la marca especificada
	     */
	  Page<Vehicle> findByVehicleBrand(String vehicleBrand, Pageable pageable);
	  
	  /**
	     * Verifica si un vehículo con la placa especificada ya existe al momento de agregar.
	     *
	     * @param vehicleLicensePlate la placa del vehículo a verificar
	     * @return true si un vehículo con la placa especificada ya existe, false de lo contrario
	     */
	  boolean existsByVehicleLicensePlate(String vehicleLicensePlate);
	  
	  /**
	     * Verifica si un vehículo con la placa especificada ya existe al momento de editar.
	     *
	     * @param vehicleLicensePlate la placa del vehículo a verificar
	     * @return true si un vehículo con la placa especificada ya existe, false de lo contrario
	     */
	  boolean existsByVehicleLicensePlateAndIdNot(String vehicleLicensePlate, Long id);
}
