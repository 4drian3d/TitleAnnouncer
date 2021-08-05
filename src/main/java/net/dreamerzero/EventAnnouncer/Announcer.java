package net.dreamerzero.EventAnnouncer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.EventAnnouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.EventAnnouncer.commands.actionbar.TestActionbarCommand;
import net.dreamerzero.EventAnnouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.EventAnnouncer.commands.title.TestTitleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Announcer extends JavaPlugin {
	//Component to send the server name: Peruviankkit
	static final TextComponent pvknet = Component.text("Peru", NamedTextColor.DARK_RED)
		.append(Component.text("vian", NamedTextColor.WHITE))
		.append(Component.text("kkit", NamedTextColor.DARK_RED)).append(Component.text(" Network", NamedTextColor.GREEN));
	//Plugin Name
	static final TextComponent eventannouncertext = Component.text("Event", NamedTextColor.RED)
		.append(Component.text("Announcer", NamedTextColor.WHITE));
	//line
	static final TextComponent linelong = Component.text("----------------------", NamedTextColor.DARK_GRAY);
	//Peruviankkit mode, to make the use of the plugin more general.
	public static final boolean pvkmode = false;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(Component.text("Enabling ", NamedTextColor.AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
		commandRegister();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(Component.text("Disabling ", NamedTextColor.AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
	}
	
	//Registration of the 2 commands that the plugin has, for the moment.
	public void commandRegister() {
		this.getCommand("titleevento").setExecutor(new AnnouncerTitleCommand(this));
		this.getCommand("titlepevento").setExecutor(new TestTitleCommand(this));
		this.getCommand("actionbarevento").setExecutor(new AnnouncerActionbarCommand(this));
		this.getCommand("actionbarpevento").setExecutor(new TestActionbarCommand(this));
	}

}