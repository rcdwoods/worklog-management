package com.richards.worklogmanagement.repository

import com.richards.worklogmanagement.model.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>
