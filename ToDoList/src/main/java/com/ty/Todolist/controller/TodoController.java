package com.ty.Todolist.controller;

import com.ty.Todolist.entity.Todo;
import com.ty.Todolist.entity.User;
import com.ty.Todolist.service.TodoService;
import com.ty.Todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public String getTodos(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Todo> todos = todoService.getTodosByUser(user);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @PostMapping
    public String createTodo(@RequestParam("task") String task, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = new Todo(task, user);
        todoService.createTodoForUser(todo, user);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        todoService.updateTodo(id, todo);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }
}
