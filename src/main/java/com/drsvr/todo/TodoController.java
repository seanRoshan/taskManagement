package com.drsvr.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@SessionAttributes("name")
@Controller 
public class TodoController {
	
	@Autowired
	TodoService service;
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
	@RequestMapping(value = "/todo-list", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		model.addAttribute("todos", service.retrieveTodos(retrievedLoggedinUser()));
		return "todo-list";
	}

	
	private String retrievedLoggedinUser() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showaddTodos(ModelMap model) {
		model.addAttribute("todo",new Todo(0,"","Default Description",new Date(),false));
		return "todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodos(ModelMap model, @Valid Todo todo, BindingResult result ) {

		if (result.hasErrors()) {
			return "todo";
		}
		else {
			service.addTodo(retrievedLoggedinUser(), todo.getDesc(), todo.getTargetDate(), false);
			model.clear();
			return "redirect:todo-list";
		}
	}
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodos(ModelMap model, @RequestParam int id) {
		service.deleteTodo(id);
		model.clear();
		return "redirect:todo-list";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(ModelMap model, @RequestParam int id) {
		model.addAttribute("todo", service.retrieveTodo(id));
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo,
			BindingResult result) {
		if (result.hasErrors())
			return "todo";

		todo.setUser(retrievedLoggedinUser()); //TODO:Remove Hardcoding Later
		//todo.setTargetDate(new Date());
		service.updateTodo(todo);

		model.clear();// to prevent request parameter "name" to be passed
		return "redirect:todo-list";
	}
	
}
