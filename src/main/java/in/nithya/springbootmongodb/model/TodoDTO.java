package in.nithya.springbootmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")
public class TodoDTO {

    @Id
    private String id;
    private String payment;
    private String description;
    private Boolean completed;
    private String username;
    private Date createdAt;
    private Date updatedAt;
    private String paymentMadeIn;
    private String role;
}
