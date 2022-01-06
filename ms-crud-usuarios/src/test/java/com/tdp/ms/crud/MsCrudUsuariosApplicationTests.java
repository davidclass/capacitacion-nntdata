package com.tdp.ms.crud;

import com.tdp.ms.crud.business.interactors.impl.AlumnoServiceImpl;
import com.tdp.ms.crud.business.repository.AlumnoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsCrudUsuariosApplicationTests {

	@Mock
	AlumnoDao alumnoDao;

	@InjectMocks
	AlumnoServiceImpl alumnoService;

	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {

	}

}
