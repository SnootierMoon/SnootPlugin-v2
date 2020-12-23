package snoot.v2.commands.commands;

import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.StringParameter;
import snoot.v2.core.PlayerData;

import java.util.ArrayList;
import java.util.Collections;

public class NickCommand extends SnootCommand {
    public NickCommand() {
        super("Nick", "set your server nickname", "snoot.chat.nick", true);
        addSubCommand(new SubCommand("reset", "set your server nickname", null, NickCommand::commandReset,
                new ArrayList<>(),
                new ArrayList<>()));
        addSubCommand(new SubCommand("set", "reset your server nickname", null, NickCommand::commandSet,
                Collections.singletonList(new StringParameter("nickname", "nickname to use")),
                new ArrayList<>()));
    }

    private static void commandReset(CommandContext context) {
        PlayerData.getData(context.getPlayer()).setNick(null);
    }

    private static void commandSet(CommandContext context) {
        String nick = (String)context.getArg(0);
        PlayerData.getData(context.getPlayer()).setNick(nick);
    }

}
