package net.dreamerzero.TitleAnnouncer.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.minimessage.Template;

public class PlaceholderUtil {
    /**
     * Replace Placeholders in Title/ActionBar
     * for the context of Console
     * @return Placeholders for console
     */
    public static List<Template> replacePlaceholders(){
        // TODO: make functional again
        /*String initialmspt[] = String.valueOf(Bukkit.getAverageTickTime()/20).split(".");
        var finalmspt = initialmspt[0];

        String initialtps[] = String.valueOf(Bukkit.getTPS()[0]/20).split(".");
        var finaltps = initialtps[0];*/

        List<Template> templates = List.of(
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("player", text("CONSOLA")),
            Template.of("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20))),
            Template.of("tps", text(String.valueOf(Bukkit.getTPS()[0]))));
        return templates;
    }
    /**
     * Replace Placeholders in Title/ActionBar
     * for player sender
     * @param player
     * @return Placeholders for sender player
     */
    public static List<Template> replacePlaceholders(Player player){
        // TODO: make functional again
        /*String initialmspt[] = String.valueOf(Bukkit.getAverageTickTime()/20).split(".");
        var finalmspt = initialmspt[0];

        String initialtps[] = String.valueOf(Bukkit.getTPS()[0]/20).split(".");
        var finaltps = initialtps[0];*/

        List<Template> templates = List.of(
            Template.of("name", text(player.getName())), 
            Template.of("ping", text(player.getPing())),
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("world", text(player.getWorld().getName())),
            Template.of("player", text(player.getName())),
            Template.of("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20))),
            Template.of("tps", text(String.valueOf(Bukkit.getTPS()[0]))));
        return templates;
    }
    /**
     * Replace Placeholder in Title/ActionBar
     * to Player or PlayerObjetive
     * @param player
     * @param otherPlayer
     * @return Placeholders for sender player and the playerobjetive
     */
    public static List<Template> replacePlaceholders(Player player, Player otherPlayer){
        // TODO: make functional again
        /*String initialmspt[] = String.valueOf(Bukkit.getAverageTickTime()/20).split(".");
        var finalmspt = initialmspt[0];

        String initialtps[] = String.valueOf(Bukkit.getTPS()[0]).split(".");
        var finaltps = initialtps[0];*/
        
        List<Template> templates = List.of(
            Template.of("name", text(player.getName())), 
            Template.of("ping", text(player.getPing())),
            Template.of("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.of("world", text(player.getWorld().getName())),
            Template.of("othername", text(otherPlayer.getName())),
            Template.of("otherping", text(otherPlayer.getPing())),
            Template.of("otherworld", text(otherPlayer.getName())),
            Template.of("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20))),
            Template.of("tps", text(String.valueOf(Bukkit.getTPS()[0]))));
        return templates;
    }
}
