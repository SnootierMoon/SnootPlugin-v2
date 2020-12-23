package snoot.v2.commands.commands;

import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.enums.BinaryOptionParameter;
import snoot.v2.core.PlayerData;
import snoot.v2.util.BinaryOption;

import java.util.ArrayList;
import java.util.Collections;

public class ChairCommand extends SnootCommand {

    public ChairCommand() {
        super("Chair", "enable/disable chairs", "snoot.chair", true);
        setDefaultSubCommand(new DefaultSubCommand(this, ChairCommand::chairCommand,
                new ArrayList<>(),
                Collections.singletonList(new BinaryOptionParameter("chair-mode", "whether or not to use chairs"))));
    }

    private static void chairCommand(CommandContext context) {
        BinaryOption option = (BinaryOption)context.getArg(0);
        PlayerData playerData = PlayerData.getData(context.getPlayer());
        if (option == null || option == BinaryOption.TOGGLE) {
            playerData.setChairsEnabled(!playerData.isChairsEnabled());
        }
        if (option == BinaryOption.OFF) {
            playerData.setChairsEnabled(false);
        } else if (option == BinaryOption.ON) {
            playerData.setChairsEnabled(true);
        }
        context.send("Chairs have been turned " + (playerData.isChairsEnabled() ? "on." : "off."));
    }

}
