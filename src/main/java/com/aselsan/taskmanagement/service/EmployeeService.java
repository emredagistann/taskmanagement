package com.aselsan.taskmanagement.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.aselsan.taskmanagement.employee.Employee;
import com.aselsan.taskmanagement.employee.NonPriorityEmployee;
import com.aselsan.taskmanagement.employee.PriorityEmployee;

@Service
public class EmployeeService {

	private TaskService taskService;

	private Employee priorityEmployee;

	private Employee nonPriorityEmployee;
	
	private AtomicBoolean autoResize = new AtomicBoolean(false);
	
	private AtomicInteger workerThreadSize = new AtomicInteger(2);

	private Thread resizeWorkersThread;
	
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
	
	private void autoResizeWorkers() {
		
		resizeWorkersThread = new Thread(() -> {
			
			while(autoResize.get()) {
				
				int taskSize = taskService.getTasks().getSize();
				
				int workerSize = Math.max(1, taskSize / 20);
				
				resizeWorkers(workerSize);
				
				try {
				
					Thread.sleep(5_000L);

				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
		});
		
		resizeWorkersThread.start();
	}
	
	public void resizeThreadPool(int n) {
		
		if(n == 0) {
			
			autoResize.set(true);
			autoResizeWorkers();
		
		} else {
			
			autoResize.set(false);
			resizeWorkers(n);
		}
	}

	public void resizeWorkers(int n) {

		if(resizeWorkersThread != null && resizeWorkersThread.isAlive()) {
			
			resizeWorkersThread.interrupt();
		
			try {
				
				resizeWorkersThread.join();
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		workerThreadSize.set(n);
		
		priorityEmployee.resizeThreadPool(n);
		nonPriorityEmployee.resizeThreadPool(n);
		
		priorityEmployee.startWorking();
		nonPriorityEmployee.startWorking();
	}
	
	public int getWorkerThreadSize() {
	
		return workerThreadSize.get();
	}
}
