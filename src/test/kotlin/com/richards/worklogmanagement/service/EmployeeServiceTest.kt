package com.richards.worklogmanagement.service

import com.richards.worklogmanagement.model.Employee
import com.richards.worklogmanagement.repository.EmployeeRepository
import com.richards.worklogmanagement.service.impl.EmployeeServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ContextConfiguration(
    classes = [
        EmployeeServiceImpl::class
    ]
)
@ExtendWith(SpringExtension::class)
class EmployeeServiceTest {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @MockBean
    private lateinit var employeeRepository: EmployeeRepository

    private val defaultPageable = PageRequest.of(0, 5)

    @BeforeEach
    fun setup() {
        val savedEmployees = listOf(
            factoryEmployee(1L, "Employee 01"),
            factoryEmployee(2L, "Employee 02"),
            factoryEmployee(3L, "Employee 03"),
            factoryEmployee(4L, "Employee 04")
        )

        Mockito.`when`(employeeRepository.save(Mockito.any())).thenAnswer { it.getArgument(0) }
        Mockito.`when`(employeeRepository.findAll(defaultPageable)).thenReturn(PageImpl(savedEmployees, defaultPageable, 5))
        savedEmployees.forEach { Mockito.`when`(employeeRepository.findById(it.id)).thenReturn(Optional.of(it)) }
    }

    @Test
    fun `must save user when user does not exist in the repository`() {
        val employee = Employee(name = "Richard", email = "richard@richards.com")
        val savedEmployee = employeeService.saveEmployee(employee)

        Assertions.assertThat(savedEmployee).isEqualTo(employee)
    }

    @Test
    fun `must not save user and throw exception when user already exists in the repository`() {
        val employee = factoryEmployee(2L, "Employee 02")
        val savedEmployee = employeeService.saveEmployee(employee)

        Assertions.assertThat(savedEmployee).isEqualTo(employee)
    }

    @Test
    fun `must return all employees paginated`() {
        val employeesFound = employeeService.retrieveEmployees(defaultPageable)

        Assertions.assertThat(employeesFound.content).hasSize(4)
    }

    @Test
    fun `must update user when user exists in the repository`() {
        val employee = factoryEmployee(2L, "New Employee 02")
        val updatedEmployee = employeeService.updateEmployee(employee)

        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee)
        Assertions.assertThat(updatedEmployee.email).isEqualTo("newemployee02@richards.com")
    }

    @Test
    fun `must not update user when user exists in the repository but has no differences`() {
        val employee = factoryEmployee(2L, "Employee 02")
        val updatedEmployee = employeeService.updateEmployee(employee)

        Mockito.verify(employeeRepository, Mockito.times(0)).save(updatedEmployee)
    }

    @Test
    fun `must not update user and throw exception when user does not exist in the repository`() {
        val employee = factoryEmployee(7L, "Employee 07")

        assertThrows<IllegalArgumentException> {
            employeeService.updateEmployee(employee)
        }
    }

    @Test
    fun `must throw exception when user does not exist in the repository`() {
        assertThrows<IllegalArgumentException> {
            employeeService.retrieveEmployeeById(5L)
        }
    }

    @Test
    fun `must delete employee when employee exists`() {
        employeeService.deleteEmployeeById(4L)

        Mockito.verify(employeeRepository, Mockito.times(1)).delete(Mockito.any())
    }

    @Test
    fun `must not delete and throw Exception employee when employee does not exist`() {
        assertThrows<IllegalArgumentException> {
            employeeService.retrieveEmployeeById(5L)
        }
    }

    fun factoryEmployee(id: Long, name: String) = Employee(
        id = id,
        name = name,
        email = name.lowercase().filter { !it.isWhitespace() } + "@richards.com"
    )
}
