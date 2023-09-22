package nl.cofx.micronaut.jackson.attribute;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleVariableParameter.class),
        @JsonSubTypes.Type(value = DoubleVariableParameter.class)
})
public interface Parameter {}
