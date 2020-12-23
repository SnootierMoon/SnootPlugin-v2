package snoot.v2.commands.parameters.numeric;

import snoot.v2.util.Range;


public class IntegerParameter extends NumericParameter<Integer> {

    public IntegerParameter(String name, String description, Range<Integer> range) {
        super(name, description, "integer", range);
    }

    public IntegerParameter(String name, String description) {
        this(name, description, null);
    }

    @Override
    protected Integer parse(String string) throws NumberFormatException {
        return Integer.valueOf(string);
    }
}
