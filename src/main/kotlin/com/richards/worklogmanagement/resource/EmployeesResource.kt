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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeesResource(
    private val employeeService: EmployeeService,
    private val employeeConverter: EmployeeConverter
) : EmployeesApi {

    override fun retrieveEmployees(): ResponseEntity<EmployeesPaginated> {
        val pageRequest = PageRequest.of(0, 0)
        val employeesFound = employeeService.retrieveEmployees(pageRequest)
        val employeesPaginated = EmployeesPaginated().apply {
            this.content = employeeConverter.convert(employeesFound.content)
            this.page = pageRequest.pageNumber
            this.perPage = pageRequest.pageSize
        }

        return ResponseEntity.ok(employeesPaginated)
    }

}
