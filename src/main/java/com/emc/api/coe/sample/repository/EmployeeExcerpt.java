package com.emc.api.coe.sample.repository;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.emc.api.coe.sample.entity.Employee;

@Projection(name = "employee", types = Employee.class)
public interface EmployeeExcerpt {

	long getEmployeeId();

	String getFirstName();

	Date getDoj();

}
