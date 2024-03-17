package com.example.tasklist.service.impl;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public Task getById(long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserId(long id) {
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task, long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

}
