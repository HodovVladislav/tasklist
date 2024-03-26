package com.example.tasklist.service;

import com.example.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(long id);

    List<Task> getAllByUserId(long id);

    Task update(Task task);

    Task create(Task task, long userId);

    void delete(long id);

}
