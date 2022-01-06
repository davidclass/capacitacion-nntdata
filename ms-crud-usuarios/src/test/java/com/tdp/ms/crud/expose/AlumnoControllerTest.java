package com.tdp.ms.crud.expose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdp.ms.crud.business.entity.Alumno;
import com.tdp.ms.crud.business.interactors.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.*;

@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlumnoService alumnoService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void actualizarAlumno() throws Exception {

        //Given
        Alumno alumno = new Alumno();
        alumno.setNombre("David");
        alumno.setApellido("Lache");
        alumno.setEmail("dlache@gmail.com");
        when(alumnoService.save(any())).then(invocation ->{
            Alumno a = invocation.getArgument(0);
            a.setId(3L);
            return a;
        });

        //when
        mvc.perform(post("/api/v1/alumno").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alumno)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.nombre", is("David")))
                .andExpect(jsonPath("$.apellido", is("Lache")))
                .andExpect(jsonPath("$.email", is("dlache@gmail.com")));
        verify(alumnoService).save(any());

    }
}