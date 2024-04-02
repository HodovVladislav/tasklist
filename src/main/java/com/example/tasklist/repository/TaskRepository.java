package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

    Optional<Task> findById(long id);

    List<Task> findAllByUserId(long userId);

    void assignToUserById(@Param("taskId") long taskId,
                          @Param("userId") long userId);

    void update(Task task);

    void create(Task task);

    void delete(long id);

}
