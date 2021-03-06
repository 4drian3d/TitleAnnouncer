package net.dreamerzero.titleannouncer.velocity.utils;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.commands.bossbar.*;
import net.dreamerzero.titleannouncer.velocity.commands.chat.AnnouncerChatCommand;
import net.dreamerzero.titleannouncer.velocity.commands.chat.SelfChatCommand;
import net.dreamerzero.titleannouncer.velocity.commands.chat.SendChatCommand;
import net.dreamerzero.titleannouncer.velocity.commands.chat.ServerChatCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.dreamerzero.titleannouncer.velocity.commands.actionbar.*;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.commands.AnnouncerCommand;

public record RegisterCommands(Announcer plugin, ProxyServer server, MiniMessage mm) {

    public void registerProxyBossbar(){
		CommandMeta vannouncebossbar = server.getCommandManager().metaBuilder("vannouncebossbar").aliases("pannouncebossbar", "vbossbar").build();
		CommandMeta vselfbossbar = server.getCommandManager().metaBuilder("vselfbossbar").aliases("pselfbossbar", "vsfbossbar").build();
		CommandMeta vsendbossbar = server.getCommandManager().metaBuilder("vsendbossbar").aliases("psendbossbar", "vsnbossbar").build();
		CommandMeta vserverbossbar = server.getCommandManager().metaBuilder("vserverbossbar").aliases("serverbossbar", "svbossbar").build();
		server.getCommandManager().register(vannouncebossbar, new AnnouncerBossbarCommand(server, plugin, mm));
        server.getCommandManager().register(vselfbossbar, new SelfBossbarCommand(plugin, server, mm));
        server.getCommandManager().register(vsendbossbar, new SendBossbarCommand(server, plugin, mm));
        server.getCommandManager().register(vserverbossbar, new ServerBossbarCommand(server, plugin, mm));
	}

	public void registerProxyTitle(){
		CommandMeta vannouncetitle = server.getCommandManager().metaBuilder("vannouncetitle").aliases("pannouncetitle", "vtitle").build();
		CommandMeta vselftitle = server.getCommandManager().metaBuilder("vselftitle").aliases("pselftitle", "vsftitle").build();
		CommandMeta vsendtitle = server.getCommandManager().metaBuilder("vsendtitle").aliases("psendtitle", "vsntitle").build();
		CommandMeta vservertitle = server.getCommandManager().metaBuilder("vservertitle").aliases("servertitle", "svtitle").build();
		server.getCommandManager().register(vannouncetitle, new AnnouncerTitleCommand(server));
        server.getCommandManager().register(vselftitle, new SelfTitleCommand(server));
        server.getCommandManager().register(vsendtitle, new SendTitleCommand(server));
        server.getCommandManager().register(vservertitle, new ServerTitleCommand(server));
	}

	public void registerProxyActionbar(){
		CommandMeta vannounceactionbar = server.getCommandManager().metaBuilder("vannounceactionbar").aliases("pannounceactionbar", "vactionbar").build();
		CommandMeta vselfactionbar = server.getCommandManager().metaBuilder("vselfactionbar").aliases("pselfactionbar", "vsfactionbar").build();
		CommandMeta vsendactionbar = server.getCommandManager().metaBuilder("vsendactionbar").aliases("psendactionbar", "vsnactionbar").build();
		CommandMeta vserveractionbar = server.getCommandManager().metaBuilder("vserveractionbar").aliases("serveractionbar", "svactionbar").build();
		server.getCommandManager().register(vannounceactionbar, new AnnouncerActionbarCommand(server));
        server.getCommandManager().register(vselfactionbar, new SelfActionbarCommand(server));
        server.getCommandManager().register(vsendactionbar, new SendActionbarCommand(server));
        server.getCommandManager().register(vserveractionbar, new ServerActionbarCommand(server));
	}

	public void registerProxyChat(){
		CommandMeta vannouncechat = server.getCommandManager().metaBuilder("vannouncechat").aliases("pannouncechat", "vchat").build();
		CommandMeta vselfchat = server.getCommandManager().metaBuilder("vselfchat").aliases("pselfchat", "vsfchat").build();
		CommandMeta vsendchat = server.getCommandManager().metaBuilder("vsendchat").aliases("psendchat", "vsnchat").build();
		CommandMeta vserverchat = server.getCommandManager().metaBuilder("vserverchat").aliases("serverchat", "svchat").build();
		server.getCommandManager().register(vannouncechat, new AnnouncerChatCommand(server));
		server.getCommandManager().register(vselfchat, new SelfChatCommand(server));
		server.getCommandManager().register(vsendchat, new SendChatCommand(server));
		server.getCommandManager().register(vserverchat, new ServerChatCommand(server));
	}

	public void registerProxyMainCommand(){
		CommandMeta vannouncer = server.getCommandManager().metaBuilder("vannouncer").aliases("vtitleannouncer", "pannouncer").build();
		server.getCommandManager().register(vannouncer, new AnnouncerCommand(mm));
	}
}
