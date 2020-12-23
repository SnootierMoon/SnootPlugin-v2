package snoot.v2.commands.commands;

import org.bukkit.ChatColor;
import snoot.v2.colors.ColorFormat;
import snoot.v2.colors.IllegalSpaceError;
import snoot.v2.commands.CommandContext;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.parameters.StringParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColorCommand extends SnootCommand {

    private static final String colorCodeList = ColorFormat.colorList.stream().map(color -> color + "&" + color.getChar()).collect(Collectors.joining(" "));
    private static final String formatList =
            Stream.concat(ColorFormat.formatList.stream().map(color -> ChatColor.GRAY + "&" + color.getChar() + ": " + ChatColor.WHITE + color.toString() + color.name().toLowerCase()),
            Stream.of(ChatColor.GRAY + "&&: " + ChatColor.DARK_GRAY + "<& symbol>" , ChatColor.GRAY + "&_: " + ChatColor.DARK_GRAY + "<empty space>")
    ).collect(Collectors.joining("\n"));

    public ColorCommand() {
        super("Color", "describe chat colors", "snoot.color", false);
        addSubCommand(new SubCommand("help", "learn about color codes", null, ColorCommand::helpCommand,
                new ArrayList<>(),
                new ArrayList<>()));
        addSubCommand(new SubCommand("list", "list available color codes", null, ColorCommand::listCommand,
                new ArrayList<>(),
                new ArrayList<>()));
        addSubCommand(new SubCommand("test", "test a colored string", null, ColorCommand::testCommand,
                Collections.singletonList(new StringParameter("test", "color code to test")),
                new ArrayList<>()));
    }

    private static void helpCommand(CommandContext context) {
        context.send("color help.");
    }

    private static void listCommand(CommandContext context) {
        context.send("Colors:");
        context.send(colorCodeList);
        context.send("Formats:");
        context.send(formatList);
    }

    private static void testCommand(CommandContext context) {
        String testString = (String)context.getArg(0);
        context.send(ColorFormat.translate(testString));
    }

}
