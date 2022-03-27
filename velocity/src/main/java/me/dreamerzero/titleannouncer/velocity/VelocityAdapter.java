package me.dreamerzero.titleannouncer.velocity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import me.dreamerzero.titleannouncer.common.commands.CommandAdapter;
import net.kyori.adventure.audience.Audience;

public class VelocityAdapter implements CommandAdapter {
    private final ProxyServer proxy;

    public VelocityAdapter(ProxyServer proxy){
        this.proxy = proxy;
    }

    @Override
    public Audience getGlobalAudience() {
        return this.proxy;
    }

    @Override
    public Optional<Audience> stringToAudience(String string) {
        return proxy.getPlayer(string).map(Function.identity());
    }

    @Override
    public Collection<String> getSuggestions() {
        return proxy.getAllPlayers().stream().map(Player::getUsername).toList();
    }
    
}