package snoot.v2.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import snoot.v2.commands.parameters.ParseError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandContext {

    private final CommandSender sender;
    private final String label;
    private final List<Object> args;
    private final List<String> completions;

    public CommandContext(CommandSender sender, String label) {
        this.sender = sender;
        this.label = label;
        args = new ArrayList<>();
        completions = new ArrayList<>();
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public boolean hasPermission(Permission permission) {
        return sender.hasPermission(permission);
    }

    public boolean notAllowed(boolean playerOnly, Permission permission, boolean silent) {
        if (playerOnly && !isPlayer()) {
            if (!silent) send("Only players are allowed to run that command!");
            return true;
        }
        if (permission != null && !hasPermission(permission)) {
            if (!silent) send("You don't have the required permissions to run that command!");
            return true;
        }
        return false;
    }

    public boolean notAllowed(SnootCommand.SubCommand subCommand, boolean silent) { return notAllowed(subCommand.playerOnly, subCommand.permission, silent); }

    public CommandSender getSender() {
        return sender;
    }
    public Player getPlayer() {
        return (Player) sender;
    }

    public String getLabel() {
        return label;
    }

    public Object getArg(int index) {
        return args.get(index);
    }
    public void addArg(Object object) {
        args.add(object);
    }

    public List<String> getCompletions(String prefix) { return completions.stream().filter(string -> string.startsWith(prefix)).collect(Collectors.toList()); }
    public void addCompletions(List<String> completions) { this.completions.addAll(completions); }
    public void addCompletions(String... completions) { addCompletions(Arrays.asList(completions)); }

    public void send(String message) {
        sender.sendMessage(message);
    }

    public void execute(SnootCommand.SubCommand subCommand, String... args) {
        if ((args.length != 0 && args[0].equals("?")) || !subCommand.parameterRange().contains(args.length)) {
            send(subCommand.getFullUsage(label));
            return;
        }
        for (int i = 0; i < subCommand.maxParameters(); i++) {
            if (i >= args.length) {
                addArg(null);
                continue;
            }
            try {
                subCommand.parse(i, this, args[i]);
            } catch (ParseError error) {
                error.send(this);
                return;
            }
        }
        subCommand.function.execute(this);
    }

    public void complete(SnootCommand.SubCommand subCommand, int index, String string) {
        if (index < subCommand.maxParameters()) {
            subCommand.complete(index, this, string);
        }
    }
    public void complete(SnootCommand.SubCommand subCommand, String[] args) {
        complete(subCommand, args.length - 1, args[args.length - 1]);
    }

}
