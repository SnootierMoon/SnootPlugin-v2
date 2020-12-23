package snoot.v2.commands.parameters.enums;

import org.bukkit.Bukkit;
import snoot.v2.commands.CommandContext;
import snoot.v2.commands.parameters.ParseError;
import snoot.v2.commands.parameters.Parameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumParameter<T extends Enum<T>> extends Parameter {

   private final Class<T> class_;
   private final String name, description, format, formatDescription;
   final List<String> options;

    public EnumParameter(Class<T> class_, String name, String description, String format, String formatDescription) {
        this.name = name;
        this.description = description;
        this.format = format;
        this.formatDescription = formatDescription;
        this.class_ = class_;
        this.options = Arrays.stream(class_.getEnumConstants()).filter(this::valid).map(Enum::name).map(String::toLowerCase).collect(Collectors.toList());
    }

    protected boolean valid(T value) { return true; }

    @Override
    public void parse(CommandContext context, String string) throws ParseError {
        try {
            T value = T.valueOf(class_, string.toUpperCase());
            if (!valid(value)) {
                throw ParseError.invalid(this, string);
            }
            context.addArg(value);
        } catch (IllegalArgumentException error) {
            throw ParseError.invalid(this, string);
        }
    }

    @Override
    public void complete(CommandContext context, String string) {
        context.addCompletions(this.options);
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public String getFormat() { return format; }
    @Override public String getFormatDescription() {return formatDescription; }

}
