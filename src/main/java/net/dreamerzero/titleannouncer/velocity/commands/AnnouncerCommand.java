package net.dreamerzero.titleannouncer.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.permission.Tristate;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityHelpMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record AnnouncerCommand(MiniMessage mm) implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length == 0) {
            source.sendMessage(mm.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            ConfigUtils.helpPrefix(source);
            source.sendMessage(VelocityHelpMessages.titleHelpMessage);
            source.sendMessage(VelocityHelpMessages.actionbarHelpMessage);
            source.sendMessage(VelocityHelpMessages.bossbarHelpMessage);
            source.sendMessage(VelocityHelpMessages.fullwikilink);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                new ConfigManager().getConfig().forceReload();
                ConfigUtils.reloadMessage(source);
            }
            case "help" -> {
                source.sendMessage(
                    mm.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                ConfigUtils.helpPrefix(source);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            source.sendMessage(VelocityHelpMessages.titleHelpMessage);
                            source.sendMessage(VelocityHelpMessages.titlewikilink);
                        }
                        case "actionbar" -> {
                            source.sendMessage(VelocityHelpMessages.actionbarHelpMessage);
                            source.sendMessage(VelocityHelpMessages.actionbarwikilink);
                        }
                        case "bossbar" -> {
                            source.sendMessage(VelocityHelpMessages.bossbarHelpMessage);
                            source.sendMessage(VelocityHelpMessages.bossbarwikilink);
                        }
                        default -> {
                            source.sendMessage(VelocityHelpMessages.titleHelpMessage);
                            source.sendMessage(VelocityHelpMessages.actionbarHelpMessage);
                            source.sendMessage(VelocityHelpMessages.bossbarHelpMessage);
                            source.sendMessage(VelocityHelpMessages.fullwikilink);
                        }
                    }
                } else {
                    source.sendMessage(VelocityHelpMessages.titleHelpMessage);
                    source.sendMessage(VelocityHelpMessages.actionbarHelpMessage);
                    source.sendMessage(VelocityHelpMessages.bossbarHelpMessage);
                    source.sendMessage(VelocityHelpMessages.fullwikilink);
                }
            }
            default -> {
                ConfigUtils.invalidCommand(source);
            }
        }

    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().getPermissionValue("titleannouncer.command.show") == Tristate.TRUE;
    }
}
