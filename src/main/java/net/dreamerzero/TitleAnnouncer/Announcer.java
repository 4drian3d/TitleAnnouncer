package net.dreamerzero.TitleAnnouncer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.TitleAnnouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.TestActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.TestTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.WorldTitleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Announcer extends JavaPlugin {
	// Component to send the server name: Peruviankkit... in color... in console
	static final TextComponent pvknet = 
		Component.text("Peru", 
			NamedTextColor.DARK_RED)
		.append(Component.text("vian", 
				NamedTextColor.WHITE))
		.append(Component.text("kkit", 
				NamedTextColor.DARK_RED))
		.append(Component.text(" Network", 
				NamedTextColor.GREEN));
	// Plugin Name with color
	static final TextComponent eventannouncertext = 
		Component.text("Title", 
			NamedTextColor.RED)
		.append(Component.text("Announcer", 
			NamedTextColor.WHITE));
	// Line
	static final TextComponent linelong = 
		Component.text("----------------------", 
			NamedTextColor.DARK_GRAY);
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(
			Component.text("Enabling ", 
				NamedTextColor.AQUA)
			.append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
		pluginConfiguration();
		commandRegister();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(
			Component.text("Disabling ", 
				NamedTextColor.AQUA)
			.append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
	}
	
	// Registration of the commands that the plugin provides
	public void commandRegister() {
		// Title Commands
		getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand(this));
		getCommand("testtitle")
			.setExecutor(new TestTitleCommand(this));
		getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand(this));
		// ActionBar Commands
		getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand(this));
		getCommand("testactionbar")
			.setExecutor(new TestActionbarCommand(this));
		getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand(this));
	}

	public void pluginConfiguration() {
		File config = new File(this.getDataFolder(), "config.yml");
		if(!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
	}
}
