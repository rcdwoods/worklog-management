package com.richards.worklogmanagement.converter

import com.richards.worklogmanagement.model.Employee
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EmployeeConverter {
    fun toEmployeeDTO(employee: Employee): io.swagger.model.Employee
    fun toEmployeesDTO(employees: List<Employee>): List<io.swagger.model.Employee>
    fun toEmployee(employee: io.swagger.model.Employee): Employee
    fun toEmployees(employees: List<io.swagger.model.Employee>): List<Employee>
}
