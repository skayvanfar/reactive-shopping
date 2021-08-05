package ir.sk.reactive.repository;

import ir.sk.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId> {

}
