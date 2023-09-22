package nl.cofx.micronaut.jackson.attribute;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Builder;
import lombok.Value;

@Builder
@MappedEntity
@Value
public class Attribute {

    @Id
    String id;

    String category;

    Parameter parameters;
}
