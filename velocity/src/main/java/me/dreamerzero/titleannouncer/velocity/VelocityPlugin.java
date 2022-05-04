package me.dreamerzero.titleannouncer.velocity;

import java.util.Optional;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import me.dreamerzero.titleannouncer.common.AnnouncerPlugin;
import me.dreamerzero.titleannouncer.common.Constants;
import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import me.dreamerzero.titleannouncer.common.commands.ActionbarCommands;
import me.dreamerzero.titleannouncer.common.commands.BossbarCommands;
import me.dreamerzero.titleannouncer.common.commands.ChatCommands;
import me.dreamerzero.titleannouncer.common.commands.TitleCommands;
import me.dreamerzero.titleannouncer.common.format.MiniPlaceholdersFormatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.title.Title;

@Plugin(
    id = Constants.ID,
    name = Constants.NAME,
    version = Constants.VERSION,
    dependencies = {
        @Dependency(
            id = "miniplaceholders",
            optional = true
        ),
        @Dependency(
            id = "protocolize",
            optional = true
        )
    }
)
public final class VelocityPlugin implements AnnouncerPlugin<CommandSource> {
    final ProxyServer proxy;
    private final CommandManager cManager;

    @Inject
    public VelocityPlugin(ProxyServer proxy, CommandManager cManager){
        this.proxy = proxy;
        this.cManager = cManager;
    }

    @Subscribe
    public void onStartup(ProxyInitializeEvent event){
        TitleAnnouncer.formatter(proxy.getPluginManager().isLoaded("miniplaceholders")
            ? new MiniPlaceholdersFormatter()
            : new RegularFormatter()
        );
        final VelocityAdapter adapter = new VelocityAdapter(this);

        this.registerActionbar(adapter);
        this.registerBossbar(adapter);
        this.registerChat(adapter);
        this.registerTitle(adapter);
    }

    @Override
    public void registerActionbar(CommandAdapter<CommandSource> adapter) {
        var actionbarCommand = new ActionbarCommands<CommandSource>(adapter)
            .actionbar(
                LiteralArgumentBuilder.<CommandSource>literal("server")
                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("serverName", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            proxy.getAllServers().stream().map(sv -> sv.getServerInfo().getName()).forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("message", StringArgumentType.string())
                            .executes(cmd -> {
                                String server = cmd.getArgument("serverName", String.class);
                                Optional<RegisteredServer> optionalSv = proxy.getServer(server);
                                if(optionalSv.isPresent()){
                                    optionalSv.get().sendActionBar(
                                        TitleAnnouncer.formatter().audienceFormat(
                                            cmd.getArgument("message", String.class),
                                            optionalSv.get()
                                        ));
                                    return 1;
                                } else {
                                    return 0;
                                }
                            })
                        )
                    )
            );
        BrigadierCommand actionbar = new BrigadierCommand(actionbarCommand);
        CommandMeta actionbarMeta = cManager.metaBuilder(actionbar).plugin(this).build();

        cManager.register(actionbarMeta, actionbar);
        
    }

    @Override
    public void registerBossbar(CommandAdapter<CommandSource> adapter) {
        var actionbarCommand = new BossbarCommands<CommandSource>(adapter)
            .bossbar(
                LiteralArgumentBuilder.<CommandSource>literal("server")
                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("server", StringArgumentType.string())
                        .suggests((ctx, builder) -> {
                            proxy.getAllServers().stream().map(sv -> sv.getServerInfo().getName()).forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .then(RequiredArgumentBuilder.<CommandSource, Float>argument("time", FloatArgumentType.floatArg())
                            .then(RequiredArgumentBuilder.<CommandSource, String>argument("message", StringArgumentType.string())
                                .executes(cmd -> {
                                    proxy.getServer(cmd.getArgument("server", String.class)).ifPresent(server -> 
                                        adapter.createBossBarTask().sendBossBar(
                                            server,
                                            FloatArgumentType.getFloat(cmd, "time"),
                                            TitleAnnouncer.formatter().audienceFormat(
                                                cmd.getArgument("message", String.class), server
                                            ),
                                            BossBar.Color.PURPLE,
                                            BossBar.Overlay.PROGRESS
                                        )
                                    );
                                    
                                    return 1;
                                })
                                .then(RequiredArgumentBuilder.<CommandSource, String>argument("color", StringArgumentType.word())
                                    .suggests((ctx, builder) -> {
                                        BossBar.Color.NAMES.keys().forEach(builder::suggest);
                                        return builder.buildFuture();
                                    })
                                    .executes(cmd -> {
                                        final BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                        if(color == null) return 0;
                                        final float time = FloatArgumentType.getFloat(cmd, "time");
                                        proxy.getServer(cmd.getArgument("server", String.class)).ifPresent(server -> 
                                            adapter.createBossBarTask().sendBossBar(
                                                server,
                                                time,
                                                TitleAnnouncer.formatter().audienceFormat(
                                                    cmd.getArgument("message", String.class), server
                                                ),
                                                color,
                                                BossBar.Overlay.PROGRESS
                                            )
                                        );
                                        
                                        return 1;
                                    })
                                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("overlay", StringArgumentType.word())
                                        .suggests((ctx, builder) -> {
                                            BossBar.Overlay.NAMES.keys().forEach(builder::suggest);
                                            return builder.buildFuture();
                                        })
                                        .executes(cmd -> {
                                            final BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                            final BossBar.Overlay overlay = BossBar.Overlay.NAMES.value(cmd.getArgument("overlay", String.class));
                                            if(color == null || overlay == null) return 0;
                                            
                                            final float time = FloatArgumentType.getFloat(cmd, "time");

                                            proxy.getServer(cmd.getArgument("server", String.class)).ifPresent(server -> 
                                                adapter.createBossBarTask().sendBossBar(
                                                    server,
                                                    time,
                                                    TitleAnnouncer.formatter().audienceFormat(
                                                        cmd.getArgument("message", String.class), server
                                                    ),
                                                    color,
                                                    BossBar.Overlay.PROGRESS
                                                )
                                            );
                                            return 1;
                                        })
                                    )
                                )
                            )
                        )
                    )
            );
        BrigadierCommand actionbar = new BrigadierCommand(actionbarCommand);
        CommandMeta actionbarMeta = cManager.metaBuilder(actionbar).plugin(this).build();

        cManager.register(actionbarMeta, actionbar);
        
    }

    @Override
    public void registerChat(CommandAdapter<CommandSource> adapter) {
        var chatCommand = new ChatCommands<>(adapter)
            .chat(LiteralArgumentBuilder.<CommandSource>literal("server")
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("server", StringArgumentType.word())
                    .suggests((ctx, builder) -> {
                        proxy.getAllServers().stream().map(sv -> sv.getServerInfo().getName()).forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("message", StringArgumentType.string())
                        .executes(cmd -> {
                            var optional = proxy.getServer(cmd.getArgument("server", String.class));
                            if(optional.isPresent()) {
                                optional.get().sendMessage(
                                    TitleAnnouncer.formatter().audienceFormat(
                                        cmd.getArgument("message", String.class),
                                        optional.get()
                                    ));
                                return Command.SINGLE_SUCCESS;
                            }
                            return BrigadierCommand.FORWARD;
                        })
                    )
                )
            );

        BrigadierCommand chat = new BrigadierCommand(chatCommand);
        CommandMeta chatMeta = cManager.metaBuilder(chat).plugin(this).build();

        cManager.register(chatMeta, chat);
        
    }

    @Override
    public void registerTitle(CommandAdapter<CommandSource> adapter) {
        var titleCommand = new TitleCommands<>(adapter)
            .title(LiteralArgumentBuilder.<CommandSource>literal("server")
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("server", StringArgumentType.word())
                    .suggests((ctx, builder) -> {
                        proxy.getAllServers().stream().map(sv -> sv.getServerInfo().getName()).forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("title", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("subtitle", StringArgumentType.string())
                            .executes(cmd -> {
                                final Optional<RegisteredServer> audience = proxy.getServer(cmd.getArgument("server", String.class));
                                if(audience.isEmpty()) return BrigadierCommand.FORWARD;
                                final RegisteredServer aud = audience.get();
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
            );

        BrigadierCommand title = new BrigadierCommand(titleCommand);
        CommandMeta titleMeta = cManager.metaBuilder(title).plugin(this).build();

        cManager.register(titleMeta, title);
        
    }


}
