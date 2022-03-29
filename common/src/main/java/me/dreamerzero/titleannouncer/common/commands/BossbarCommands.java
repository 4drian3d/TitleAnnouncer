package me.dreamerzero.titleannouncer.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.kyori.adventure.audience.Audience;

public class BossbarCommands<A> {
    private final CommandAdapter<A> adapter;

    public BossbarCommands(CommandAdapter<A> adapter){
        this.adapter = adapter;
    }

    // announcebossbar time message color overlay

    public LiteralArgumentBuilder<A> bossbar(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announcebossbar")
            .then(LiteralArgumentBuilder.<A>literal("global").build())
            .then(LiteralArgumentBuilder.<A>literal("send").build())
            .then(LiteralArgumentBuilder.<A>literal("self").build())
            .then(platformArgument.build());
    }
}
