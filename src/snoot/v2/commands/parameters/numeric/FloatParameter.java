package snoot.v2.commands.parameters.numeric;

import snoot.v2.util.Range;

public class FloatParameter extends NumericParameter<Float> {

    public FloatParameter(String name, String description, Range<Float> range) {
        super(name, description, "real number", range);
    }

    public FloatParameter(String name, String description) {
        this(name, description, null);
    }

    @Override
    protected Float parse(String string) throws NumberFormatException {
        return Float.valueOf(string);
    }


}
