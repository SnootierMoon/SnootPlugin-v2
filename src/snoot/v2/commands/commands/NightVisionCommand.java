package snoot.v2.commands.commands;

import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.enums.BinaryOptionParameter;
import snoot.v2.core.PlayerData;
import snoot.v2.util.BinaryOption;

import java.util.ArrayList;
import java.util.Collections;

public class NightVisionCommand extends SnootCommand {

    public NightVisionCommand() {
        super("NightVision", "enable/disable night vision", "snoot.creative.nightvision", true);
        setDefaultSubCommand(new DefaultSubCommand(this, NightVisionCommand::commandNightVision,
                new ArrayList<>(),
                Collections.singletonList(new BinaryOptionParameter("nv-mode", "whether or not to use night vision"))));
    }

    private static void commandNightVision(CommandContext context) {
        BinaryOption option = (BinaryOption)context.getArg(0);
        PlayerData playerData = PlayerData.getData(context.getPlayer());
        if (option == null || option == BinaryOption.TOGGLE) {
            playerData.setNightVisionEnabled(!playerData.isNightVisionEnabled());
        }
        if (option == BinaryOption.OFF) {
            playerData.setNightVisionEnabled(false);
        } else if (option == BinaryOption.ON) {
            playerData.setNightVisionEnabled(true);
        }
        context.send("NightVision been turned " + (playerData.isChairsEnabled() ? "on." : "off."));
    }

}
