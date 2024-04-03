package in.nithya.springbootmongodb.controller;

import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import in.nithya.springbootmongodb.service.CurrencyConversionService;
import in.nithya.springbootmongodb.service.PaymentService;
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



    private final TodoRepository todoRepo;


    private final CurrencyConversionService conversionService;


    private final PaymentService paymentService;
    @Autowired
    public TodoController(TodoRepository todoRepo, CurrencyConversionService conversionService, PaymentService paymentService) {
        this.todoRepo = todoRepo;
        this.conversionService = conversionService;
        this.paymentService = paymentService;
    }


    @GetMapping("/todos")
    public ResponseEntity<?>getAllTodos(){
        List< TodoDTO> todos=todoRepo.findAll();
        if(todos.size()>0){
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No todos available",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todosPost")
    public ResponseEntity<?>createTodo(@RequestBody TodoDTO todo){
        try {
            double amountInINR = conversionService.convertToINR(todo.getPaymentMadeIn(), Double.parseDouble(todo.getPayment()));
            todo.setPayment(String.valueOf(amountInINR));
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id")String id){
        Optional<TodoDTO> todoOptional=todoRepo.findById(id);
        if(todoOptional.isPresent()){
            return new ResponseEntity<>(todoOptional.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Todo not found with id "+id,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todosu/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id")String id,@RequestBody TodoDTO todo){
        Optional<TodoDTO> todoOptional=todoRepo.findById(id);
        if(todoOptional.isPresent()){
            TodoDTO todoToSave=todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted(): todoToSave.getCompleted());
            todoToSave.setPayment(todo.getCompleted()!=null? todo.getPayment():todoToSave.getPayment());
            todoToSave.setDescription(todo.getDescription()!=null? todo.getDescription():todoToSave.getDescription());
            todoToSave.setUsername(todo.getUsername()!=null? todo.getUsername():todoToSave.getUsername());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave,HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Todo not found with id "+id,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todosd/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try{
            todoRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted with id "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @GetMapping("/totalPayments")
    public ResponseEntity<Double> getTotalPayments() {
        double totalPayments = paymentService.getTotalPayments();
        return new ResponseEntity<>(totalPayments, HttpStatus.OK);
    }

}
