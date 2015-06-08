package com.emc.api.coe.sample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.emc.api.coe.sample.entity.Employee;

@RepositoryRestResource(excerptProjection = EmployeeExcerpt.class, path = "employee")
public interface EmployeeRepository extends
		PagingAndSortingRepository<Employee, Long> {

}
