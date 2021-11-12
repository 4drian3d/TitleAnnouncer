package net.dreamerzero.titleannouncer.paper.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;

public class SelfChatCommand implements CommandExecutor {
    private PaperPlaceholders placeholders;
    public SelfChatCommand(){
        this.placeholders = new PaperPlaceholders();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        if(args.length == 0) {
            ConfigUtils.noChatArgumentProvided(sender);
            return false;
        }

        String chattext = GeneralUtils.getCommandString(args);

        sender.sendMessage(placeholders.applyPlaceholders(chattext, player));
        ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
        ConfigUtils.playPaperSound(ComponentType.CHAT, sender);
        return true;
    }
}
