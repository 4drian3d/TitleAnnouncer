package me.dreamerzero.titleannouncer.common.commands;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
                            Title title = Title.title(
                                MiniMessage.miniMessage().deserialize(
                                    cmd.getArgument("title", String.class)), 
                                MiniMessage.miniMessage().deserialize(
                                    cmd.getArgument("subtitle", String.class)));
                            adapter.getGlobalAudience().showTitle(title);
                            return 1;
                        })
                    )
                )
            )
            .then(LiteralArgumentBuilder.<A>literal("send")
                .then(RequiredArgumentBuilder.<A, String>argument("objetive", StringArgumentType.word())
                    .then(RequiredArgumentBuilder.<A, String>argument("title", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<A, String>argument("subtitle", StringArgumentType.string())
                            .executes(cmd -> {
                                Optional<Audience> audience = adapter.stringToAudience(cmd.getArgument("objetive", String.class));
                                if(audience.isEmpty()) return 0;
                                Title title = Title.title(
                                    MiniMessage.miniMessage().deserialize(
                                        cmd.getArgument("title", String.class)), 
                                    MiniMessage.miniMessage().deserialize(
                                        cmd.getArgument("subtitle", String.class)));
                                audience.get().showTitle(title);
                                return 1;
                            })
                        )
                    )
                )
            
            )
            .then(LiteralArgumentBuilder.<A>literal("self")
                .then(RequiredArgumentBuilder.<A, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<A, String>argument("subtitle", StringArgumentType.string())
                        .executes(cmd -> {
                            Audience audience = adapter.toAudience(cmd.getSource());
                            Title title = Title.title(
                                MiniMessage.miniMessage().deserialize(
                                    cmd.getArgument("title", String.class)), 
                                MiniMessage.miniMessage().deserialize(
                                    cmd.getArgument("subtitle", String.class)));
                            audience.showTitle(title);
                            return 1;
                        })
                    )
                )
            )
            .then(platformArgument.build());
    }
}
