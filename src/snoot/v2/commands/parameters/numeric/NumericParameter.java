package snoot.v2.commands.parameters.numeric;

import snoot.v2.commands.CommandContext;
import snoot.v2.commands.parameters.ParseError;
import snoot.v2.commands.parameters.Parameter;
import snoot.v2.util.Range;

public abstract class NumericParameter<T extends Number & Comparable<T>> extends Parameter {

    private final String name, description, format;
    private final Range<T> range;

    public NumericParameter(String name, String description, String format, Range<T> range) {
        this.name = name;
        this.description = description;
        this.format = format;
        this.range = range;
    }

    public NumericParameter(String name, String description, String format) {
        this(name, description, format, null);
    }

    protected abstract T parse(String string) throws NumberFormatException;

    @Override
    public void parse(CommandContext context, String string) throws ParseError {
        try {
            T result = parse(string);
            if (!range.contains(result)) {
                throw ParseError.generic("The " + name + " is not " + range.getDescription(), string);
            }
            context.addArg(result);
        } catch (NumberFormatException error) {
            throw ParseError.invalid(this, string);
        }
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public String getFormat() { return format; }
    @Override public String getFormatDescription() {
        String rangeDescription = range.getDescription();
        return  (rangeDescription == null) ? format : format + " " + rangeDescription;
    }


}
