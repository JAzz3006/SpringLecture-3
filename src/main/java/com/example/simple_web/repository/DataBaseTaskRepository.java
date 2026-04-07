package com.example.simple_web.repository;
import com.example.simple_web.Task;
import com.example.simple_web.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
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
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
