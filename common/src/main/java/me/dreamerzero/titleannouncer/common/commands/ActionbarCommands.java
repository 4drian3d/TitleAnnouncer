package me.dreamerzero.titleannouncer.common.commands;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import net.kyori.adventure.audience.Audience;

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
                            TitleAnnouncer.formatter().audienceFormat(
                                cmd.getArgument("message", String.class),
                                adapter.getGlobalAudience()
                            ));
                        return 1;
                    }).build()
                ).build()
            )
            .then(LiteralArgumentBuilder.<A>literal("self")
                .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                    .executes(cmd -> {
                        Audience audience = adapter.toAudience(cmd.getSource());
                        audience.sendActionBar(
                            TitleAnnouncer.formatter().audienceFormat(
                                cmd.getArgument("message", String.class),
                                audience
                            ));
                        return 1;
                    })
                ).build()
            )
            .then(LiteralArgumentBuilder.<A>literal("send")
                .then(RequiredArgumentBuilder.<A, String>argument("objetive", StringArgumentType.word())
                    .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                        .executes(cmd -> {
                            Optional<? extends Audience> aud = adapter.stringToAudience(cmd.getArgument("objetive", String.class));
                            if(aud.isPresent()){
                                aud.get().sendActionBar(
                                    TitleAnnouncer.formatter().audienceFormat(
                                        cmd.getArgument("message", String.class),
                                        aud.get()
                                    ));
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
