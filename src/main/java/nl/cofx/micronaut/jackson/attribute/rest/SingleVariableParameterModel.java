package nl.cofx.micronaut.jackson.attribute.rest;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class SingleVariableParameterModel implements ParameterModel {

    String variableId;
}
