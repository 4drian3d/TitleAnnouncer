package net.dreamerzero.titleannouncer.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.permission.Tristate;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;

public class AnnouncerCommand implements SimpleCommand {
    public AnnouncerCommand() {}

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length == 0) {
            source.sendMessage(
                MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            ConfigUtils.helpPrefix(source);
            //TODO: Add proper velocity command help /*v*announcetitle
            source.sendMessage(GeneralUtils.titleHelpMessage);
            source.sendMessage(GeneralUtils.actionbarHelpMessage);
            source.sendMessage(GeneralUtils.bossbarHelpMessage);
            source.sendMessage(GeneralUtils.fullwikilink);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                ConfigManager.getConfig().forceReload();
                ConfigUtils.reloadMessage(source);
            }
            case "help" -> {
                source.sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                ConfigUtils.helpPrefix(source);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            source.sendMessage(GeneralUtils.titleHelpMessage);
                            source.sendMessage(GeneralUtils.titlewikilink);
                        }
                        case "actionbar" -> {
                            source.sendMessage(GeneralUtils.actionbarHelpMessage);
                            source.sendMessage(GeneralUtils.actionbarwikilink);
                        }
                        case "bossbar" -> {
                            source.sendMessage(GeneralUtils.bossbarHelpMessage);
                            source.sendMessage(GeneralUtils.bossbarwikilink);
                        }
                        default -> {
                            source.sendMessage(GeneralUtils.titleHelpMessage);
                            source.sendMessage(GeneralUtils.actionbarHelpMessage);
                            source.sendMessage(GeneralUtils.bossbarHelpMessage);
                            source.sendMessage(GeneralUtils.fullwikilink);
                        }
                    }
                } else {
                    source.sendMessage(GeneralUtils.titleHelpMessage);
                    source.sendMessage(GeneralUtils.actionbarHelpMessage);
                    source.sendMessage(GeneralUtils.bossbarHelpMessage);
                    source.sendMessage(GeneralUtils.fullwikilink);
                }
            }
            default -> {
                ConfigUtils.invalidCommand(source);
            }
        }

    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        if (invocation.source().getPermissionValue("titleannouncer.command.show") != Tristate.TRUE){
            return false;
        }
        return true;
    }
}
