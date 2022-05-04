package me.dreamerzero.titleannouncer.common.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import net.kyori.adventure.bossbar.BossBar;

public class BossbarCommands<A> {
    private final CommandAdapter<A> adapter;

    public BossbarCommands(CommandAdapter<A> adapter){
        this.adapter = adapter;
    }

    public LiteralArgumentBuilder<A> bossbar(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("announcebossbar")
            .then(LiteralArgumentBuilder.<A>literal("global")
                .then(RequiredArgumentBuilder.<A, Float>argument("time", FloatArgumentType.floatArg())
                    .then(RequiredArgumentBuilder.<A, String>argument("message", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<A, String>argument("color", StringArgumentType.word())
                            .suggests((ctx, builder) -> {
                                BossBar.Color.NAMES.keys().forEach(builder::suggest);
                                return builder.buildFuture();
                            })
                            .executes(cmd -> {
                                BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                if(color == null) return 0;
                                Float time = FloatArgumentType.getFloat(cmd, "time");

                                adapter.createBossBarTask()
                                    .sendBossBar(adapter.getGlobalAudience(), time, cmd.getArgument("message", String.class), color, BossBar.Overlay.PROGRESS);
                                return 1;
                            })
                            .then(RequiredArgumentBuilder.<A, String>argument("overlay", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    BossBar.Overlay.NAMES.keys().forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(cmd -> {
                                    BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                    BossBar.Overlay overlay = BossBar.Overlay.NAMES.value(cmd.getArgument("overlay", String.class));
                                    if(color == null || overlay == null) return 0;
                                    Float time = FloatArgumentType.getFloat(cmd, "time");

                                    adapter.createBossBarTask()
                                        .sendBossBar(adapter.getGlobalAudience(), time, cmd.getArgument("message", String.class), color, overlay);
                                    return 1;
                                })

                            )
                        )
                    )
                )
            )
            .then(LiteralArgumentBuilder.<A>literal("send").build())
            .then(LiteralArgumentBuilder.<A>literal("self").build())
            .then(platformArgument.build());
    }
}
