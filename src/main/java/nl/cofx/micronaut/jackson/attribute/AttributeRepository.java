package nl.cofx.micronaut.jackson.attribute;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
public interface AttributeRepository extends CrudRepository<Attribute, String> {}
