package com.richards.worklogmanagement.service.impl

import com.richards.worklogmanagement.model.Employee
import com.richards.worklogmanagement.repository.EmployeeRepository
import com.richards.worklogmanagement.service.EmployeeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository
) : EmployeeService {

    override fun saveEmployee(employee: Employee): Employee =
        employeeRepository.save(employee)

    override fun retrieveEmployees(pageable: Pageable): Page<Employee> =
        employeeRepository.findAll(pageable)

    override fun updateEmployee(employee: Employee): Employee =
        employeeRepository.save(employee)

}
