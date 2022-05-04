package me.dreamerzero.titleannouncer.common.commands;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ChatCommands<A>  {
    private final CommandAdapter<A> adapter;

    public ChatCommands(CommandAdapter<A> adapter){
        this.adapter = adapter;
    }

    public LiteralArgumentBuilder<A> chat(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announcechat")
            .then(LiteralArgumentBuilder.<A>literal("global")
                .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                    .executes(cmd -> {
                        adapter.getGlobalAudience().sendMessage(
                            TitleAnnouncer.formatter().audienceFormat(
                                cmd.getArgument("message", String.class),
                                adapter.getGlobalAudience()
                            ));
                        return 1;
                    })
                )
            )
            .then(LiteralArgumentBuilder.<A>literal("self")
                .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                    .executes(cmd -> {
                        adapter.toAudience(cmd.getSource()).sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                cmd.getArgument("message", String.class)));
                        return 1;
                    })
                )
            )
            .then(LiteralArgumentBuilder.<A>literal("send")
                .then(RequiredArgumentBuilder.<A, String>argument("objetive", StringArgumentType.word())
                    .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                        .executes(cmd -> {
                            Optional<Audience> aud = adapter.stringToAudience(cmd.getArgument("objetive", String.class));
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
                    )
                )
            )
            .then(platformArgument.build());
    }
}
