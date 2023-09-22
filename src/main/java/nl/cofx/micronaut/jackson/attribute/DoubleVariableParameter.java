package nl.cofx.micronaut.jackson.attribute;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DoubleVariableParameter implements Parameter {

    String firstVariableId;
    String secondVariableId;
}
