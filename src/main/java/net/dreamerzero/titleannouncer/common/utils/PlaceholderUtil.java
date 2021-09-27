package net.dreamerzero.titleannouncer.common.utils;

import java.util.List;

import com.velocitypowered.api.proxy.ProxyServer;

import org.bukkit.Bukkit;

import static net.kyori.adventure.text.Component.text;

import net.dreamerzero.titleannouncer.paper.Announcer;
import net.kyori.adventure.text.minimessage.Template;

public class PlaceholderUtil {
    private static boolean isPlaceholderAPIPresent;
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
     * @deprecated Very confusing to use, 
     * since the placeholders must point to the target player and not to the one sending it
     */
    @Deprecated
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

    public static Template replaceProxyPlaceholders(){
        ProxyServer server = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();
        return Template.of("online", String.valueOf(server.getPlayerCount()));
    }

    public static List<Template> replaceProxyPlaceholders(
        com.velocitypowered.api.proxy.Player player){

        ProxyServer server = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();
        List<Template> templates = List.of(
            Template.of("online", String.valueOf(server.getPlayerCount())),
            Template.of("name", player.getUsername()),
            Template.of("ping", String.valueOf(player.getPing())),
            Template.of("client", player.getClientBrand()),
            Template.of("locale", player.getEffectiveLocale().getDisplayLanguage()),
            Template.of("server", player.getCurrentServer().get().getServerInfo().getName()),
            Template.of("version", String.valueOf(player.getProtocolVersion().getMostRecentSupportedVersion())));
        if(player.getModInfo().isPresent()) {
            templates.add(Template.of("mods", player.getModInfo().get().getMods().toString()));
        }
        return templates;
    }

    public static void setPAPIStatus(boolean status){
        isPlaceholderAPIPresent = status;
    }

    public static boolean placeholderAPIHook(){
        return isPlaceholderAPIPresent;
    }

    public static void placeholderAPICheck(){
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Announcer.getInstance().getLogger().info("PlaceholderAPI founded. Enabling integration.");
            isPlaceholderAPIPresent = true;
		} else {
			Announcer.getInstance().getLogger().info("PlaceholderAPI integration disabled.");
			isPlaceholderAPIPresent = false;
		}
	}
}
