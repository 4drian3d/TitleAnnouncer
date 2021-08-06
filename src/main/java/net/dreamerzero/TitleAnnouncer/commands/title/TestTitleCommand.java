package net.dreamerzero.TitleAnnouncer.commands.title;

import java.time.Duration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

/*
This command will be executed as a test of the "/anunciarevento" command. 
It will only be sent for the same player.
*/
public class TestTitleCommand implements CommandExecutor {
    private Announcer plugin;
	public TestTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Component that parses the title with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    static final Sound titlesound = Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.MUSIC, 10f, 2f);

    //Basic Title sender in Adventure format
    public void sendTitle(final Component anuntitle, final Component anunsubtitle, final Audience target) {
        //Title Duration
        final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(3000), Duration.ofMillis(1000));
        //Title Format
        final Title title = Title.title(anuntitle, anunsubtitle, times);
        //Send the title to sender
        target.showTitle(title);
    }

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.without-argument")));
            return true;
        } else if (args.length == 1) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.single-argument")));
            return true;
        }

        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++){
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        try {
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            sendTitle(miniMessageParse(titleandsubtitlefinal[0]), miniMessageParse(titleandsubtitlefinal[1]), sender);
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.successfully")));
            return true;
        } catch (Exception e) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.error")));
            return false;
        }
    }
}