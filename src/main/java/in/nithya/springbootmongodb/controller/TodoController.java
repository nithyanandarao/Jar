//Contains all the endpoints for the transactional api
package in.nithya.springbootmongodb.controller;
import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import in.nithya.springbootmongodb.service.CurrencyConversionService;
import in.nithya.springbootmongodb.service.PaymentService;
import in.nithya.springbootmongodb.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class TodoController {


    private final TodoService todoService;

    private final PaymentService paymentService;
    @Autowired
    public TodoController(TodoRepository todoRepo, CurrencyConversionService conversionService, TodoService todoService, PaymentService paymentService) {

        this.todoService = todoService;
        this.paymentService = paymentService;
    }


    @GetMapping("/todos")
    public ResponseEntity<?>getAllTodos(){
        return todoService.getAllTodos();
    }

    @PostMapping("/todosPost")
    public ResponseEntity<?>createTodo(@RequestBody TodoDTO todo){
        return todoService.createTodo(todo);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id")String id){
        return todoService.getSingleTodoById(id);
    }

    @PutMapping("/todosu/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id")String id,@RequestBody TodoDTO todo){
        return todoService.updateTodoById(id, todo);

    }

    @DeleteMapping("/todosd/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        return todoService.deleteTodoById(id);
    }

    @GetMapping("/totalPayments")//returns the total sum of the all amounts in the database
    public ResponseEntity<Double> getTotalPayments() {
        double totalPayments = paymentService.getTotalPayments();
        return new ResponseEntity<>(totalPayments, HttpStatus.OK);
    }

}
