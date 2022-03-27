package me.dreamerzero.titleannouncer.common.commands;

import java.util.function.Function;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.kyori.adventure.audience.Audience;

public class ChatCommands<A> {
    private final CommandAdapter adapter;
    private Function<A, Audience> fromAToAudience = a -> Audience.empty();

    public ChatCommands(CommandAdapter adapter, Function<A, Audience> fromAToAudience){
        this.adapter = adapter;
        this.fromAToAudience = fromAToAudience;
    }

    public LiteralArgumentBuilder<A> bossbar(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announcechat")
            .then(LiteralArgumentBuilder.<A>literal("global").build())
            .then(LiteralArgumentBuilder.<A>literal("send").build())
            .then(LiteralArgumentBuilder.<A>literal("self").build())
            .then(platformArgument.build());
    }
}
