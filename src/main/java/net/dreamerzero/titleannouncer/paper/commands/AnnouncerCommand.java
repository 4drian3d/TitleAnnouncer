package net.dreamerzero.titleannouncer.paper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperHelpMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.util.TriState;

public record AnnouncerCommand(MiniMessage mm) implements CommandExecutor {

    // Main Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.permissionValue("titleannouncer.command.admin") != TriState.TRUE){
            sender.sendMessage(mm.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            return true;
        }
        ConfigUtils config = new ConfigUtils();
        PaperHelpMessages paperMessages = new PaperHelpMessages();
        if (args.length == 0) {
            sender.sendMessage(mm.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            config.helpPrefix(sender);
            sender.sendMessage(paperMessages.titleHelpMessage);
            sender.sendMessage(paperMessages.actionbarHelpMessage);
            sender.sendMessage(paperMessages.bossbarHelpMessage);
            sender.sendMessage(paperMessages.fullwikilink);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                new ConfigManager().getConfig().forceReload();
                config.reloadMessage(sender);
                return true;
            }
            case "help" -> {
                sender.sendMessage(
                    mm.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                config.helpPrefix(sender);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            sender.sendMessage(paperMessages.titleHelpMessage);
                            sender.sendMessage(paperMessages.titlewikilink);
                            return true;
                        }
                        case "actionbar" -> {
                            sender.sendMessage(paperMessages.actionbarHelpMessage);
                            sender.sendMessage(paperMessages.actionbarwikilink);
                            return true;
                        }
                        case "bossbar" -> {
                            sender.sendMessage(paperMessages.bossbarHelpMessage);
                            sender.sendMessage(paperMessages.bossbarwikilink);
                            return true;
                        }
                        default -> {
                            sender.sendMessage(paperMessages.titleHelpMessage);
                            sender.sendMessage(paperMessages.actionbarHelpMessage);
                            sender.sendMessage(paperMessages.bossbarHelpMessage);
                            sender.sendMessage(paperMessages.fullwikilink);
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(paperMessages.titleHelpMessage);
                    sender.sendMessage(paperMessages.actionbarHelpMessage);
                    sender.sendMessage(paperMessages.bossbarHelpMessage);
                    sender.sendMessage(paperMessages.fullwikilink);
                    return true;
                }
            }
            default -> {
                config.invalidCommand(sender);
                return false;
            }
        }
    }

}
