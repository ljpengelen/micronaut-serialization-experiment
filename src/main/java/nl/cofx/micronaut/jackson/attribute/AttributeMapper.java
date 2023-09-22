package nl.cofx.micronaut.jackson.attribute;

import nl.cofx.micronaut.jackson.attribute.rest.AttributeModel;
import nl.cofx.micronaut.jackson.attribute.rest.DoubleVariableParameterModel;
import nl.cofx.micronaut.jackson.attribute.rest.ParameterModel;
import nl.cofx.micronaut.jackson.attribute.rest.SingleVariableParameterModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface AttributeMapper {

    AttributeModel map(Attribute attribute);

    Attribute map(AttributeModel attributeModel);

    default Parameter map(ParameterModel parameter) {
        if (parameter == null) return null;

        if (parameter instanceof SingleVariableParameterModel)
            return map((SingleVariableParameterModel) parameter);
        if (parameter instanceof DoubleVariableParameterModel)
            return map((DoubleVariableParameterModel) parameter);

        throw new IllegalStateException(String.format("Unexpected type %s", parameter.getClass()));
    }

    default ParameterModel map(Parameter parameter) {
        if (parameter == null) return null;

        if (parameter instanceof SingleVariableParameter)
            return map((SingleVariableParameter) parameter);
        if (parameter instanceof DoubleVariableParameter)
            return map((DoubleVariableParameter) parameter);

        throw new IllegalStateException(String.format("Unexpected type %s", parameter.getClass()));
    }

    SingleVariableParameterModel map(SingleVariableParameter parameter);

    SingleVariableParameter map(SingleVariableParameterModel parameter);

    DoubleVariableParameterModel map(DoubleVariableParameter parameter);

    DoubleVariableParameter map(DoubleVariableParameterModel parameter);
}
