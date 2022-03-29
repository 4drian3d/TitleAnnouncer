package me.dreamerzero.titleannouncer.paper;

import java.util.Collection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.dreamerzero.titleannouncer.common.commands.CommandAdapter;
import net.kyori.adventure.audience.Audience;
import net.minecraft.commands.CommandSourceStack;

public final class PaperAdapter implements CommandAdapter<CommandSourceStack> {

    @Override
    public Audience getGlobalAudience() {
        return Bukkit.getServer();
    }

    @Override
    public Optional<Audience> stringToAudience(String string) {
        return Optional.ofNullable(Bukkit.getServer().getPlayer(string));
    }

    @Override
    public Collection<String> getSuggestions() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }

    @Override
    public @Nullable Audience toAudience(@NotNull CommandSourceStack object) {
        return object.getBukkitSender();
    }
    
}
