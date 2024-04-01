package in.nithya.springbootmongodb.repository;
 import in.nithya.springbootmongodb.model.TodoDTO;
 import org.springframework.data.mongodb.repository.MongoRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO,String> {

}
