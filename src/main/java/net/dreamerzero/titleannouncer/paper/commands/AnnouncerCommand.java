package net.dreamerzero.titleannouncer.paper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperHelpMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.util.TriState;

public class AnnouncerCommand implements CommandExecutor {
    private final MiniMessage mm;

    public AnnouncerCommand(MiniMessage mm){
        this.mm = mm;
    }

    // Main Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.permissionValue("titleannouncer.command.admin") != TriState.TRUE){
            sender.sendMessage(mm.deserialize(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(mm.deserialize(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            ConfigUtils.helpPrefix(sender);
            sender.sendMessage(PaperHelpMessages.titleHelpMessage);
            sender.sendMessage(PaperHelpMessages.actionbarHelpMessage);
            sender.sendMessage(PaperHelpMessages.bossbarHelpMessage);
            sender.sendMessage(PaperHelpMessages.fullwikilink);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                ConfigManager.getConfig().forceReload();
                ConfigUtils.reloadMessage(sender);
                return true;
            }
            case "help" -> {
                sender.sendMessage(mm.deserialize(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                ConfigUtils.helpPrefix(sender);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            sender.sendMessage(PaperHelpMessages.titleHelpMessage);
                            sender.sendMessage(PaperHelpMessages.titlewikilink);
                            return true;
                        }
                        case "actionbar" -> {
                            sender.sendMessage(PaperHelpMessages.actionbarHelpMessage);
                            sender.sendMessage(PaperHelpMessages.actionbarwikilink);
                            return true;
                        }
                        case "bossbar" -> {
                            sender.sendMessage(PaperHelpMessages.bossbarHelpMessage);
                            sender.sendMessage(PaperHelpMessages.bossbarwikilink);
                            return true;
                        }
                        case "chat" -> {
                            sender.sendMessage(PaperHelpMessages.chatHelpMessage);
                            sender.sendMessage(PaperHelpMessages.chatwikilink);
                            return true;
                        }
                        default -> {
                            sender.sendMessage(PaperHelpMessages.titleHelpMessage);
                            sender.sendMessage(PaperHelpMessages.actionbarHelpMessage);
                            sender.sendMessage(PaperHelpMessages.bossbarHelpMessage);
                            sender.sendMessage(PaperHelpMessages.chatHelpMessage);
                            sender.sendMessage(PaperHelpMessages.fullwikilink);
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(PaperHelpMessages.titleHelpMessage);
                    sender.sendMessage(PaperHelpMessages.actionbarHelpMessage);
                    sender.sendMessage(PaperHelpMessages.bossbarHelpMessage);
                    sender.sendMessage(PaperHelpMessages.fullwikilink);
                    return true;
                }
            }
            default -> {
                ConfigUtils.invalidCommand(sender);
                return false;
            }
        }
    }
}
