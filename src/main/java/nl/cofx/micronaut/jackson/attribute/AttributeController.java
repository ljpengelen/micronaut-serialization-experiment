package nl.cofx.micronaut.jackson.attribute;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import nl.cofx.micronaut.jackson.attribute.rest.AttributeModel;

@Controller("/attribute")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeMapper attributeMapper;
    private final AttributeRepository attributeRepository;

    @Get("/{id}")
    public HttpResponse<AttributeModel> get(String id) {
        var optionalAttribute = attributeRepository.findById(id);
        if (optionalAttribute.isPresent()) return HttpResponse.ok(attributeMapper.map(optionalAttribute.get()));

        return HttpResponse.notFound();
    }

    @Post
    public HttpResponse<AttributeModel> create(@Body AttributeModel attribute) {
        var optionalExistingAttribute = attributeRepository.findById(attribute.getId());
        if (optionalExistingAttribute.isPresent()) return HttpResponse.badRequest();

        return HttpResponse.ok(attributeMapper.map(attributeRepository.save(attributeMapper.map(attribute))));
    }
}
