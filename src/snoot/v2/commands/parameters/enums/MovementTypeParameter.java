package snoot.v2.commands.parameters.enums;

import snoot.v2.commands.parameters.enums.EnumParameter;
import snoot.v2.util.MovementType;

public class MovementTypeParameter extends EnumParameter<MovementType> {

    public MovementTypeParameter(String name, String description) {
        super(MovementType.class, name, description, "movement type", "walk/fly/both");
    }

}
