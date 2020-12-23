package snoot.v2.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.numeric.IntegerParameter;
import snoot.v2.util.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NearCommand extends SnootCommand {

    public NearCommand() {
        super("Near", "list nearby players", "snoot.near", true);
        setDefaultSubCommand(new DefaultSubCommand(this, NearCommand::commandNear,
                new ArrayList<>(),
                Collections.singletonList(new IntegerParameter("radius", "maximum distance to list players", Range.greaterThan(0)))));
    }

    private static void commandNear(CommandContext context) {
        Integer radius = (Integer)context.getArg(0);
        for (Player player : context.getPlayer().getWorld().getPlayers()) {
            if (radius == null || player.getLocation().distanceSquared(context.getPlayer().getLocation()) < radius) {
                context.send(player.getName());
            }
        }
    }

}
