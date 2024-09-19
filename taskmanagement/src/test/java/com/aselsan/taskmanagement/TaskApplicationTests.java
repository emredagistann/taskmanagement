package com.aselsan.taskmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.aselsan.taskmanagement.queue.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

@SpringBootTest
class TaskApplicationTests {

	private ConcurrentPriorityQueue<ITask> queue = new ConcurrentPriorityQueue<>(new Task(2));

	@Test
	void testQueuePriorityOrder() {

		Task t1 = new Task(0);
		queue.add(t1);
		String queueString = queue.stringifyQueue();
		Assertions.assertEquals(t1.toString(), queueString);

		Task t2 = new Task(5);
		queue.add(t2);
		queueString = queue.stringifyQueue();
		Assertions.assertEquals(t2 + ", " + t1, queueString);

		Task t3 = new Task(2);
		queue.add(t3);
		queueString = queue.stringifyQueue();
		Assertions.assertEquals(t2 + ", " + t3 + ", " + t1, queueString);
	}
}
