package nl.cofx.micronaut.jackson.attribute;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import nl.cofx.micronaut.jackson.attribute.rest.AttributeModel;
import nl.cofx.micronaut.jackson.attribute.rest.SingleVariableParameterModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MicronautTest(transactional = false)
class AttributeControllerTest {

    private static final String ATTRIBUTE_ID = "attributeId";
    private static final String VARIABLE_ID = "variableId";
    private static final String CATEGORY = "SINGLE";
    private static final AttributeModel ATTRIBUTE_MODEL = AttributeModel.builder()
            .id(ATTRIBUTE_ID)
            .category(CATEGORY)
            .parameters(SingleVariableParameterModel.builder()
                    .variableId(VARIABLE_ID)
                    .build())
            .build();
    private static final Attribute ATTRIBUTE = Attribute.builder()
            .id(ATTRIBUTE_ID)
            .category(CATEGORY)
            .parameters(SingleVariableParameter.builder()
                    .variableId(VARIABLE_ID)
                    .build())
            .build();

    @Inject
    @Client("/attribute")
    private HttpClient httpClient;

    private BlockingHttpClient blockingHttpClient;

    @BeforeEach
    void setUp() {
        blockingHttpClient = httpClient.toBlocking();
        attributeRepository.deleteAll();
    }

    @Inject
    private AttributeRepository attributeRepository;

    @Test
    void returnsAttribute() {
        attributeRepository.save(ATTRIBUTE);

        var response = blockingHttpClient.exchange(HttpRequest.GET(ATTRIBUTE_ID), AttributeModel.class);

        assertThat((CharSequence) response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.body()).isEqualTo(ATTRIBUTE_MODEL);
    }

    @Test
    void returns404_givenNonExistingAttributeId() {
        assertThatThrownBy(() -> blockingHttpClient.exchange(HttpRequest.GET(ATTRIBUTE_ID), AttributeModel.class))
                .isInstanceOf(HttpClientResponseException.class)
                .hasMessageContaining("Not Found");
    }

    @Test
    void createsAttribute() {
        var response = blockingHttpClient.exchange(HttpRequest.POST("", ATTRIBUTE_MODEL), AttributeModel.class);

        assertThat((CharSequence) response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.body()).isEqualTo(ATTRIBUTE_MODEL);

        var optionalAttribute = attributeRepository.findById(ATTRIBUTE_ID);
        assertThat(optionalAttribute).isPresent();
        assertThat(optionalAttribute.get()).isEqualTo(ATTRIBUTE);
    }
}
