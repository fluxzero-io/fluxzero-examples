package com.example.todo

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TodoApp {
    companion object {
        private val log = LoggerFactory.getLogger(TodoApp::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TodoApp::class.java, *args)
            log.info("Todo Application started successfully")
        }
    }
}