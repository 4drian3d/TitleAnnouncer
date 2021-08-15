package net.dreamerzero.TitleAnnouncer.commands.title;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

public class AnnouncerTitleCommand implements CommandExecutor {
    private Announcer plugin;
	public AnnouncerTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //The audience that will receive the title will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    //Component that parses the title with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    //Configuration paths
    //String soundtitle = plugin.getConfig().getString("sounds.title.id");
    //Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled");

    //Sound to play
    Sound titlesound = Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.MUSIC, 10f, 2f);

    //Basic Title sender in Adventure format
    public void sendTitle(final Component anuntitle, final Component anunsubtitle) {
        //Title Duration
        final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(3000), Duration.ofMillis(1000));
        //Title Format
        final Title title = Title.title(anuntitle, anunsubtitle, times);
        //Send the title to the specified audience, in this case, the entire server.
        audience.showTitle(title);
    }

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.without-argument")));
            return true;
        } else if (args.length == 1) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.single-argument")));
            return true;
        }

        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        try {
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            sendTitle(miniMessageParse(titleandsubtitlefinal[0]), miniMessageParse(titleandsubtitlefinal[1]));
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.successfully")));
            //Sound
            if (true) { //Momentary change, I just don't want to lose the structure.
                audience.playSound(titlesound);
            }
            return true;
        } catch (Exception e) {
            sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.title.error")));
            return false;
        }
    }
}
