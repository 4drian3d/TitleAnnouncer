package me.dreamerzero.titleannouncer.common.commands;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ActionbarCommands<A> {
    private final CommandAdapter<A> adapter;

    public ActionbarCommands(CommandAdapter<A> adapter){
        this.adapter = adapter;
    }

    public LiteralArgumentBuilder<A> actionbar(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announceactionbar")
            .then(LiteralArgumentBuilder.<A>literal("global")
                .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                    .executes(cmd -> {
                        adapter.getGlobalAudience().sendActionBar(
                            MiniMessage.miniMessage().deserialize(
                                cmd.getArgument("message", String.class)));
                        return 1;
                    }).build()
                ).build()
            )
            .then(LiteralArgumentBuilder.<A>literal("self")
                .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                    .executes(cmd -> {
                        adapter.toAudience(cmd.getSource()).sendActionBar(
                            MiniMessage.miniMessage().deserialize(
                                cmd.getArgument("message", String.class)));
                        return 1;
                    })
                ).build()
            )
            .then(LiteralArgumentBuilder.<A>literal("send")
                .then(RequiredArgumentBuilder.<A, String>argument("objetive", StringArgumentType.word())
                    .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                        .executes(cmd -> {
                            Optional<Audience> aud = adapter.stringToAudience(cmd.getArgument("objetive", String.class));
                            if(aud.isPresent()){
                                aud.get().sendActionBar(
                                    MiniMessage.miniMessage().deserialize(
                                        cmd.getArgument("message", String.class)));
                                return 1;
                            } else {
                                return 0;
                            }
                            
                        })
                    ).build()
                ).build() 
            )
            .then(platformArgument.build());

    }
}
