package com.tdp.ms.crud.business.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoTest {

    @Test
    void testNombreAlumno(){
        Alumno alumno = new Alumno();
        alumno.setNombre("David");
        String esperado = "David";
        String real = alumno.getNombre();
        assertEquals(esperado, real);
    }

    @Test
    void testApellidoAlumno(){
        Alumno alumno = new Alumno();
        alumno.setApellido("Lache");
        String esperado = "Lache";
        String real = alumno.getApellido();
        assertEquals(esperado, real);
    }

    @Test
    void testEmailAlumno(){
        Alumno alumno = new Alumno();
        alumno.setEmail("dlache@gmail.com");
        String esperado = "dlache@gmail.com";
        String real = alumno.getEmail();
        assertEquals(esperado, real);
    }

    @Test
    void testCreateAtAlumno(){
        Alumno alumno = new Alumno();
        alumno.setCreateAt(new Date(2022,1,5));
        Date esperado = new Date(2022,1,5);
        Date real = alumno.getCreateAt();
        assertEquals(esperado, real);
    }


}