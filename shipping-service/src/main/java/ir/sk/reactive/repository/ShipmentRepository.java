package ir.sk.reactive.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ir.sk.domain.Shipment;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, ObjectId> {

}
