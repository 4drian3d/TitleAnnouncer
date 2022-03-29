package me.dreamerzero.titleannouncer.velocity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.dreamerzero.titleannouncer.common.adapter.BossBarTask;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import net.kyori.adventure.audience.Audience;

public class VelocityAdapter implements CommandAdapter<CommandSource> {
    private final VelocityPlugin plugin;

    public VelocityAdapter(VelocityPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public Audience getGlobalAudience() {
        return this.plugin.proxy;
    }

    @Override
    public Optional<Audience> stringToAudience(String string) {
        return this.plugin.proxy.getPlayer(string).map(Function.identity());
    }

    @Override
    public Collection<String> getSuggestions() {
        return this.plugin.proxy.getAllPlayers().stream().map(Player::getUsername).toList();
    }

    @Override
    public @Nullable Audience toAudience(@NotNull CommandSource object) {
        return object;
    }

    @Override
    public @NotNull BossBarTask createBossBarTask() {
        return new VelocityBossBarTask(plugin);
    }
    
}
