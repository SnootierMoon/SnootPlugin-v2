package snoot.v2.commands.parameters.enums;

import org.bukkit.ChatColor;

public class ColorParameter extends EnumParameter<ChatColor> {

    public ColorParameter(String name, String description) {
        super(ChatColor.class, name, description, "color", "see /color");
    }

    @Override
    protected boolean valid(ChatColor value) {
        return value.isColor();
    }

}
