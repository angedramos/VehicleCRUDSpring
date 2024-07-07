package com.Vehicles.SpringBoot.DI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.Vehicles.SpringBoot.DI.Model.Vehicle;
import com.Vehicles.SpringBoot.DI.Repository.VehicleRepository;

/**
 * El servicio VehicleService proporciona métodos para interactuar con el repositorio VehicleRepository.
 * Se encarga de la lógica de negocio relacionada con la entidad Vehicle.
 */

@Service
public class VehicleService {
	 @Autowired
	    private VehicleRepository vehicleRepository;

	    /**
	     * Obtiene una página de todos los vehículos.
	     *
	     * @param pageable información de paginación
	     * @return una página de todos los vehículos
	     */
	    public Page<Vehicle> getAllVehicles(Pageable pageable) {
	        return vehicleRepository.findAll(pageable);
	    }
	    
	    /**
	     * Encuentra una página de vehículos por su modelo.
	     *
	     * @param vehicleModel el modelo del vehículo a buscar
	     * @param pageable información de paginación
	     * @return una página de vehículos con el modelo especificado
	     */
	    public Page<Vehicle> findByVehicleModel(String vehicleModel, Pageable pageable) {
	        return vehicleRepository.findByVehicleModel(vehicleModel, pageable);
	    }
	    
	    /**
	     * Encuentra una página de vehículos por su placa.
	     *
	     * @param vehicleLicensePlate la placa del vehículo a buscar
	     * @param pageable información de paginación
	     * @return una página de vehículos con la placa especificada
	     */
	    public Page<Vehicle> findByVehicleLicensePlate(String vehicleLicensePlate, Pageable pageable) {
	        return vehicleRepository.findByVehicleLicensePlate(vehicleLicensePlate, pageable);
	    }

	    /**
	     * Encuentra una página de vehículos por su marca.
	     *
	     * @param vehicleBrand la marca del vehículo a buscar
	     * @param pageable información de paginación
	     * @return una página de vehículos con la marca especificada
	     */
	    public Page<Vehicle> findByVehicleBrand(String vehicleBrand, Pageable pageable) {
	        return vehicleRepository.findByVehicleBrand(vehicleBrand, pageable);
	    }
	   
}
