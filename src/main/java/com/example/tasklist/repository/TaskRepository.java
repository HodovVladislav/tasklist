package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(long id);

    List<Task> findAllByUserId(long userId);

    void assignToUserById(long taskId, long userId);

    void update(Task task);

    void create(Task task);

    void delete(long id);

}
