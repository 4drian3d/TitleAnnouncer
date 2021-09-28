package net.dreamerzero.titleannouncer.common.utils;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.AnnouncerBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.SelfBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.SendBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.WorldBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.SelfTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.SendTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.WorldTitleCommand;

public class RegisterCommands {
	
    public static void registerBossbar() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcebossbar")
			.setExecutor(new AnnouncerBossbarCommand());
		plugin.getCommand("selfbossbar")
			.setExecutor(new SelfBossbarCommand());
		plugin.getCommand("worldbossbar")
			.setExecutor(new WorldBossbarCommand());
		plugin.getCommand("sendbossbar")
			.setExecutor(new SendBossbarCommand());
    }
    public static void registerTitle() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand());
		plugin.getCommand("selftitle")
			.setExecutor(new SelfTitleCommand());
		plugin.getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand());
		plugin.getCommand("sendtitle")
			.setExecutor(new SendTitleCommand());
    }
    public static void registerActionbar() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand());
		plugin.getCommand("selfactionbar")
			.setExecutor(new SelfActionbarCommand());
		plugin.getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand());
		plugin.getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand());
    }
    public static void registerMainCommand(){
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcer")
			.setExecutor(new AnnouncerCommand());
    }

	public static void registerProxyBossbar(ProxyServer server){
		CommandMeta vannouncebossbar = server.getCommandManager().metaBuilder("vannouncebossbar").aliases("pannouncebossbar", "vbossbar").build();
		CommandMeta vselfbossbar = server.getCommandManager().metaBuilder("vselfbossbar").aliases("pselfbossbar", "vsfbossbar").build();
		CommandMeta vsendbossbar = server.getCommandManager().metaBuilder("vsendbossbar").aliases("psendbossbar", "vsnbossbar").build();
		CommandMeta vserverbossbar = server.getCommandManager().metaBuilder("vserverbossbar").aliases("serverbossbar", "svbossbar").build();
		server.getCommandManager().register(vannouncebossbar, new net.dreamerzero.titleannouncer.velocity.commands.bossbar.AnnouncerBossbarCommand(server));
        server.getCommandManager().register(vselfbossbar, new net.dreamerzero.titleannouncer.velocity.commands.bossbar.SelfBossbarCommand());
        server.getCommandManager().register(vsendbossbar, new net.dreamerzero.titleannouncer.velocity.commands.bossbar.SendBossbarCommand(server));
        server.getCommandManager().register(vserverbossbar, new net.dreamerzero.titleannouncer.velocity.commands.bossbar.ServerBossbarCommand(server));
	}

	public static void registerProxyTitle(ProxyServer server){
		CommandMeta vannouncetitle = server.getCommandManager().metaBuilder("vannouncetitle").aliases("pannouncetitle", "vtitle").build();
		CommandMeta vselftitle = server.getCommandManager().metaBuilder("vselftitle").aliases("pselftitle", "vsftitle").build();
		CommandMeta vsendtitle = server.getCommandManager().metaBuilder("vsendtitle").aliases("psendtitle", "vsntitle").build();
		CommandMeta vservertitle = server.getCommandManager().metaBuilder("vservertitle").aliases("servertitle", "svtitle").build();
		server.getCommandManager().register(vannouncetitle, new net.dreamerzero.titleannouncer.velocity.commands.title.AnnouncerTitleCommand(server));
        server.getCommandManager().register(vselftitle, new net.dreamerzero.titleannouncer.velocity.commands.title.SelfTitleCommand());
        server.getCommandManager().register(vsendtitle, new net.dreamerzero.titleannouncer.velocity.commands.title.SendTitleCommand(server));
        server.getCommandManager().register(vservertitle, new net.dreamerzero.titleannouncer.velocity.commands.title.ServerTitleCommand(server));
	}

	public static void registerProxyActionbar(ProxyServer server){
		CommandMeta vannounceactionbar = server.getCommandManager().metaBuilder("vannounceactionbar").aliases("pannounceactionbar", "vactionbar").build();
		CommandMeta vselfactionbar = server.getCommandManager().metaBuilder("vselfactionbar").aliases("pselfactionbar", "vsfactionbar").build();
		CommandMeta vsendactionbar = server.getCommandManager().metaBuilder("vsendactionbar").aliases("psendactionbar", "vsnactionbar").build();
		CommandMeta vserveractionbar = server.getCommandManager().metaBuilder("vserveractionbar").aliases("serveractionbar", "svactionbar").build();
		server.getCommandManager().register(vannounceactionbar, new net.dreamerzero.titleannouncer.velocity.commands.actionbar.AnnouncerActionbarCommand(server));
        server.getCommandManager().register(vselfactionbar, new net.dreamerzero.titleannouncer.velocity.commands.actionbar.SelfActionbarCommand());
        server.getCommandManager().register(vsendactionbar, new net.dreamerzero.titleannouncer.velocity.commands.actionbar.SendActionbarCommand(server));
        server.getCommandManager().register(vserveractionbar, new net.dreamerzero.titleannouncer.velocity.commands.actionbar.ServerActionbarCommand(server));
	}

	public static void registerProxyMainCommand(ProxyServer server){
		CommandMeta vannouncer = server.getCommandManager().metaBuilder("vannouncer").aliases("vtitleannouncer", "pannouncer").build();
		server.getCommandManager().register(vannouncer, new net.dreamerzero.titleannouncer.velocity.commands.AnnouncerCommand());
	}
}
