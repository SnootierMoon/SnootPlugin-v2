package snoot.v2.commands.parameters.enums;

import snoot.v2.util.BinaryOption;

public class BinaryOptionParameter extends EnumParameter<BinaryOption> {

    public BinaryOptionParameter(String name, String description) {
        super(BinaryOption.class, name, description, "option", "on/off/toggle");
    }
}
