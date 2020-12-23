package snoot.v2.colors;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColorFormat {

    public static final List<ChatColor> colorList = Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).collect(Collectors.toList());
    public static final List<ChatColor> formatList = Arrays.stream(ChatColor.values()).filter(color -> !color.isColor()).collect(Collectors.toList());

    private static String translateChar(char character) {
        ChatColor color = ChatColor.getByChar(character);
        if (color != null) {
            return color.toString();
        }
        switch (character) {
            case '_': return " ";
            case '&': return "&";
        }
        return "";
    }

    private static int translateCharLength(char character) {
        ChatColor color = ChatColor.getByChar(character);
        if (color != null) {
            return 0;
        }
        switch (character) {
            case '_':
            case '&': return 1;
        }
        return 0;
    }

    public static String translate(String string) {
        int index = 0;
        StringBuilder text = new StringBuilder();
        while (true) {
            index = string.indexOf('&', index);
            if (index == -1) {
                return text + string;
            }
            if (index == string.length() - 1) {
                return text + string.substring(0, string.length() - 1);
            }
            text.append(string, 0, index).append(translateChar(string.charAt(index + 1)));
            string = string.substring(index + 2);
        }
    }

    public static int translateLength(String string) {
        int index = 0;
        int length = 0;
        while (true) {
            index = string.indexOf('&', index);
            if (index == -1) {
                return length + string.length();
            }
            if (index == string.length() - 1) {
                return length + string.length() - 1;
            }
            length += index + translateCharLength(string.charAt(index + 1));
            string = string.substring(index + 2);
        }
    }
    
    public static String strip(String string) {
        int index = 0;
        StringBuilder text = new StringBuilder();
        while (true) {
            index = string.indexOf('&', index);
            if (index == -1) {
                return text + string;
            }
            if (index == string.length() - 1) {
                return text + string.substring(0, string.length() - 1);
            }
            text.append(string, 0, index);
            string = string.substring(index + 2);
        }

    }

}
