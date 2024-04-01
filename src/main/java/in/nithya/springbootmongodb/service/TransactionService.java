package in.nithya.springbootmongodb.service;

import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransactionService {
    @Autowired
    private TodoRepository todoRepository;

}
