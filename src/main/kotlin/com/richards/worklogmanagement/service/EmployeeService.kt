package com.richards.worklogmanagement.service

import com.richards.worklogmanagement.model.Employee
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface EmployeeService {
    fun saveEmployee(employee: Employee): Employee
    fun updateEmployee(employee: Employee): Employee
    fun retrieveEmployees(pageable: Pageable): Page<Employee>
}
