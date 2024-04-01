package in.nithya.springbootmongodb.service;

import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private TodoRepository todoRepository;

    public double getTotalPayments() {
        List<TodoDTO> payments = todoRepository.findAll();
        double total = (double) 0;
        for (TodoDTO payment : payments) {
            total += Double.parseDouble(payment.getPayment());
        }
        return total;
    }

}
