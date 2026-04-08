package com.example.simple_web.repository;
import com.example.simple_web.Task;
import com.example.simple_web.exception.TaskNotFoundException;
import com.example.simple_web.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
@Profile("v3")
public class DataBaseTaskRepository implements TaskRepository{

    private final JdbcTemplate jdbcTemplate; //через него будем работать с БД. Этот бин предоставляет подключенный стартер

    @Override
    public List<Task> findAll() {
        log.debug("Calling DataBaseTaskRepository -> findAll()");
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper()); //кроме запроса в метод кидаем специальный мэппер,
        // который из результатов sql-запроса будет создавать Task
        //см. com/example/simple_web/repository/mapper/TaskRowMapper
    }

    @Override
    public Optional<Task> findById(Long id) {
        log.debug("Calling DataBaseTaskRepository -> findById() with ID: {}", id);
        String sql = "SELECT * FROM task WHERE id = ?"; //это prepared statement запрос. Защищает от sql-инъекций.
        Task task = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}), // а это мы передаем аргументы для запроса
                        new RowMapperResultSetExtractor<>(new TaskRowMapper(), 1) //а это мы передаем наш мэппер
                )// запрос выдает коллекцию, для этого мы исп.
        ); // это специальный утильный класс DataAccessUtils.singleResult()
        return Optional.ofNullable(task);
    }

    @Override
    public Task save(Task task) {
        log.debug("Calling DataBaseTaskRepository -> save() with Task: {}", task);
        String sql = "INSERT INTO task(title, description, priority) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getPriority());
        return task;
    }

    @Override
    public Task update(Task task) {
        log.debug("Calling DataBaseTaskRepository -> update() with Task: {}", task);
        Task existed = findById(task.getId()).orElse(null);
        if (existed != null){
            String sql = "UPDATE task SET title = ?, description = ?, priority = ? WHERE id = ?";
            jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getPriority(), task.getId());
            return task;
        }
        log.warn("Task with ID {} not found", task.getId());
        throw new TaskNotFoundException("Task for update not found? ID: " + task.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DataBaseTaskRepository -> deleteById() with ID: {}", id);
        String sql = "DELETE FROM task where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        log.debug("Calling DataBaseTaskRepository -> batchInsert()");
        String sql = "INSERT INTO task (title, description, priority) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Task task = tasks.get(i);
                ps.setString(1, task.getTitle());
                ps.setString(2, task.getDescription());
                ps.setInt(3, task.getPriority());
                //ps.setLong(4, task.getId());
            }

            @Override
            public int getBatchSize() {
                return tasks.size();
            }
        });
    }
}
