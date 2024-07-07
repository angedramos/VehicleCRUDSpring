package com.Vehicles.SpringBoot.DI.ControllerTest;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

import com.Vehicles.SpringBoot.DI.Dto.VehicleDto;
import com.Vehicles.SpringBoot.DI.Model.Vehicle;
import com.Vehicles.SpringBoot.DI.Repository.VehicleRepository;
import com.Vehicles.SpringBoot.DI.controller.VehicleController;
import com.Vehicles.SpringBoot.DI.service.VehicleService;


@WebMvcTest(VehicleController.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VehicleControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private BindingResult result;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;
    
    @MockBean
    private VehicleRepository vRepo;

    @InjectMocks
    private VehicleController vehicleController;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @MockBean
    private VehicleService vehicleService;
    
    private List<Vehicle> vehicleList;

    @BeforeEach
    public void setup() {
    	Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleBrand("Toyota");
        vehicle1.setVehicleModel("Camry");
        vehicle1.setVehicleLicensePlate("ABC123");
        vehicle1.setVehicleColor("Blue");
        vehicle1.setVehicleYear(2020);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setVehicleBrand("Honda");
        vehicle2.setVehicleModel("Accord");
        vehicle2.setVehicleLicensePlate("DEF456");
        vehicle2.setVehicleColor("Red");
        vehicle2.setVehicleYear(2019);

        vehicleList = Arrays.asList(vehicle1, vehicle2);
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetAllVehicles() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicleList, pageable, vehicleList.size());

        when(vehicleService.getAllVehicles(any(Pageable.class))).thenReturn(vehiclePage);

        mockMvc.perform(get("/vehicles")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles"))
                .andExpect(model().attributeExists("vehiclePage"))
                .andExpect(model().attribute("vehiclePage", vehiclePage))
                .andExpect(model().attribute("sortBy", "id"))
                .andExpect(model().attribute("sortDir", "asc"));
    }
    
    @Test
    public void testDeleteVehicleSuccess() throws Exception {
        Long vehicleId =  1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setVehicleBrand("Toyota");

        when(vRepo.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/vehicles/delete/")
                .param("id", vehicleId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles"))
                .andExpect(flash().attribute("successMessage", "Vehicle: " + vehicle.getVehicleBrand() + " has been deleted Successfully"));

        verify(vRepo, times(1)).findById(vehicleId);
        verify(vRepo, times(1)).delete(vehicle);
    }
    

    @Test
    public void testAddVehicleSuccess() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleBrand("Toyota");
        vehicleDto.setVehicleModel("Camry");
        vehicleDto.setVehicleLicensePlate("ABCSS123");
        vehicleDto.setVehicleColor("Blue");
        vehicleDto.setVehicleYear(2020);

        when(vRepo.existsByVehicleLicensePlate(anyString())).thenReturn(false);
        when(vRepo.save(any(Vehicle.class))).thenReturn(new Vehicle());

        this.mockMvc.perform(post("/vehicles/addVehicle")
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles"))
                .andExpect(flash().attribute("successMessage", "Vehicle with License Plate: "+vehicleDto.getVehicleLicensePlate()+" was added Successfully"));
    }
    
    @Test
    public void testAddVehicleLicensePlateExists() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleBrand("Toyota");
        vehicleDto.setVehicleModel("Camry");
        vehicleDto.setVehicleLicensePlate("ABC123");
        vehicleDto.setVehicleColor("Blue");
        vehicleDto.setVehicleYear(2020);

        when(vRepo.existsByVehicleLicensePlate(anyString())).thenReturn(true);

        this.mockMvc.perform(post("/vehicles/addVehicle")
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage", "License Plate: ABC123 already exists"))
                .andExpect(model().attributeExists("vehicleDto"));
    }

    @Test
    public void testAddVehicleValidationErrors() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();

        this.mockMvc.perform(post("/vehicles/addVehicle")
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vehicleDto"))
                .andExpect(model().attributeHasFieldErrors("vehicleDto", "vehicleBrand", "vehicleModel", "vehicleLicensePlate", "vehicleColor", "vehicleYear"));
    }
    
    @Test
    public void testEditVehicleSuccess() throws Exception {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setVehicleBrand("Toyota");
        vehicle.setVehicleModel("Camry");
        vehicle.setVehicleLicensePlate("ABC123");
        vehicle.setVehicleColor("Blue");
        vehicle.setVehicleYear(2020);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleBrand("Honda");
        vehicleDto.setVehicleModel("Civic");
        vehicleDto.setVehicleLicensePlate("XYZ789");
        vehicleDto.setVehicleColor("Red");
        vehicleDto.setVehicleYear(2021);

        when(vRepo.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(result.hasErrors()).thenReturn(false);
        when(vRepo.save(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/vehicles/edit/")
                .param("id", vehicleId.toString())
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles"))
                .andExpect(flash().attribute("successMessage", "Vehicle with License Plate: "+vehicleDto.getVehicleLicensePlate()+"was edited successfully"));

        verify(vRepo, times(1)).findById(vehicleId);
        verify(vRepo, times(1)).save(any(Vehicle.class));
    }
    
    @Test
    public void testEditVehicleWithErrors() throws Exception {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleBrand("Honda");
        vehicleDto.setVehicleModel("Civic");
        vehicleDto.setVehicleLicensePlate("XYZ789");
        vehicleDto.setVehicleColor("Red");
        vehicleDto.setVehicleYear(1880);

        when(vRepo.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/vehicles/edit/")
                .param("id", vehicleId.toString())
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().isOk())
                .andExpect(view().name("editVehicle"));

        verify(vRepo, times(1)).findById(vehicleId);
        verify(vRepo, times(0)).save(any(Vehicle.class));
    }
    
    @Test	
    public void testEditVehicleNotFound() throws Exception {
        Long vehicleId = 34L;

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleBrand("Honda");
        vehicleDto.setVehicleModel("Civic");
        vehicleDto.setVehicleLicensePlate("XYZ789");
        vehicleDto.setVehicleColor("Red");
        vehicleDto.setVehicleYear(2021);

        when(vRepo.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/vehicles/edit/")
                .param("id", vehicleId.toString())
                .flashAttr("vehicleDto", vehicleDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles"));

        verify(vRepo, times(1)).findById(vehicleId);
        verify(vRepo, times(0)).save(any(Vehicle.class));
    }
    
    @Test
    public void testSearchVehiclesByModel() throws Exception {
        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(new Vehicle()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByVehicleModel(anyString(), any(Pageable.class))).thenReturn(vehiclePage);

        mockMvc.perform(post("/vehicles/search")
                .param("searchType", "model")
                .param("searchTerm", "Camry")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchVehicle"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("searchTerm", "Camry"))
                .andExpect(model().attribute("searchType", "Model"))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1));
    }

    @Test
    public void testSearchVehiclesByLicensePlate() throws Exception {
        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(new Vehicle()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByVehicleLicensePlate(anyString(), any(Pageable.class))).thenReturn(vehiclePage);

        mockMvc.perform(post("/vehicles/search")
                .param("searchType", "licensePlate")
                .param("searchTerm", "ABC123")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchVehicle"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("searchTerm", "ABC123"))
                .andExpect(model().attribute("searchType", "License Plate"))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1));
    }

    @Test
    public void testSearchVehiclesByBrand() throws Exception {
        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(new Vehicle()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByVehicleBrand(anyString(), any(Pageable.class))).thenReturn(vehiclePage);

        mockMvc.perform(post("/vehicles/search")
                .param("searchType", "brand")
                .param("searchTerm", "Toyota")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchVehicle"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("searchTerm", "Toyota"))
                .andExpect(model().attribute("searchType", "Brand"))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1));
    }


}
