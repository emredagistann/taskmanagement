package com.aselsan.taskmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.aselsan.taskmanagement.queue.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.queue.Priority;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

@SpringBootTest
class EmployeeApplicationTests {

	@Test
	void testTaskMakingOrder() {

		ConcurrentPriorityQueue<ITask> tasks = new ConcurrentPriorityQueue<ITask>(new Task(2));

		Task task4 = new Task(4);
		Task task1 = new Task(1);
		Task task3 = new Task(3);
		Task task2 = new Task(2);

		// high priorities
		// 4, 3

		// low priorities
		// 2, 1

		tasks.add(task4);
		tasks.add(task1);
		tasks.add(task3);
		tasks.add(task2);

		Assertions.assertEquals(task4, tasks.poll(Priority.HIGH));

		Assertions.assertEquals(task2, tasks.poll(Priority.LOW));
	}
}
