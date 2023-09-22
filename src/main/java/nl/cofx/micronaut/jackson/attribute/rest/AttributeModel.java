package nl.cofx.micronaut.jackson.attribute.rest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class AttributeModel {

    String id;

    String category;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            visible = true,
            property = "category"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SingleVariableParameterModel.class, name = "SINGLE"),
            @JsonSubTypes.Type(value = DoubleVariableParameterModel.class, name = "DOUBLE")
    })
    ParameterModel parameters;
}
