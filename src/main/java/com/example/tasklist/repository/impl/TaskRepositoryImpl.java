package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.exceptions.ResourceMappingException;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.DataSourceConfig;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM tasks t
            WHERE t.id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM tasks t
                     JOIN users_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = ?
            """;

    private final String ASSIGN_TO_USER_BY_ID = """
            INSERT INTO users_tasks (task_id, user_id)
            VALUES (?, ?)
            """;

    private final String UPDATE = """
            UPDATE tasks
            SET title = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO tasks (title, description, expiration_date, status)
            VALUES (?, ?, ?, ?)
            """;

    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?
            """;


    @Override
    public Optional<Task> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot find task by id");
        }
    }

    @Override
    public List<Task> findAllByUserId(long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot find tasks by user id");
        }
    }

    @Override
    public void assignToUserById(long taskId, long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_USER_BY_ID);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot assign task to user by id");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);

            statement.setString(1, task.getTitle());
            if (task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else statement.setString(2, task.getDescription());
            if (task.getExpirationDate() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));

            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot update task");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, task.getTitle());
            if (task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else statement.setString(2, task.getDescription());
            if (task.getExpirationDate() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));

            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot create task");
        }
    }

    @Override
    public void delete(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ResourceMappingException("Cannot delete task");
        }
    }

}
