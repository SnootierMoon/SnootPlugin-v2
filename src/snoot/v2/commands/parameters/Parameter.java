package snoot.v2.commands.parameters;

import snoot.v2.commands.CommandContext;

public abstract class Parameter {

    public abstract void parse(CommandContext context, String string) throws ParseError;
    public void complete(CommandContext context, String string) {

    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getFormat();
    public abstract String getFormatDescription();

    public String getNameComponent(boolean required) { return required ? "<" + getName() + ">" : "[" + getName() + "]"; }

}
