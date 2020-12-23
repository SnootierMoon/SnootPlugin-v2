package snoot.v2.commands.commands;

import org.bukkit.entity.Player;
import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.numeric.FloatParameter;
import snoot.v2.commands.parameters.enums.MovementTypeParameter;
import snoot.v2.util.MovementType;
import snoot.v2.util.Range;

import java.util.ArrayList;
import java.util.Collections;

public class SpeedCommand extends SnootCommand {

    public SpeedCommand() {
        super("Speed", "set your movement speed", "snoot.creative.speed", true);
        addSubCommand(new SubCommand("reset", "reset your speed", null, SpeedCommand::commandReset,
                new ArrayList<>(),
                Collections.singletonList(new MovementTypeParameter("type", "the speed type to reset"))));
        addSubCommand(new SubCommand("set", "set your speed", null, SpeedCommand::commandSet,
                Collections.singletonList(new FloatParameter("speed", "the new speed", Range.between(0.0f, 10.0f))),
                Collections.singletonList(new MovementTypeParameter("type", "the speed type to change"))));
    }

    private static void commandReset(CommandContext context) {
        MovementType type = (MovementType)context.getArg(0);
        if (type == null) {
            type = context.getPlayer().isFlying() ? MovementType.FLY : MovementType.WALK;
        }
        if (type == MovementType.BOTH) {
            context.getPlayer().setFlySpeed(0.2f);
            context.send("Your speed has been reset.");
        } else if (type == MovementType.FLY) {
            context.getPlayer().setFlySpeed(0.2f);
            context.send("Your fly speed has been reset.");
        } else if (type == MovementType.WALK) {
            context.getPlayer().setWalkSpeed(0.2f);
            context.send("Your walk speed has been reset.");
        }
    }

    private static void commandSet(CommandContext context) {
        Float speed = (Float)context.getArg(0);
        MovementType type = (MovementType)context.getArg(1);
        if (type == null) {
            type = context.getPlayer().isFlying() ? MovementType.FLY : MovementType.WALK;
        }
        if (type == MovementType.BOTH) {
            context.getPlayer().setFlySpeed(speed / 10);
            context.send("Your speed has been set to " + speed + ".");
        } else if (type == MovementType.FLY) {
            context.getPlayer().setFlySpeed(speed / 10);
            context.send("Your fly speed has been set to " + speed + ".");
        } else if (type == MovementType.WALK) {
            context.getPlayer().setWalkSpeed(speed / 10);
            context.send("Your walk speed has been set to " + speed + ".");
        }
    }

}
