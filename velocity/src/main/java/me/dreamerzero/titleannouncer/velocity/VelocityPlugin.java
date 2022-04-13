package me.dreamerzero.titleannouncer.velocity;

import java.util.Optional;

import com.google.inject.Inject;
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
import me.dreamerzero.titleannouncer.common.format.MiniPlaceholdersFormatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;

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
        VelocityAdapter adapter = new VelocityAdapter(this);

        this.registerActionbar(adapter);
    }

    @Override
    public void registerActionbar(CommandAdapter<CommandSource> adapter) {
        LiteralArgumentBuilder<CommandSource> actionbarCommand = new ActionbarCommands<CommandSource>(adapter)
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
                                        MiniMessage.miniMessage().deserialize(
                                            cmd.getArgument("message", String.class)));
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
        LiteralArgumentBuilder<CommandSource> actionbarCommand = new BossbarCommands<CommandSource>(adapter)
            .bossbar(
                LiteralArgumentBuilder.<CommandSource>literal("server")
                    .then(RequiredArgumentBuilder.<CommandSource, String>argument("server", StringArgumentType.string())
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerTitle(CommandAdapter<CommandSource> adapter) {
        // TODO Auto-generated method stub
        
    }


}
