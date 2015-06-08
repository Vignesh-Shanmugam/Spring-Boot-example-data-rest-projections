package com.emc.api.coe.sample.repository;

import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.emc.api.coe.sample.entity.Employee;

@Configuration
public class EmployeeRepositoryConfig extends
		SpringBootRepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.exposeIdsFor(Employee.class);
	}

}
