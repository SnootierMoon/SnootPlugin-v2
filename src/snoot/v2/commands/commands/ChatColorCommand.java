package snoot.v2.commands.commands;

import org.bukkit.ChatColor;
import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.enums.ColorParameter;
import snoot.v2.core.PlayerData;

import java.util.ArrayList;
import java.util.Collections;

public class ChatColorCommand extends SnootCommand {

    public ChatColorCommand() {
        super("ChatColor", "change the color of your chat messages", "snoot.chat.color", false);
        addSubCommand(new SubCommand("list", "list available chat colors", null, ChatColorCommand::commandList,
                new ArrayList<>(),
                new ArrayList<>()));
        addSubCommand(new SubCommand("reset", "reset your chat color", null, ChatColorCommand::commandReset,
                new ArrayList<>(),
                new ArrayList<>()));
        addSubCommand(new SubCommand("set", "set your chat color", null, ChatColorCommand::commandSet,
                Collections.singletonList(new ColorParameter("chatcolor", "chatcolor to use")),
                new ArrayList<>()));
    }

    private static void commandList(CommandContext context) {
        context.send("Listing chat colors.");
    }

    private static void commandReset(CommandContext context) {
        context.send("Reset chat color.");
    }

    private static void commandSet(CommandContext context) {
        ChatColor color = (ChatColor)context.getArg(0);
        PlayerData.getData(context.getPlayer()).setChatColor(color);
    }

}
