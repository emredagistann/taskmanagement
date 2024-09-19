package com.aselsan.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aselsan.taskmanagement.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/start")
	public ResponseEntity<String> startTasks() {

		employeeService.startWorking();

		return ResponseEntity.ok("Employees started working.");
	}

	@GetMapping("/stop")
	public ResponseEntity<String> stopTasks() {

		employeeService.stopWorking();

		return ResponseEntity.ok("Employees stopped working.");
	}
	
	@GetMapping("/size")
	public ResponseEntity<String> getSize() {

		return ResponseEntity.ok("Thread pool size: " + employeeService.getWorkerThreadSize());
	}
	
	@GetMapping
	public ResponseEntity<String> resizeThreadPool(@RequestParam int size) {

		employeeService.resizeWorkers(size);

		if(size == 0) {

			return ResponseEntity.ok("Thread pool resizing automatically.");
			
		} else {
			
			return ResponseEntity.ok("Thread pool resized to: " + size);
		}
		
	}
}
