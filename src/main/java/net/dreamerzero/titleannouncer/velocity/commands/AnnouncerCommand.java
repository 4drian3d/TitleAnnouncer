package net.dreamerzero.titleannouncer.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;

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
                return;
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
                            return;
                        }
                        case "actionbar" -> {
                            source.sendMessage(GeneralUtils.actionbarHelpMessage);
                            source.sendMessage(GeneralUtils.actionbarwikilink);
                            return;
                        }
                        case "bossbar" -> {
                            source.sendMessage(GeneralUtils.bossbarHelpMessage);
                            source.sendMessage(GeneralUtils.bossbarwikilink);
                            return;
                        }
                        default -> {
                            source.sendMessage(GeneralUtils.titleHelpMessage);
                            source.sendMessage(GeneralUtils.actionbarHelpMessage);
                            source.sendMessage(GeneralUtils.bossbarHelpMessage);
                            source.sendMessage(GeneralUtils.fullwikilink);
                            return;
                        }
                    }
                } else {
                    source.sendMessage(GeneralUtils.titleHelpMessage);
                    source.sendMessage(GeneralUtils.actionbarHelpMessage);
                    source.sendMessage(GeneralUtils.bossbarHelpMessage);
                    source.sendMessage(GeneralUtils.fullwikilink);
                    return;
                }
            }
            default -> {
                ConfigUtils.invalidCommand(source);
                return;
            }
        }

    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return switch (invocation.source().getPermissionValue("announcer.command.show")){
            case FALSE -> {
                invocation.source().sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                yield false;
            }
            case UNDEFINED -> {
                ConfigUtils.sendNoMainPermission(invocation.source());
                yield false;
            }
            default -> true;
        };
    }
}
