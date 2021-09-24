package net.dreamerzero.titleannouncer.utils;

import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.AnnouncerBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.SelfBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.SendBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.WorldBossbarCommand;
import net.dreamerzero.titleannouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.SelfTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.SendTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.WorldTitleCommand;

public class RegisterCommands {
    public static void registerBossbar() {
        final var plugin = Announcer.getInstance();
        plugin.getCommand("announcebossbar")
			.setExecutor(new AnnouncerBossbarCommand(plugin));
		plugin.getCommand("selfbossbar")
			.setExecutor(new SelfBossbarCommand(plugin));
		plugin.getCommand("worldbossbar")
			.setExecutor(new WorldBossbarCommand(plugin));
		plugin.getCommand("sendbossbar")
			.setExecutor(new SendBossbarCommand(plugin));
    }
    public static void registerTitle() {
        final var plugin = Announcer.getInstance();
        plugin.getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand());
		plugin.getCommand("selftitle")
			.setExecutor(new SelfTitleCommand(plugin));
		plugin.getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand(plugin));
		plugin.getCommand("sendtitle")
			.setExecutor(new SendTitleCommand());
    }
    public static void registerActionbar() {
        final var plugin = Announcer.getInstance();
        plugin.getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand(plugin));
		plugin.getCommand("selfactionbar")
			.setExecutor(new SelfActionbarCommand(plugin));
		plugin.getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand(plugin));
		plugin.getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand(plugin));
    }
    public static void registerMainCommand(){
        final var plugin = Announcer.getInstance();
        plugin.getCommand("announcer")
			.setExecutor(new AnnouncerCommand(plugin));
    }
}
