package in.nithya.springbootmongodb.service;

import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class TodoService {

    private final TodoRepository todoRepo;


    private final CurrencyConversionService currencyConversionService;
    @Autowired
    public TodoService(TodoRepository todoRepo, CurrencyConversionService currencyConversionService) {
        this.todoRepo = todoRepo;
        this.currencyConversionService = currencyConversionService;
    }

    public ResponseEntity<?> updateTodoById(String id, TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setPayment(todo.getCompleted() != null ? todo.getPayment() : todoToSave.getPayment());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUsername(todo.getUsername() != null ? todo.getUsername() : todoToSave.getUsername());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (!todos.isEmpty()) {
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No entries available", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> createTodo(TodoDTO todo) {
        try {
            double amountInINR = currencyConversionService.convertToINR(todo.getPaymentMadeIn(), Double.parseDouble(todo.getPayment()));
            todo.setPayment(String.valueOf(amountInINR));
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSingleTodoById(String id) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteTodoById(String id) {
        try {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
