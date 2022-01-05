package com.tdp.ms.crud.expose;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tdp.ms.commons.expose.CommonController;
import com.tdp.ms.crud.business.entity.Alumno;
import com.tdp.ms.crud.business.interactors.AlumnoService;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {
	
			
	/**
     * Metodo para actuaizar alumno.
     */
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarAlumno(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
		
		Optional<Alumno> alumnoEncontrado = alumnoService.findById(id);
		Alumno alumnoBd = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()){
			return this.validar(result);
		}
				
				
		if(alumnoEncontrado.isEmpty()) {
			List<String> errorMensaje = Arrays.asList("Error: No se pudo editar, el ID: ", id.toString(), "no existe en la base datos!" );
			response.put("mensaje", errorMensaje.stream().collect(Collectors.joining(" ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			alumnoBd = alumnoEncontrado.get();		
			alumnoBd.setNombre(alumno.getNombre());
			alumnoBd.setApellido(alumno.getApellido());
			alumnoBd.setEmail(alumno.getEmail());
			
			alumnoBd = alumnoService.save(alumnoBd);
			
		}catch (DataAccessException e){
			response.put("mensaje", "Error al actualizar el alumno en la base de datos.");
			List<String> errorMensaje = Arrays.asList(e.getMessage(), ": ", e.getMostSpecificCause().getMessage() );
			response.put("error", errorMensaje.stream().collect(Collectors.joining(" ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
					
		response.put("mensaje", "La entidad ha sido actualizada con éxito!");
        response.put("curso", alumnoBd);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
