package snoot.v2;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import snoot.v2.commands.SnootCommand;
import snoot.v2.commands.commands.*;
import snoot.v2.core.PlayerData;

import java.util.List;

public class SnootMain extends JavaPlugin {

    private static SnootMain instance;

    @Override
    public void onEnable() {
        instance = this;
        PlayerData.onEnable();
        addCommand(new ChairCommand());
        addCommand(new ChatColorCommand());
        addCommand(new ColorCommand());
        addCommand(new NearCommand());
        addCommand(new NickCommand());
        addCommand(new NightVisionCommand());
        addCommand(new SpeedCommand());
        addCommand(new TagCommand());
    }

    @Override
    public void onDisable() {
        PlayerData.onDisable();
        instance = null;
    }

    public static void addCommand(SnootCommand command) {
        PluginCommand pluginCommand = instance.getCommand(command.getName());
        if (pluginCommand == null) {
            instance.getLogger().warning("Failed to find command \"" + command.getName() + "\".");
            return;
        }
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

    public static void addListener(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

}
