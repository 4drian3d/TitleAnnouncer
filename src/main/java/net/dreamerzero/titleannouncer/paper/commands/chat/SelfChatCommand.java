package net.dreamerzero.titleannouncer.paper.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SelfChatCommand implements CommandExecutor {
    private MiniMessage mm;
    public SelfChatCommand(MiniMessage mm){
        this.mm = mm;
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

        sender.sendMessage(mm.deserialize(
            MiniMessageUtil.replaceLegacy(
                Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, chattext) : chattext), 
            PPlaceholders.replacePlaceholders(player)));
        ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
        ConfigUtils.playPaperSound(ComponentType.CHAT, sender);
        return true;
    }
}
