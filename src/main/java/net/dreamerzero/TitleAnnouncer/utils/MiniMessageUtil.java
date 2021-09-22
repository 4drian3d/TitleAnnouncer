package net.dreamerzero.titleannouncer.utils;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
/*
Component that parses the title or
actionbar with the MiniMessage format.
*/
public class MiniMessageUtil {

    /**
     * Will format the delivered text
     * @param message
     * @return The message with formatting
     */
    public static Component parse(
        final String message) {

        return MiniMessage.get().parse(message);
    }

    /**
     * It will format the delivered text
     * and replace a single placeholder.
     * @param message
     * @param template
     * @return The formatted message and a replaced placeholder
     */
    public static Component parse(
        final String message, Template template) {

        return MiniMessage.get().parse(message, template);
    }

    /**
     * Will format the delivered text
     * with a list of placeholders.
     * @param message
     * @param template
     * @return Message with formatting and a list of placeholders replaced
     */
    public static Component parse(
        final String message, List<Template> template) {

        return MiniMessage.get().parse(message, template);
    }

    public static String replaceLegacy(String legacyText){
        String newText = legacyText
            .replaceAll("&1", "<dark_blue>")
            .replaceAll("&2", "<dark_green>")
            .replaceAll("&3", "<dark_aqua>")
            .replaceAll("&4", "<dark_red>")
            .replaceAll("&5", "<dark_purple>")
            .replaceAll("&6", "<gold>")
            .replaceAll("&7", "<gray>")
            .replaceAll("&8", "<dark_gray>")
            .replaceAll("&9", "<blue>")
            .replaceAll("&a", "<green>")
            .replaceAll("&b", "<aqua>")
            .replaceAll("&c", "<red>")
            .replaceAll("&d", "<light_purple>")
            .replaceAll("&e", "<yellow>")
            .replaceAll("&f", "<white>")
            .replaceAll("&l", "<bold>")
            .replaceAll("&k", "<obfuscated>")
            .replaceAll("&m", "<strikethrough>")
            .replaceAll("&n", "<underline>");
            return newText;
    }
}
