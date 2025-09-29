package com.ty.Todolist.service;

import com.ty.Todolist.entity.Todo;
import com.ty.Todolist.entity.User;
import com.ty.Todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodosByUser(User user) {
        return todoRepository.findByUser(user);
    }

    public Todo createTodoForUser(Todo todo, User user) {
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTask(updatedTodo.getTask());
            todo.setCompleted(updatedTodo.isCompleted());
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
