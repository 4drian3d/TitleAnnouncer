package net.dreamerzero.titleannouncer.paper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;

public class AnnouncerCommand implements CommandExecutor {
    public AnnouncerCommand() {}

    // Main Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (sender.permissionValue("announcer.command.show")){
            case NOT_SET -> {
                sender.sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                return true;
            }
            case FALSE -> {
                ConfigUtils.sendNoMainPermission(sender);
                return true;
            }
            case TRUE -> {}
        }

        if (args.length == 0) {
            sender.sendMessage(
                MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            ConfigUtils.helpPrefix(sender);
            sender.sendMessage(GeneralUtils.titleHelpMessage);
            sender.sendMessage(GeneralUtils.actionbarHelpMessage);
            sender.sendMessage(GeneralUtils.bossbarHelpMessage);
            sender.sendMessage(GeneralUtils.fullwikilink);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                ConfigManager.getConfig().forceReload();
                ConfigUtils.reloadMessage(sender);
                return true;
            }
            case "help" -> {
                sender.sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                ConfigUtils.helpPrefix(sender);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            sender.sendMessage(GeneralUtils.titleHelpMessage);
                            sender.sendMessage(GeneralUtils.titlewikilink);
                            return true;
                        }
                        case "actionbar" -> {
                            sender.sendMessage(GeneralUtils.actionbarHelpMessage);
                            sender.sendMessage(GeneralUtils.actionbarwikilink);
                            return true;
                        }
                        case "bossbar" -> {
                            sender.sendMessage(GeneralUtils.bossbarHelpMessage);
                            sender.sendMessage(GeneralUtils.bossbarwikilink);
                            return true;
                        }
                        default -> {
                            sender.sendMessage(GeneralUtils.titleHelpMessage);
                            sender.sendMessage(GeneralUtils.actionbarHelpMessage);
                            sender.sendMessage(GeneralUtils.bossbarHelpMessage);
                            sender.sendMessage(GeneralUtils.fullwikilink);
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(GeneralUtils.titleHelpMessage);
                    sender.sendMessage(GeneralUtils.actionbarHelpMessage);
                    sender.sendMessage(GeneralUtils.bossbarHelpMessage);
                    sender.sendMessage(GeneralUtils.fullwikilink);
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
