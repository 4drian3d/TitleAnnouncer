package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;
import net.kyori.adventure.audience.Audience;

public class AnnouncerActionbarCommand implements CommandExecutor {
    private final PaperPlaceholders placeholders;
    public AnnouncerActionbarCommand(){
        this.placeholders = new PaperPlaceholders();
    }

    // Command
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);
        // The audience that will receive the actionbar will be all the players on the server.
        Audience audience = Bukkit.getServer();

        // Send to all
        if (sender instanceof Player player) {
            audience.sendActionBar(placeholders.applyPlaceholders(actionbartext, player));
            ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, audience);
            ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
        } else {
            audience.sendActionBar(placeholders.applyPlaceholders(actionbartext));
            ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, audience);
            ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
        }
        return true;
    }
}
