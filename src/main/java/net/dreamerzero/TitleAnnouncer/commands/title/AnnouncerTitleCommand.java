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
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

public class AnnouncerTitleCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private Announcer plugin;
	public AnnouncerTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //The audience that will receive the title will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    //Static component of the Peruviankkit abbreviation
    static final TextComponent pvktext = Component.text("[", NamedTextColor.DARK_GRAY)
        .append(Component.text("P", NamedTextColor.DARK_RED))
	    .append(Component.text("V", NamedTextColor.WHITE))
	    .append(Component.text("K", NamedTextColor.DARK_RED))
        .append(Component.text("] ", NamedTextColor.DARK_GRAY));

    //Component that parses the title with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    static final Sound titlesound = Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.MUSIC, 10f, 2f);

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
        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++){
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
        if(Announcer.pvkmode){
            switch(args.length){
                case 0: sender.sendMessage(pvktext.append(Component.text("Hola Eventor(a), necesitas introducir los valores necesarios para la ejecución del comando", NamedTextColor.GRAY)));
                        break;
                case 1: sender.sendMessage(pvktext.append(Component.text("Hola Eventor(a), necesitas introducir el titulo y el subtitulo del anuncio junto con el ; para poder enviarlo, o sea /anunciarevento <gradient:red:white>Nuevo <gradient:white:red>Evento;<gold>/warp (nombre de tu evento)", NamedTextColor.WHITE)));
                        break;
                //Title + SubTitle
                default: sendTitle(miniMessageParse(titleandsubtitlefinal[0]), 
                                miniMessageParse(titleandsubtitlefinal[1]));
                        audience.playSound(titlesound);        
                        sender.sendMessage(pvktext.append(Component.text("Mensaje Enviado Correctamente", NamedTextColor.GREEN)));
                        break;
            }
            return true;
        } else {
            switch(args.length){
                case 0: sender.sendMessage(Component.text("You need to enter the title and subtitle arguments.", NamedTextColor.GRAY));
                        break;
                case 1: sender.sendMessage(Component.text("You need to enter the title, the subtitle and the separator ';' in orden to send the title for example: /titleevento <gradient:red:white>New <gradient:white:red>Event;<gold>/warp <gradient:red:white>Event", NamedTextColor.WHITE));
                        break;
                //Title + SubTitle
                default: sendTitle(miniMessageParse(titleandsubtitlefinal[0]), 
                                miniMessageParse(titleandsubtitlefinal[1]));
                        audience.playSound(titlesound);
                        sender.sendMessage(Component.text("Title succesfully sended", NamedTextColor.GREEN));
                        break;
            }
            return true;
        }
    }
}