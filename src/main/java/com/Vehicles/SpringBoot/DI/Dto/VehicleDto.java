package com.Vehicles.SpringBoot.DI.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * VehicleDto es un objeto de transferencia de datos que representa la información de un vehículo.
 * Este DTO se utiliza para transferir datos entre la capa de presentación y la capa de servicio
 * de la aplicación sin exponer las entidades del dominio directamente.
 */

public class VehicleDto {

    /**
     * Marca del vehículo.
     */
	@NotEmpty(message="Vehicle Make is required")
	@Size(min=3, max=100, message="Vehicle Make must be between 3 and 100 characters")
	private String vehicleBrand;
	
    /**
     * Modelo del vehículo.
     */
	@NotEmpty(message="Vehicle Model is required")
	@Size(min=2,max=100, message="Vehicle Make must be between 2 and 100 characters")
    private String vehicleModel;
    
    /**
     * Placa del vehículo. Debe ser única y siempre en mayúsculas.
     */
	@NotEmpty(message="Vehicle License Plate is required")
	@Size(min=5, max=20, message="Vehicle License Plate must be between 5 and 20 characters")
    private String vehicleLicensePlate;
	
    /**
     * Color del vehículo.
     */
    
	@NotEmpty(message="Vehicle Color is required")
	@Size(min=3, max=30, message="Vehicle Color must be between 3 and 30 characters")
    private String vehicleColor;
	
    /**
     * Año del vehículo.
     */
	@NotNull(message = "Vehicle Year cannot be Empty")
    @Min(value = 1886, message = "Vehicle Year cannot be lower than 1886")
    @Max(value = 2024, message = "Vehicle Year cannot be Higher than 2024")
    private int vehicleYear;

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleLicensePlate() {
		return vehicleLicensePlate;
	}

	public void setVehicleLicensePlate(String vehicleLicensePlate) {
		this.vehicleLicensePlate = vehicleLicensePlate.toUpperCase();
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public int getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(int vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
}
