package com.tdp.ms.commons.expose;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tdp.ms.commons.business.iteractors.CommonService;


public class CommonController<E, S extends CommonService<E>>{
	
	@Autowired
	protected S alumnoService;
		
	/**
     * Metodo para listar alumno.
     */
	@GetMapping
	public ResponseEntity<?> listarAlumno(){
		
		return ResponseEntity.ok().body(alumnoService.findAll());
	}
	
	/**
     * Metodo para visualizar alumno.
     */
	@GetMapping("/{id}")
	public ResponseEntity<?> verAlumno(@PathVariable Long id){
		
		Optional<E> entity = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			entity = alumnoService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            List<String> errorMensaje = Arrays.asList(e.getMessage(), e.getMostSpecificCause().getMessage());
            response.put("error", errorMensaje.stream().collect(Collectors.joining(" ")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if(entity.isEmpty()) {
			List<String> errorMensaje = Arrays.asList("El ID: ", id.toString(), "no existe en la base datos!" );
			response.put("mensaje", errorMensaje.stream().collect(Collectors.joining(" ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(entity.get());
	}

	/**
     * Metodo para registrar alumno.
     */
	@PostMapping
	public ResponseEntity<?> resgitrarAlumno(@Valid @RequestBody E entity, BindingResult result){
		
		E entityDb = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()){
			return this.validar(result);
		}
		
		try{
			entityDb = alumnoService.save(entity);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            List<String> errorMensaje = Arrays.asList(e.getMessage(), e.getMostSpecificCause().getMessage());
            response.put("error", errorMensaje.stream().collect(Collectors.joining(" ")));            
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		response.put("mensaje", "La entidad ha sido creado con éxito!");
        response.put("curso", entityDb);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
		
	/**
     * Metodo para eliminiar alumno.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> actualizarAlumno(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();

		
		try {
			alumnoService.deleteById(id);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar la entidad en la base de datos.");
            List<String> errorMensaje = Arrays.asList(e.getMessage(), e.getMostSpecificCause().getMessage());
            response.put("error", errorMensaje.stream().collect(Collectors.joining(" ")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		response.put("mensaje", "El entidad ha sido eliminada con éxito!");
		return ResponseEntity.noContent().build();				
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			List<String> mensajeError = Arrays.asList("El campo", err.getField(), err.getDefaultMessage());
			errores.put(err.getField(), mensajeError.stream().collect(Collectors.joining(" ")));
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
