package net.dreamerzero.titleannouncer.commands.title;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.utils.TitleUtil;
import net.kyori.adventure.util.TriState;

/*
This command will be executed as a test of the "/anunciarevento" command.
It will only be sent for the same player.
*/
public class SelfTitleCommand implements CommandExecutor {
    private final Announcer plugin;
    public SelfTitleCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an title to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        // Permission Check
        if (sender.permissionValue("announcer.title.test") != TriState.TRUE) {
            ConfigUtils.sendNoTitlePermission(sender);
            return true;
        }

        // The command requires arguments to work
        if(args.length == 0){
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = TitleUtil.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            if(PlaceholderUtil.placeholderAPIHook()){
                TitleUtil.sendOnlyTitle(
                    MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        PlaceholderAPI.setPlaceholders(player, titleandsubtitle)), replacePlaceholders(player)),
                        sender, 1000, 3000, 1000);
                ConfigUtils.sendTitleConfirmation(sender);
                ConfigUtils.playTitleSound(sender);
                return true;
            } else {
                TitleUtil.sendOnlyTitle(
                    MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        titleandsubtitle), replacePlaceholders(player)),
                        sender, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(sender);
                    return true;
            }
        }

        String titleandsubtitlefinal[];

        if(TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender) == null){
            return false;
        } else {
            titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);
        }

        if(PlaceholderUtil.placeholderAPIHook()){
            String title = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]));
            String subtitle = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]));
            // Send the Title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(title, replacePlaceholders(player)), 
                MiniMessageUtil.parse(subtitle, replacePlaceholders(player)), 
                sender,
                1000,
                3000,
                1000);
            ConfigUtils.sendTitleConfirmation(sender);
            ConfigUtils.playTitleSound(sender);
            return true;
        } else {
            // Send the Title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(player)), 
                MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(player)), 
                sender,
                1000,
                3000,
                1000);
            ConfigUtils.sendTitleConfirmation(sender);
            ConfigUtils.playTitleSound(sender);
            return true;
        }
    }
}
