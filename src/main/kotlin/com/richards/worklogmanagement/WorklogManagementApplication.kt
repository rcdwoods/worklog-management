package com.richards.worklogmanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class WorklogManagementApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<WorklogManagementApplication>(*args)
        }
    }
}
