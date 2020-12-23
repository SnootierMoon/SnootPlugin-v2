package snoot.v2.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import snoot.v2.SnootMain;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    private static class PlayerDataListener implements Listener {

        @EventHandler
        private void handleEvent(PlayerJoinEvent event) {
            playerCache.put(event.getPlayer(), new PlayerData(event.getPlayer()));
        }

        @EventHandler
        private void handleEvent(PlayerQuitEvent event) {
            playerCache.get(event.getPlayer()).save();
            playerCache.remove(event.getPlayer());
        }

        @EventHandler
        private void handleEvent(AsyncPlayerChatEvent event) {
            PlayerData player = getData(event.getPlayer());
            event.setFormat("[%1$s] %2$s\n");
        }

    }

    private static final HashMap<Player, PlayerData> playerCache = new HashMap<>();

    private final Player player;
    private String nick;
    private ChatColor chatColor;
    private boolean chairsEnabled;

    private PlayerData(Player player) {
        load();
        this.player = player;
    }

    public static void onEnable() {
        onDisable();
        SnootMain.addListener(new PlayerDataListener());
        Bukkit.getOnlinePlayers().forEach((player) -> playerCache.put(player, new PlayerData(player)));
    }

    public static void onDisable() {
        playerCache.forEach((player, data) -> data.save());
        playerCache.clear();
    }

    private void save() {
    }

    private void load() {
        nick = "fuck";
        chatColor = ChatColor.DARK_GRAY;
        chairsEnabled = true;
    }

    public static PlayerData getData(Player player) { return playerCache.get(player); }

    public String getNick() {
        return nick == null ? ChatColor.DARK_GRAY + player.getName() : nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ChatColor getChatColor() {
        return chatColor == null ? ChatColor.GRAY : chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public boolean isChairsEnabled() {
        return chairsEnabled;
    }

    public void setChairsEnabled(boolean chairsEnabled) {
        this.chairsEnabled = chairsEnabled;
    }

    public boolean isNightVisionEnabled() {
        return player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }

    public void setNightVisionEnabled(boolean nightVisionEnabled) {
        if (nightVisionEnabled) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

}
