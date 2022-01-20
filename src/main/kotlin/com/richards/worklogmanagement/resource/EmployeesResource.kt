package com.richards.worklogmanagement.resource

import com.richards.worklogmanagement.converter.EmployeeConverter
import com.richards.worklogmanagement.service.EmployeeService
import io.swagger.api.EmployeesApi
import io.swagger.model.Employee
import io.swagger.model.EmployeesPaginated
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/employees")
class EmployeesResource(
    private val employeeService: EmployeeService,
    private val employeeConverter: EmployeeConverter
) : EmployeesApi {

    @GetMapping
    override fun retrieveEmployees(
        @RequestParam("page") page: Int,
        @RequestParam("per_page", defaultValue = "5") perPage: Int
    ): ResponseEntity<EmployeesPaginated> {
        val employeesFound = employeeService.retrieveEmployees(PageRequest.of(page, perPage))
        val employeesPaginated = EmployeesPaginated().apply {
            this.content = employeeConverter.toEmployeesDTO(employeesFound.content)
            this.page = page
            this.perPage = perPage
        }

        return ResponseEntity.ok(employeesPaginated)
    }

    @PostMapping
    override fun registerEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        val employeeConverted = employeeConverter.toEmployee(employee)
        val savedEmployee = employeeService.saveEmployee(employeeConverted)
        return ResponseEntity.ok(employeeConverter.toEmployeeDTO(savedEmployee))
    }

    @PutMapping
    override fun updateEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        val employeeConverted = employeeConverter.toEmployee(employee)
        val updatedEmployee = employeeService.updateEmployee(employeeConverted)
        return ResponseEntity.ok(employeeConverter.toEmployeeDTO(updatedEmployee))
    }
}
