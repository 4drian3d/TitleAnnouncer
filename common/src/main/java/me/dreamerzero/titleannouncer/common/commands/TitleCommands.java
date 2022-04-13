package me.dreamerzero.titleannouncer.common.commands;

import java.util.Optional;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.title.Title;

public class TitleCommands<A> {
    private final CommandAdapter<A> adapter;

    public TitleCommands(CommandAdapter<A> adapter){
        this.adapter = adapter;
    }

    public LiteralArgumentBuilder<A> title(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announcetitle")
            .then(LiteralArgumentBuilder.<A>literal("global")
                .then(RequiredArgumentBuilder.<A, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<A, String>argument("subtitle", StringArgumentType.string())
                        .executes(cmd -> {
                            final Audience sender = adapter.toAudience(cmd.getSource());
                            adapter.getGlobalAudience().showTitle(Title.title(
                                TitleAnnouncer.formatter().audienceFormat(
                                    cmd.getArgument("title", String.class),
                                    sender
                                ), 
                                TitleAnnouncer.formatter().audienceFormat(
                                    cmd.getArgument("subtitle", String.class),
                                    sender
                                )
                            ));
                            return Command.SINGLE_SUCCESS;
                        })
                    )
                )
            )
            .then(LiteralArgumentBuilder.<A>literal("send")
                .then(RequiredArgumentBuilder.<A, String>argument("objetive", StringArgumentType.word())
                    .then(RequiredArgumentBuilder.<A, String>argument("title", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<A, String>argument("subtitle", StringArgumentType.string())
                            .executes(cmd -> {
                                final Optional<Audience> audience = adapter.stringToAudience(cmd.getArgument("objetive", String.class));
                                if(audience.isEmpty()) return 0;
                                final Audience aud = audience.get();
                                aud.showTitle(Title.title(
                                    TitleAnnouncer.formatter().audienceFormat(
                                        cmd.getArgument("title", String.class),
                                        aud
                                    ), 
                                    TitleAnnouncer.formatter().audienceFormat(
                                        cmd.getArgument("subtitle", String.class),
                                        aud
                                    )
                                ));
                                return Command.SINGLE_SUCCESS;
                            })
                        )
                    )
                )
            
            )
            .then(LiteralArgumentBuilder.<A>literal("self")
                .then(RequiredArgumentBuilder.<A, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<A, String>argument("subtitle", StringArgumentType.string())
                        .executes(cmd -> {
                            final Audience audience = adapter.toAudience(cmd.getSource());
                            audience.showTitle(Title.title(
                                TitleAnnouncer.formatter().audienceFormat(
                                    cmd.getArgument("title", String.class),
                                    audience
                                ), 
                                TitleAnnouncer.formatter().audienceFormat(
                                    cmd.getArgument("subtitle", String.class),
                                    audience
                                )
                            ));
                            return Command.SINGLE_SUCCESS;
                        })
                    )
                )
            )
            .then(platformArgument.build());
    }
}
