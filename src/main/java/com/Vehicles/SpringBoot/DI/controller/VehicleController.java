package com.Vehicles.SpringBoot.DI.controller;

/**
 * @author Angel Eduardo Ramos Mena
 */
import com.Vehicles.SpringBoot.DI.Dto.VehicleDto;
import com.Vehicles.SpringBoot.DI.Model.Vehicle;
import com.Vehicles.SpringBoot.DI.Repository.VehicleRepository;

import org.springframework.stereotype.Controller;



import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
	import org.springframework.data.domain.Pageable;
	import org.springframework.data.domain.Sort;
	import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
	import com.Vehicles.SpringBoot.DI.service.VehicleService;

import jakarta.validation.Valid;

	/**
	 * Este es el controlador que controla la interacción del usuario con la aplicación.
	 */
	@Controller
	@RequestMapping("/vehicles")
	public class VehicleController {

	    @Autowired
	    private VehicleService vehicleService;
	    
	    @Autowired
	    private VehicleRepository vRepo;

	    /**
	     * Este método maneja una solicitud HTTP GET para obtener una lista paginada de vehículos.
	     * obtiene los vehículos que hay en la base de datos y,
	     * los agrega al modelo para mostrarlo en la vista renderizada de "vehicles".
	     * 
	     * @param page Es el número de paginas a mostrar, está en 0 si no está especificado.
	     * @param size Es el numero de registros por página, está en 10 si no está especificado.
	     * @param sortBy Es el campo en el cual podemos filtrar por campo, está filtrado por "id" si no está especificado.
	     * @param sortDir Es la dirección del ordenamiento,
	     * puede ser "asc" para ascendente o "desc" para descendente, está en "asc" si no está especificado.
	     * @param model Es el modelo por el cual la pagina y ordenamiento de los atributos del vehículo son agregados.
	     * @return Se muestra el nombre de la vista renderizada, el cual es "vehicles".
	     *
	     */
	    
	    @GetMapping({"","/"})
	    public String getAllVehicles(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy,
	            @RequestParam(defaultValue = "asc") String sortDir,
	            Model model) {

	        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
	                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

	        Pageable pageable = PageRequest.of(page, size, sort);
	        Page<Vehicle> vehiclePage = vehicleService.getAllVehicles(pageable);
	        model.addAttribute("sortBy", sortBy);  
	        model.addAttribute("sortDir", sortDir);  
	        model.addAttribute("vehiclePage", vehiclePage);
	        return "vehicles";
	    }
	    
	    /**
	     * 
	     * Este método maneja una solicitud HTTP GET para mostrar el formulario
	     * que sirve para agregar un nuevo vehículo a la base de datos.
	     * 
	     * Este método crea una nueva instancia de {@link VehicleDto} y lo agrega al modelo.
	     * 
	     * El formulario que se usará para crear un nuevo vehículo será renderizado usando la vista "createVehicle"
	     * 
	     * @param model es el modelo el cual {@link VehicleDto} es usado como objeto a enviar.
	     * @return Se mostrará el nombre de la vista a renderizar, el cual es "createVehicle".
	     */
	    
	    @GetMapping("/addVehicle")
	    public String ShowVehicleForm(Model model) {
	    	VehicleDto vehicleDto = new VehicleDto();
	    	model.addAttribute("vehicleDto",vehicleDto);
	    	return "createVehicle";
	    }
	    
	    /**
	     * Este método maneja una solicitud HTTP POST para agregar un nuevo vehículo.
	     * 
	     * Este método valida la data que viene para ese vehículo. Si hay errores, redirecciona a la vista "createVehicle".
	     * Si en la data entrante, hay una matricula que coincide con otra en la base de datos, se considera error y
	     * devuelve la misma vista con ese mensaje.
	     * En cambio, si no hay ningún error, 
	     * Lo guarda en el repositorio, y redirige al usuario a la lista de vehículos, es decir,
	     * a la vista "vehicles", con el mensaje de que se agregó correctamente.
	     *
	     * @param vehicleDto Es el objeto que tiene los datos.
	     * @param result Es el objeto para la validación.
	     * @param redirectAttributes Aquí se redireccionara el mensaje exitoso.
	     * @param model Es el modelo al cual los atributos serán agregados.
	     * @return Regresa el nombre de la vista renderizada "createVehicle" si hay algún error, si no,
	     * lo redirecciona a la vista "/vehicles"
	     */
	    
	    @PostMapping("/addVehicle")
	    public String AddVehicle(@Valid @ModelAttribute VehicleDto vehicleDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
	    	if(result.hasErrors()) {
	    		return "createVehicle";
	    	}
	        if (vRepo.existsByVehicleLicensePlate(vehicleDto.getVehicleLicensePlate())) {
	            model.addAttribute("vehicleDto", vehicleDto);
	            model.addAttribute("errorMessage", "License Plate: "+vehicleDto.getVehicleLicensePlate()+" already exists");
	            return "createVehicle";
	        }
	    	Vehicle vehicle = new Vehicle();
	    	vehicle.setVehicleBrand(vehicleDto.getVehicleBrand());
	    	vehicle.setVehicleModel(vehicleDto.getVehicleModel());
	    	vehicle.setVehicleLicensePlate(vehicleDto.getVehicleLicensePlate());
	    	vehicle.setVehicleColor(vehicleDto.getVehicleColor());
	    	vehicle.setVehicleYear(vehicleDto.getVehicleYear());
	    	vRepo.save(vehicle);
	    	redirectAttributes.addFlashAttribute("successMessage", "Vehicle with License Plate: "+vehicle.getVehicleLicensePlate()+" was added Successfully");
	    	return "redirect:/vehicles";
	    }
	    /**
	     * Este método maneja una solicitud GET para mostrar el formulario para editar un vehículo.
	     * 
	     * Este método devuelve el vehículo con el ID dado desde el repositorio,
	     * lo convierte al Dto {@link com.Vehicles.SpringBoot.DI.Dto}, y lo agrega al modelo para mostrarlo en la vista "editVehicle",
	     * si no se encuentra el vehículo, mostrará un mensaje de error en la pantalla. 
	     *
	     * @param model Es el modelo al cual el vehículo y su objeto {@link com.Vehicles.SpringBoot.DI.Dto} serán agregados.
	     * @param id Es el ID del vehículo a Editar.
	     * @param redirectAttributes Se mostrará en caso de que haya algún error.
	     * @return Se muestra el nombre de la vista, el cual es "editVehicle".
	     */
	    @GetMapping("/edit/")
	    public String ShowEditVehicle(Model model, @RequestParam Long id, RedirectAttributes redirectAttributes) {
	    	
	    	try {
	    		Vehicle vehicle = vRepo.findById(id).get();
	            model.addAttribute("vehicle", vehicle);
	            
	            VehicleDto vehicleDto = new VehicleDto();
	            vehicleDto.setVehicleBrand(vehicle.getVehicleBrand());
	            vehicleDto.setVehicleModel(vehicle.getVehicleModel());
	            vehicleDto.setVehicleLicensePlate(vehicle.getVehicleLicensePlate());
	            vehicleDto.setVehicleColor(vehicle.getVehicleColor());
	            vehicleDto.setVehicleYear(vehicle.getVehicleYear());
		    	
		    	model.addAttribute("vehicleDto",vehicleDto);
	    	}
	    	catch(Exception ex) {
	    		redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
	    		return "editVehicle";
	    	}
	    	
	    	return "editVehicle";
	    }
	    
	    /**
	     * Este método maneja una solicitud HTTP POST para la edición de un vehículo existente.
	     *
	     * Este método obtiene el vehículo por su ID y actualiza sus detalles basados en el objeto {@link com.Vehicles.SpringBoot.DI.Dto}.
	     * Si hay errores en la validación del objeto, muestra esos errores en la vista "editvehicle".
	     * Si al intentar actualizar la matricula ya hay una igual en la base de datos, agrega ese error al modelo y devuelve la vista "editVehicle".
	     * En cambio, si todo sale bien, agrega la actualización del vehículo al repositorio, y redireccionará
	     * con un mensaje exitoso en la vista "/vehicles".
	     *
	     * @param id ID del vehículo
	     * @param vehicleDto tEl objeto que contiene los datos actualizados.
	     * @param result Es el objeto para la validación.
	     * @param model Es el modelo al cual el los atributos serán agregados.
	     * @param redirectAttributes Es el objeto al cual se agregarán los mensajes exitosos.
	     * @return Muestra el nombre de la vista renderizada "editVehicle" si hay errores, si no hay errores, se redireciona a la vista "/vehicles".
	     */

	    @PostMapping("/edit/")
	    public String EditVehicle(@RequestParam Long id, @Valid @ModelAttribute VehicleDto vehicleDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

	        try {
	            Vehicle vehicle = vRepo.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
	            model.addAttribute("vehicle",vehicle);
		    	if(result.hasErrors()) {
		    		return "editVehicle";
		    	}
		        if (vRepo.existsByVehicleLicensePlate(vehicleDto.getVehicleLicensePlate())) {
		            model.addAttribute("vehicleDto", vehicleDto);
		            model.addAttribute("errorMessage", "License Plate: "+vehicleDto.getVehicleLicensePlate()+" already exists");
		            return "editVehicle";
		        }

	            vehicle.setVehicleBrand(vehicleDto.getVehicleBrand());
	            vehicle.setVehicleModel(vehicleDto.getVehicleModel());
	            vehicle.setVehicleLicensePlate(vehicleDto.getVehicleLicensePlate());
	            vehicle.setVehicleColor(vehicleDto.getVehicleColor());
	            vehicle.setVehicleYear(vehicleDto.getVehicleYear());

	            vRepo.save(vehicle);
	            redirectAttributes.addFlashAttribute("successMessage", "Vehicle with License Plate: "+vehicleDto.getVehicleLicensePlate()+"was edited successfully");
	        } catch (Exception ex) {
	            System.out.println("Exception: "+ex.getMessage());
	        }
	        return "redirect:/vehicles";
	    }
	    /**
	     * Este método maneja una solicitud HTTP GET para borrar un vehículo por su ID.
	     *
	     * Este método obtiene el vehículo por su ID, lo borra desde el repositorio,
	     * y muestra un mensaje exitoso de que ha sido borrado. si hay una excepción,
	     * se muestra un mensaje.
	     *
	     * @param id ID del vehículo
	     * @param redirectAttributes Es el objeto al cual se agregarán los mensajes exitosos.
	     * @return Se muestra la vista renderizada, la cual es "/vehicles"
	     */

        @GetMapping("/delete/")
        public String deteleVehicle(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        	
        	try {
        		Vehicle vehicle = vRepo.findById(id).get();
        		vRepo.delete(vehicle);
        		redirectAttributes.addFlashAttribute("successMessage", "Vehicle: "+vehicle.getVehicleBrand()+" has been deleted Successfully");
        	}
        	catch(Exception ex) {
        		System.out.println("Exception: "+ex.getMessage());
        	}
        	
        	
        	return "redirect:/vehicles";
        }
        
        /**
         * Este método maneja una solicitud HTTP GET para mostrar una vista con un buscador .
         *
         * Este método inicializa el modelo con valores por defecto para la busqueda por tipo y por término,
         * luego, regresa la nombre de la vista con el formulario de busqueda.
         *
         * @param model Es el modelo al cual los atributos serán agregados.
         * @return Se muestra la vista renderizada, la cual es "searchVehicle"
         */
        @GetMapping("/searchVehicle")
        public String showSearchForm(Model model) {
            model.addAttribute("searchTerm", new String());
            model.addAttribute("searchTerm", "");
            model.addAttribute("searchType", "model");
            return "searchVehicle";
        }
        
        /**
         * Este método maneja solicitudes HTTP POST para la busqueda de vehículos en base al termino y al tipo de busqueda..
         *
         * Este método acepta parametros de busqueda de la solicitud, hace la busqueda en base al tipo de busqueda y,
         * agrega los resultados de la busqueda y la información relacionada al modelo que será mostrado en la vista "searchVehicle".
         *
         * @param searchType Es el típo de busqueda que se va a realizar (e.j., Busqueda por modelo, o Matrícula, o Marca)
         * @param searchTerm El termino a buscar
         * @param page El número de la pagina en la paginación.
         * @param size Números de registros por página.
         * @param model Es el modelo al cual los atributos serán agregados.
         * @return El nombre de la vista renderizada, el cual es "searchVehicle"
         */

        @PostMapping("/search")
        public String searchVehicles(@RequestParam("searchType") String searchType, @RequestParam("searchTerm") String searchTerm, 
                                     @RequestParam("page") int page, @RequestParam("size") int size, Model model) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Vehicle> vehicles;
            switch (searchType) {
                case "model":
                    vehicles = vehicleService.findByVehicleModel(searchTerm, pageable);
                    break;
                case "licensePlate":
                    vehicles = vehicleService.findByVehicleLicensePlate(searchTerm, pageable);
                    break;
                case "brand":
                    vehicles = vehicleService.findByVehicleBrand(searchTerm, pageable);
                    break;
                default:
                    vehicles = Page.empty();
            }
            model.addAttribute("vehicles", vehicles.getContent());
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("searchType", searchType.equals("model") ? "Model" : searchType.equals("licensePlate") ? "License Plate" : "Brand");
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", vehicles.getTotalPages());
            return "searchVehicle";
        }
	}


