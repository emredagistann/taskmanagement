package com.aselsan.taskmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aselsan.taskmanagement.queue.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

@Service
public class TaskService {

	private ConcurrentPriorityQueue<ITask> tasks;

	public TaskService(@Value("${taskmanagement.task.priorityBorderValue}") int highestLowPriorityValue) {

		// above highestLowPriorityValue is considered as HIGH PRIORITY TASK otherwise
		// LOW PRIORITY TASK
		tasks = new ConcurrentPriorityQueue<>(new Task(highestLowPriorityValue));
		
//		new Thread(() -> {
//
//			List<Integer> list = new ArrayList<>();
//			
//			while(true) {
//				
//				list.add(tasks.getSize());
//				
//				list.forEach(v -> System.out.print(v + ", "));
//				System.out.println();
//				
//				try {
//					Thread.sleep(1000L);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}).start();
	}

	public ConcurrentPriorityQueue<ITask> getTasks() {

		return tasks;
	}
}
