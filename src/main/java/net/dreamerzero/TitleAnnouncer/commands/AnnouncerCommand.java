package net.dreamerzero.TitleAnnouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import org.jetbrains.annotations.NotNull;

public class AnnouncerCommand implements CommandExecutor {
    private final Announcer plugin;

	public AnnouncerCommand(Announcer plugin) {
	    this.plugin = plugin;
	}

    final static Component helpMessage =
        text("Title:", AQUA).append(newline()).append(
        text("/announcetitle [Title]; [Subtitle]", GOLD)).append(newline()).append(
        text("/selftitle [Title]; [Subtitle]", GOLD)).append(newline()).append(
        text("/worldtitle [Title]; [Subtitle]", GOLD)).append(newline()).append(
        text("ActionBar: ", AQUA)).append(newline()).append(
        text("/announceactionbar [Actionbar]", GOLD)).append(newline()).append(
        text("/selfactionbar [Actionbar]", GOLD)).append(newline()).append(
        text("/worldactionbar [Actionbar]", GOLD)).append(newline()).append(
        text("/sendactionbar [Player] [Actionbar]", GOLD));
    ;

    // Main Command
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
  
        Component prefix = text("");
        Component announce = MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"
        );

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }

        if (!(sender.hasPermission("announcer.command.show"))) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return false;
        }
        if (!(sender.hasPermission("announcer.command.admin"))) {
            sender.sendMessage(announce);
            return false;
        }

        if(args.length == 0) {
            sender.sendMessage(announce);
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>")));
            sender.sendMessage(helpMessage);
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();

            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.reload-config", 
                        "<green>Config Reloaded</green>"))));
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(announce);
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>")));
            sender.sendMessage(helpMessage);
            return true;
        } else {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.invalid-command", 
                        "<red>Unknown Command</red>"))));
        }
        return true;
    }

}
