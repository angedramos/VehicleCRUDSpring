package com.Vehicles.SpringBoot.DI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * La clase principal de la aplicación Spring Boot para la gestión de vehículos.
 * 
 * <p>Esta clase es la entrada principal de la aplicación y se encarga de arrancar
 * el contexto de Spring Boot.</p>
 * 
 * <p>La anotación {@code @SpringBootApplication} es una combinación de las siguientes 
 * tres anotaciones:</p>
 * <ul>
 *   <li>{@code @Configuration}: Marca la clase como una fuente de definiciones de beans.</li>
 *   <li>{@code @EnableAutoConfiguration}: Permite a Spring Boot configurar automáticamente el contexto.</li>
 *   <li>{@code @ComponentScan}: Activa el escaneo de componentes para buscar beans en el paquete actual y sus subpaquetes.</li>
 * </ul>
 */
@SpringBootApplication
public class VehiclesCrudApplication {

	/**
     * El método principal que se utiliza para arrancar la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos pasados durante la ejecución.
     */
	public static void main(String[] args) {
		SpringApplication.run(VehiclesCrudApplication.class, args);
	}

}
