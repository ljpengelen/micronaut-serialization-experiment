package nl.cofx.micronaut.jackson.asset;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import nl.cofx.micronaut.jackson.attribute.AttributeClient;
import nl.cofx.micronaut.jackson.attribute.rest.AttributeModel;
import nl.cofx.micronaut.jackson.attribute.rest.DoubleVariableParameterModel;
import nl.cofx.micronaut.jackson.attribute.rest.SingleVariableParameterModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@MicronautTest
@MockServerSettings(ports = 8080)
class AttributeClientTest {

    private static final String VARIABLE_ID = "variableId";
    private static final String ATTRIBUTE_ID = "attributeId";
    private static final String CATEGORY_SINGLE = "SINGLE";
    private static final String CATEGORY_DOUBLE = "DOUBLE";

    @Inject
    private AttributeClient attributeClient;

    @BeforeEach
    void setUp(MockServerClient client) {
        client.reset();
    }

    @Test
    void returnsAttributeWithCategorySingle(MockServerClient client) {
        client.when(request()).respond(response("""
                {
                    "id": "attributeId",
                    "category": "SINGLE",
                    "parameters": {
                        "variableId": "variableId"
                    }
                }""").withContentType(MediaType.APPLICATION_JSON));

        var optionalAttribute = attributeClient.get(ATTRIBUTE_ID);

        assertThat(optionalAttribute).isPresent();
        assertThat(optionalAttribute.get()).isEqualTo(AttributeModel.builder()
                .id(ATTRIBUTE_ID)
                .category(CATEGORY_SINGLE)
                .parameters(SingleVariableParameterModel.builder()
                        .variableId(VARIABLE_ID)
                        .build())
                .build());
    }

    @Test
    void returnsAttributeWithCategoryDouble(MockServerClient client) {
        client.when(request()).respond(response("""
                {
                    "id": "attributeId",
                    "category": "DOUBLE",
                    "parameters": {
                        "firstVariableId": "firstVariableId",
                        "secondVariableId": "secondVariableId"
                    }
                }""").withContentType(MediaType.APPLICATION_JSON));

        var optionalAttribute = attributeClient.get(ATTRIBUTE_ID);

        assertThat(optionalAttribute).isPresent();
        assertThat(optionalAttribute.get()).isEqualTo(AttributeModel.builder()
                .id(ATTRIBUTE_ID)
                .category(CATEGORY_DOUBLE)
                .parameters(DoubleVariableParameterModel.builder()
                        .firstVariableId("firstVariableId")
                        .secondVariableId("secondVariableId")
                        .build())
                .build());
    }
}
