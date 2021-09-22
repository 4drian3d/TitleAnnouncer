package net.dreamerzero.titleannouncer.utils;

import java.util.List;

import org.bukkit.Bukkit;

import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.minimessage.Template;

public class PlaceholderUtil {
    /**
     * Replace Placeholders in Title/ActionBar
     * for the context of Console
     * @return Placeholders for console
     */
    public static List<Template> replacePlaceholders() {
        String mspt = String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3);
        String tps = String.valueOf(Bukkit.getTPS()[0]).substring(0, 4);

        final List<Template> templates = List.of(
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("mspt", text(mspt)),
            Template.of("tps", text(tps)));
        return templates;
    }
    /**
     * Replace Placeholders in Title/ActionBar
     * for player sender
     * @param player
     * @return Placeholders for sender player
     */
    public static List<Template> replacePlaceholders(org.bukkit.entity.Player player) {

        String mspt = String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3);
        String tps = String.valueOf(Bukkit.getTPS()[0]).substring(0, 4);

        final List<Template> templates = List.of(
            Template.of("name", text(player.getName())),
            Template.of("ping", text(String.valueOf(player.getPing()))),
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("world", text(player.getWorld().getName())),
            Template.of("player", text(player.getName())),
            Template.of("mspt", text(mspt)),
            Template.of("tps", text(tps)));
        return templates;
    }
    /**
     * Replace Placeholder in Title/ActionBar
     * to Player or PlayerObjetive
     * @param player
     * @param otherPlayer
     * @return Placeholders for sender player and the playerobjetive
     */
    public static List<Template> replacePlaceholders(
            org.bukkit.entity.Player player,
            org.bukkit.entity.Player otherPlayer) {

        String mspt = String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3);
        String tps = String.valueOf(Bukkit.getTPS()[0]).substring(0, 4);

        final List<Template> templates = List.of(
            Template.of("name", text(player.getName())),
            Template.of("ping", text(String.valueOf(player.getPing()))),
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("world", text(player.getWorld().getName())),
            Template.of("othername", text(otherPlayer.getName())),
            Template.of("otherping", text(otherPlayer.getPing())),
            Template.of("otherworld", text(otherPlayer.getName())),
            Template.of("mspt", text(mspt)),
            Template.of("tps", text(tps)));
        return templates;
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
