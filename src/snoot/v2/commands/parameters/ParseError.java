package snoot.v2.commands.parameters;

import snoot.v2.commands.CommandContext;

public class ParseError extends Throwable {

    private final String message;

    private ParseError(String message) {
        this.message = message;
    }

    public static ParseError generic(String error, String value) {
        return new ParseError(error + ": " + value + ".");
    }

    public static ParseError invalid(String type, String field, String value) {
        return generic("Invalid " + type + " for " + field, "\"" + value + "\"");
    }

    public static ParseError invalid(Parameter parameter, String value) {
        return invalid(parameter.getFormat(), parameter.getName(), value);
    }

    public void send(CommandContext context) {
        context.send(message);
    }

}
