package nl.cofx.micronaut.jackson.attribute;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import nl.cofx.micronaut.jackson.attribute.rest.AttributeModel;

import java.util.Optional;

@Client("${services.attribute-service.url}")
public interface AttributeClient {

    @Get("/attribute/{id}")
    Optional<AttributeModel> get(String id);

    @Post("/attribute")
    AttributeModel create(@Body AttributeModel attributeModel);
}
