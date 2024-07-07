package com.Vehicles.SpringBoot.DI.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * La clase Vehicle representa la entidad del vehículo en la base de datos.
 * Esta entidad se mapea a la tabla "vehicles_system" en la base de datos.
 */

@Entity
@Table(name="vehicles_system")
public class Vehicle {

    /**
     * Identificador único del vehículo. Es generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Marca del vehículo.
     */
    @Column(name="vehicle_make")
    private String vehicleBrand;
    
    /**
     * Modelo del vehículo.
     */
    @Column(name="vehicle_model")
    private String vehicleModel;
    
    /**
     * Placa del vehículo.
     */
    @Column(name="vehicle_license_plate")
    private String vehicleLicensePlate;
    
    /**
     * Color del vehículo.
     */
    @Column(name="vehicle_color")
    private String vehicleColor;
    
    /**
     * Año del vehículo.
     */
    @Column(name="vehicle_year")
    private int vehicleYear;
    
    /**
     * Constructor por defecto para vehículo.
     */
	public Vehicle() {
		
	}
	
    /**
     * Constructor para vehículo.
     */
	public Vehicle(Long id, String vehicleBrand, String vehicleModel, String vehicleLicensePlate,
			String vehicleColor, int vehicleYear) {
		super();
		this.id = id;
		this.vehicleBrand = vehicleBrand;
		this.vehicleModel = vehicleModel;
		this.vehicleLicensePlate = vehicleLicensePlate;
		this.vehicleColor = vehicleColor;
		this.vehicleYear = vehicleYear;
	}

    /**
     * Obtiene el ID del vehículo.
     * @return ID.
     */
	public Long getId() {
		return id;
	}
	
    /**
     * Asigna el ID del vehículo
     * @param id ID del vehículo
     */
	public void setId(Long id) {
		this.id = id;
	}
	
    /**
     * Obtiene la marca del vehículo.
     * @return vehicleBrand.
     */
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	
    /**
     * Asigna la marca del vehículo
     * @param vehicleBrand Marca del vehículo
     */
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	
    /**
     * Obtiene el modelo del vehículo.
     * @return vehicleModel.
     */
	public String getVehicleModel() {
		return vehicleModel;
	}
	
    /**
     * Asigna el modelo del vehículo
     * @param vehicleModel Modelo del vehículo
     */
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
    /**
     * Obtiene la matrícula del vehículo.
     * @return vehicleLicensePlate.
     */
	public String getVehicleLicensePlate() {
		return vehicleLicensePlate;
	}
    /**
     * Asigna la matrícula del vehículo
     * @param vehicleLicensePlate Matrícula del vehículo
     */
	public void setVehicleLicensePlate(String vehicleLicensePlate) {
		this.vehicleLicensePlate = vehicleLicensePlate;
	}
	
    /**
     * Obtiene el color del vehículo.
     * @return vehicleColor.
     */
	public String getVehicleColor() {
		return vehicleColor;
	}
    /**
     * Asigna el color del vehículo
     * @param vehicleColor Color del vehículo
     */
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
    /**
     * Obtiene el año del vehículo.
     * @return vehicleYear.
     */
	public int getVehicleYear() {
		return vehicleYear;
	}
    /**
     * Asigna el año del vehículo
     * @param vehicleYear Año del vehículo
     */
	public void setVehicleYear(int vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
    
    
    
    
}