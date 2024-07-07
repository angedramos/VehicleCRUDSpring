-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS `vehicles`;

-- Usar la base de datos recién creada o existente
USE `vehicles`;

-- Crear la tabla vehicles_system con las columnas especificadas y un índice único en vehicle_license_plate
CREATE TABLE `vehicles_system` (
  `id` BIGINT NOT NULL AUTO_INCREMENT, -- ID de vehículo, incremento automático
  `vehicle_brand` VARCHAR(100) NOT NULL, -- Marca del vehículo, no puede estar vacío
  `vehicle_model` VARCHAR(100) NOT NULL, -- Modelo del vehículo, no puede estar vacío
  `vehicle_license_plate` VARCHAR(20) NOT NULL UNIQUE, -- Placa del vehículo, no puede estar vacío y debe ser único
  `vehicle_color` VARCHAR(30) NOT NULL, -- Color del vehículo, no puede estar vacío
  `vehicle_year` INT NOT NULL, -- Año del vehículo, no puede estar vacío
  PRIMARY KEY (`id`) -- Clave primaria en la columna id
);