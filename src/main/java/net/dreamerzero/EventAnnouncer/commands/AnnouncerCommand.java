package net.dreamerzero.EventAnnouncer.commands;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.EventAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

public class AnnouncerCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private Announcer plugin;
	public AnnouncerCommand(Announcer plugin) {
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
        switch(args.length){
            case 0: sender.sendMessage(pvktext.append(Component.text("Hola Eventor(a), necesitas introducir los valores necesarios para la ejecución del comando", NamedTextColor.GRAY)));
                    break;
            case 1: 
            case 2:
            case 3: sender.sendMessage(pvktext.append(Component.text("Hola Eventor(a), necesitas introducir el nombre del warp completo porfavor, o sea /anunciarevento <gradient:red:white>Nuevo <gradient:white:red>Evento <gold>/warp (nombre de tu evento)", NamedTextColor.WHITE)));
                    break;
            //Title + SubTitle
            case 4: sendTitle(miniMessageParse(args[0] + " " + args[1]), miniMessageParse(args[2] + " " + args[3]));
                    sender.sendMessage(pvktext.append(Component.text("Mensaje Enviado Correctamente", NamedTextColor.GREEN)));
                    break;
            default: sender.sendMessage(Component.text("Hola Eventor(a), necesitas introducir el solo el /warp y el nombre del warp. Hay posibilidad que te hayas confundido poniendo '<aqua>/pevento <red> /warp &5evento' o parecido. Actualmente solo esta permitido 2 palabras.", NamedTextColor.GRAY));
                    break;
        }
        return true;
    }
}