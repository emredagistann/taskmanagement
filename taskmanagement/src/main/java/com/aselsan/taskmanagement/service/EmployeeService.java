package com.aselsan.taskmanagement.service;

import org.springframework.stereotype.Service;

import com.aselsan.taskmanagement.employee.Employee;
import com.aselsan.taskmanagement.employee.NonPriorityEmployee;
import com.aselsan.taskmanagement.employee.PriorityEmployee;

@Service
public class EmployeeService {

	private TaskService taskService;

	private Employee priorityEmployee;

	private Employee nonPriorityEmployee;

	public EmployeeService(TaskService taskService, PriorityEmployee priorityEmployee,
			NonPriorityEmployee nonPriorityEmployee) {

		this.taskService = taskService;
		this.priorityEmployee = priorityEmployee;
		this.nonPriorityEmployee = nonPriorityEmployee;
	}

	public Employee getPriorityEmployee() {

		return priorityEmployee;
	}

	public Employee getNonPriorityEmployee() {

		return nonPriorityEmployee;
	}

	public void startWorking() {

		priorityEmployee.startWorking();
		nonPriorityEmployee.startWorking();
	}

	public void stopWorking() {

		priorityEmployee.stopWorking();
		nonPriorityEmployee.stopWorking();
	}

	public void resizeWorkers(int n) {

		priorityEmployee.resizeThreadPool(n);
		nonPriorityEmployee.resizeThreadPool(n);
	}
}
