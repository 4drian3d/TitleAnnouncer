package net.dreamerzero.TitleAnnouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncerCommand implements CommandExecutor {
    private final Announcer plugin;
	public AnnouncerCommand(Announcer plugin) {
		this.plugin = plugin;
	}
    static final TextComponent commands = 
        Component.newline()
        .append(Component.text("Title:", NamedTextColor.AQUA))
        .append(Component.newline())
        .append(Component.text("/announcetitle [Title]; [Subtitle]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/selftitle [Title]; [Subtitle]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/worldtitle [Title]; [Subtitle]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/sendtitle [Player] [Title]; [Subtitle]", 
            NamedTextColor.GOLD))
        .append(Component.newline())
        .append(Component.text("ActionBar: ", 
            NamedTextColor.AQUA))
            .append(Component.newline())
        .append(Component.text("/announceactionbar [Actionbar]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/selfactionbar [Actionbar]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/worldactionbar [Actionbar]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        .append(Component.text("/sendactionbar [Player] [Actionbar]", 
            NamedTextColor.GOLD))
            .append(Component.newline())
        ;
    static final Component announce = MiniMessageUtil.parse(
        "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>");

    // Main Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Boolean enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix){
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray>"));
        }

        if (!(sender.hasPermission("announcer.command.show"))) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return false;
        }
        if (!(sender.hasPermission("announcer.command.admin"))){
            sender.sendMessage(announce);
            return false;
        }

        if(args.length == 0) {
            sender.sendMessage(announce);
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>"))));
            sender.sendMessage(commands);
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
        } else if (args[0].equalsIgnoreCase("help")){
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>"))));
            sender.sendMessage(commands);
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
