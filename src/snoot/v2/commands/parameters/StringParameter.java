package snoot.v2.commands.parameters;

import snoot.v2.commands.CommandContext;

public class StringParameter extends Parameter {

    private final String name, description;

    public StringParameter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void parse(CommandContext context, String string) {
        context.addArg(string);
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public String getFormat() { return "string"; }
    @Override public String getFormatDescription() {return "text without spaces"; }

}
