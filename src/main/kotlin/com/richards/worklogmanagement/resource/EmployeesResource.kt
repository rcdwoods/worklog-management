package com.richards.worklogmanagement.resource

import com.richards.worklogmanagement.service.EmployeeService
import io.swagger.api.EmployeesApi
import io.swagger.model.Employee
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeesResource(
    private val employeeService: EmployeeService
) : EmployeesApi {

    @GetMapping
    override fun retrieveEmployees(
        @RequestParam page: Long,
        @RequestParam size: Long
    ): ResponseEntity<List<Employee>> =
        employeeService.retrieveEmployees()

}
