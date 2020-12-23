package snoot.v2.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permission;
import snoot.v2.commands.parameters.ParseError;
import snoot.v2.commands.parameters.Parameter;
import snoot.v2.util.Range;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SnootCommand implements CommandExecutor, TabCompleter {

    protected static class SubCommand {

        protected final String name, description;
        protected final Permission permission;
        protected final CommandFunction function;
        protected final List<Parameter> requiredParameters;
        protected final List<Parameter> optionalParameters;
        protected final boolean playerOnly;

        public SubCommand(String name, String description, String permission, CommandFunction function, List<Parameter> requiredParameters, List<Parameter> optionalParameters, boolean playerOnly) {
            this.name = name;
            this.description = description;
            this.permission = permission == null ? null : new Permission(permission);
            this.function = function;
            this.requiredParameters = requiredParameters;
            this.optionalParameters = optionalParameters;
            this.playerOnly = playerOnly;
        }

        public SubCommand(String name, String description, String permission, CommandFunction function, List<Parameter> requiredParameters, List<Parameter> optionalParameters) {
            this(name, description, permission, function, requiredParameters, optionalParameters, false);
        }

        public int minParameters() { return requiredParameters.size(); }
        public int maxParameters() { return requiredParameters.size() + optionalParameters.size(); }
        public Range<Integer> parameterRange() { return Range.between(minParameters(), maxParameters()); }

        private Parameter get(int index) {
            return (index < minParameters()) ? requiredParameters.get(index) : optionalParameters.get(index - requiredParameters.size());
        }

        public void parse(int index, CommandContext context, String string) throws ParseError {
            get(index).parse(context, string);
        }

        public void complete(int index, CommandContext context, String string) { get(index).complete(context, string); }

        public String parameterUsage() { return
                requiredParameters.stream().map(parameter -> " " + parameter.getNameComponent(true)).collect(Collectors.joining()) +
                optionalParameters.stream().map(parameter -> " " + parameter.getNameComponent(false)).collect(Collectors.joining());
        }

        public String getUsage(String label) { return "/" + label + " " + name + parameterUsage(); }

        public String getFullUsage(String label) {
            return getUsage(label) + "\nDesc: " + description;
        }

    }

    protected static class DefaultSubCommand extends SubCommand {

        SnootCommand command;

        public DefaultSubCommand(SnootCommand command, CommandFunction function, List<Parameter> requiredParameters, List<Parameter> optionalParameters) {
            super(null, null, null, function, requiredParameters, optionalParameters, false);
            this.command = command;
        }

        @Override
        public String getUsage(String label) {
            return "/" + label + parameterUsage();
        }

        @Override
        public String getFullUsage(String label) {
            return command.name + " command\nDesc: " + command.description + "\n" +
                    getUsage(label) + "\n" +
                    command.subCommands.values().stream().map(subCommand -> subCommand.getUsage(label)).collect(Collectors.joining("\n"));
        }

    }

    protected final String name, description;
    protected final Permission permission;
    protected final boolean playerOnly;
    protected final HashMap<String, SubCommand> subCommands;
    protected DefaultSubCommand defaultSubCommand = null;

    protected SnootCommand(String name, String description, String permission, boolean playerOnly) {
        this.name = name;
        this.description = description;
        this.permission = permission == null ? null : new Permission(permission);
        this.playerOnly = playerOnly;
        this.subCommands = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    protected void addSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.name, subCommand);
    }

    protected void setDefaultSubCommand(DefaultSubCommand defaultSubCommand) {
        this.defaultSubCommand = defaultSubCommand;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        CommandContext context = new CommandContext(sender, label);
        if (context.notAllowed(playerOnly, permission, false)) {
            return true;
        }
        if (args.length != 0) {
            SubCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                if (context.notAllowed(subCommand, false)) {
                    return true;
                }
                context.execute(subCommand, Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
            if ((defaultSubCommand == null) && (args.length != 1 && !args[1].equals("?"))) {
                context.send("Invalid subcommand");
            }
        }
        if (defaultSubCommand != null) {
            context.execute(defaultSubCommand, args);
            return true;
        }
        context.send(name + " Command\nDesc: " + description + "\n" +
                subCommands.values().stream().map(subCommand -> subCommand.getUsage(label)).collect(Collectors.joining("\n")));
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        CommandContext context = new CommandContext(sender, label);
        if (context.notAllowed(playerOnly, permission, true)) {
            return new ArrayList<>();
        }
        if (args.length == 1) {
            context.addCompletions(subCommands.values().stream().filter(subCommand -> !context.notAllowed(subCommand, true)).map(subCommand -> subCommand.name).collect(Collectors.toList()));
            context.addCompletions("?");
        }
        SubCommand subCommand = subCommands.get(args[0]);
        if (subCommand != null && args.length != 1) {
            if (context.notAllowed(subCommand, true)) {
                return new ArrayList<>();
            }
            if (args.length == 2) {
                context.addCompletions("?");
            }
            context.complete(subCommand, Arrays.copyOfRange(args, 1, args.length));
        }
        if (defaultSubCommand != null) {
            context.complete(defaultSubCommand, args);
        }
        return context.getCompletions(args[args.length - 1]);
    }

}
