package com.richards.worklogmanagement.service.impl

import com.richards.worklogmanagement.model.Employee
import com.richards.worklogmanagement.repository.EmployeeRepository
import com.richards.worklogmanagement.service.EmployeeService
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository
) : EmployeeService {

    override fun saveEmployee(employee: Employee): Employee =
        employeeRepository.save(employee)

    override fun retrieveEmployees(pageable: Pageable): Page<Employee> =
        employeeRepository.findAll(pageable)

    override fun retrieveEmployeeById(id: Long): Employee {
        val employeeFound = employeeRepository.findById(id)

        if (employeeFound.isEmpty)
            throw IllegalArgumentException("Employee not found.")

        return employeeFound.get()
    }

    override fun updateEmployee(employee: Employee): Employee {
        val employeeFound = retrieveEmployeeById(employee.id!!)

        return when (employeeFound != employee) {
            true -> employeeRepository.save(employee)
            false -> employee
        }
    }

}
