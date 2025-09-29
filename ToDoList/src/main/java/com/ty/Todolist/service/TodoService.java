package com.ty.Todolist.service;

import com.ty.Todolist.entity.Todo;
import com.ty.Todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() { return todoRepository.findAll(); }

    public Todo createTodo(Todo todo) { return todoRepository.save(todo); }

    public Todo toggleTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) { todoRepository.deleteById(id); }
}
