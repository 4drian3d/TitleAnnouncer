package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;
import net.kyori.adventure.bossbar.BossBar;

public class SelfBossbarCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return;
        }

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time;
        if(BossBarUtils.validBossbarNumber(args[0], sender) == 0.1f){
            return;
        } else {
            time = BossBarUtils.validBossbarNumber(args[0], sender);
        }

        BossBar.Color color = BossBarUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(
                ConfigUtils.getPrefix().append(
                    MiniMessageUtil.parse("<dark_red>Invalid Argument")));
            return;
        }

        // Send to all
        BossBarUtils.sendVelocityBossbar(
            sender,
            time,
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    bossbartext),
                    PlaceholderUtil.replaceProxyPlaceholders(player)),
            color,
            overlay);
        ConfigUtils.sendBossbarConfirmation(sender);
        SoundUtil.playProxyBossbarSound(player);
        return;
    }
}