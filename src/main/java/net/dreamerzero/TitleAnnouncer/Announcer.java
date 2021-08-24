package net.dreamerzero.TitleAnnouncer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.TitleAnnouncer.commands.AnnouncerCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.SendTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.SelfTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.WorldTitleCommand;
import net.dreamerzero.TitleAnnouncer.listeners.TabCompleteListener;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Announcer extends JavaPlugin {
	private static Announcer instance;
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
	static final Component eventannouncertext = 
		MiniMessageUtil.parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>");
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
		listenerRegister();
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
		// Main Command
		getCommand("announcer")
			.setExecutor(new AnnouncerCommand(this));
		// Title Commands
		getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand(this));
		getCommand("testtitle")
			.setExecutor(new SelfTitleCommand(this));
		getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand(this));
		getCommand("sendtitle")
			.setExecutor(new SendTitleCommand(this));
		// ActionBar Commands
		getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand(this));
		getCommand("testactionbar")
			.setExecutor(new SelfActionbarCommand(this));
		getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand(this));
		getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand(this));
	}

	public void listenerRegister(){
		getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);
	}

	public void pluginConfiguration() {
		File config = new File(this.getDataFolder(), "config.yml");
		if(!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
	}

	public static Announcer getInstance() {
		return instance;
	}
}
