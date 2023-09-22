package nl.cofx.micronaut.jackson.attribute;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(transactional = false)
class AttributeRepositoryTest {

    private static final String VARIABLE_ID = "variableId";
    private static final Attribute ATTRIBUTE_WITH_CATEGORY_SINGLE = Attribute.builder()
            .id("singleId")
            .category("SINGLE")
            .parameters(SingleVariableParameter.builder()
                    .variableId(VARIABLE_ID)
                    .build())
            .build();
    private static final Attribute ATTRIBUTE_CATEGORY_DOUBLE = Attribute.builder()
            .id("doubleId")
            .category("DOUBLE")
            .parameters(DoubleVariableParameter.builder()
                    .firstVariableId("firstVariableId")
                    .secondVariableId("secondVariableId")
                    .build())
            .build();

    @Inject
    private AttributeRepository attributeRepository;

    @Inject
    private MongoClient mongoClient;

    @BeforeEach
    void setUp() {
        attributeRepository.deleteAll();
    }

    @Test
    void savesEntityWithCategorySingle() {
        var attribute = attributeRepository.save(ATTRIBUTE_WITH_CATEGORY_SINGLE);
        assertThat(attribute).isEqualTo(ATTRIBUTE_WITH_CATEGORY_SINGLE);
        assertThat(attributeRepository.findAll()).containsExactly(ATTRIBUTE_WITH_CATEGORY_SINGLE);

        assertThat(attributesAsDocuments()).allSatisfy(document ->
                assertThat(document.toJson()).isEqualToIgnoringWhitespace("""
                        {
                            "_id": "singleId",
                            "category": "SINGLE",
                            "parameters": {
                                "@type": "SingleVariableParameter",
                                "variableId": "variableId"
                            }
                        }"""));
    }

    private FindIterable<Document> attributesAsDocuments() {
        return mongoClient.getDatabase("test").getCollection("attribute").find();
    }

    @Test
    void savesEntityWithCategoryDouble() {
        var attribute = attributeRepository.save(ATTRIBUTE_CATEGORY_DOUBLE);
        assertThat(attribute).isEqualTo(ATTRIBUTE_CATEGORY_DOUBLE);
        assertThat(attributeRepository.findAll()).containsExactly(ATTRIBUTE_CATEGORY_DOUBLE);

        assertThat(attributesAsDocuments()).allSatisfy(document ->
                assertThat(document.toJson()).isEqualToIgnoringWhitespace("""
                        {
                            "_id": "doubleId",
                            "category": "DOUBLE",
                            "parameters": {
                                "@type": "DoubleVariableParameter",
                                "firstVariableId": "firstVariableId",
                                "secondVariableId": "secondVariableId"
                            }
                        }"""));
    }
}
