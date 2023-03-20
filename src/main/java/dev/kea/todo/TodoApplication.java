package dev.kea.todo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@SpringBootApplication
@RestController
public class TodoApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Todo App";
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Todo> todos = jdbcTemplate.query("SELECT * FROM todo", new TodoRowMapper());
        return todos;
    }

    @PostMapping("/todos")
    public void createTodo(@RequestBody Todo todo) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO todo (title, description) VALUES (?, ?)", 
                            todo.getTitle(), todo.getDescription());
    }

    public static class Todo {
        private int id;
        private String title;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet resultSet, int i) throws SQLException {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setTitle(resultSet.getString("title"));
            todo.setDescription(resultSet.getString("description"));
            return todo;
        }
    }

}
