package net.dreamerzero.titleannouncer.paper.utils;

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
}
