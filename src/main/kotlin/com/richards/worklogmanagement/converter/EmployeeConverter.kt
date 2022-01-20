package com.richards.worklogmanagement.converter

import com.richards.worklogmanagement.model.Employee
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EmployeeConverter {
    fun convert(employee: Employee): io.swagger.model.Employee
    fun convert(employees: List<Employee>): List<io.swagger.model.Employee>
}
