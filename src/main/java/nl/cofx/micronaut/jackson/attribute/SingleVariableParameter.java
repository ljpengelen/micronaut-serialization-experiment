package nl.cofx.micronaut.jackson.attribute;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SingleVariableParameter implements Parameter {

    String variableId;
}
