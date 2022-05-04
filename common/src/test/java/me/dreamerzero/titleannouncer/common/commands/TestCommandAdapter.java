package me.dreamerzero.titleannouncer.common.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.dreamerzero.titleannouncer.common.adapter.BossBarTask;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import me.dreamerzero.titleannouncer.common.audience.TestAudience;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;

public class TestCommandAdapter implements CommandAdapter<TestAudience> {

    @Override
    public @NotNull Audience getGlobalAudience() {
        return Audience.empty();
    }

    @Override
    public Optional<Audience> stringToAudience(@NotNull String string) {
        return Optional.empty();
    }

    @Override
    public @Nullable Audience toAudience(@NotNull TestAudience object) {
        return object;
    }

    @Override
    public @NotNull Collection<String> getSuggestions() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull BossBarTask createBossBarTask() {
        return new BossBarTask() {

            @Override
            public void sendBossBar(@NotNull Audience audience, float time, @NotNull String content,
                    @NotNull BossBar.Color color, @NotNull BossBar.Overlay type) {
                
            }
            
        };
    }
    
}
