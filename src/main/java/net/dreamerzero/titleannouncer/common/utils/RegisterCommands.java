package net.dreamerzero.titleannouncer.common.utils;

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
	static final Announcer plugin = Announcer.getInstance();
    public static void registerBossbar() {
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
        plugin.getCommand("announcer")
			.setExecutor(new AnnouncerCommand());
    }
}
