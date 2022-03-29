package me.dreamerzero.titleannouncer.velocity;

import java.util.Optional;

import com.google.inject.Inject;
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
import me.dreamerzero.titleannouncer.common.commands.ActionbarCommands;
import me.dreamerzero.titleannouncer.common.commands.CommandAdapter;
import me.dreamerzero.titleannouncer.common.format.MiniPlaceholdersFormatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;
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
    private final ProxyServer proxy;
    private final CommandManager cManager;

    @Inject
    public VelocityPlugin(ProxyServer proxy, CommandManager cManager){
        this.proxy = proxy;
        this.cManager = cManager;
    }

    @Subscribe
    public void onStartup(ProxyInitializeEvent event){
        TitleAnnouncer.setFormatter(proxy.getPluginManager().isLoaded("miniplaceholders")
            ? new MiniPlaceholdersFormatter()
            : new RegularFormatter()
        );
        VelocityAdapter adapter = new VelocityAdapter(proxy);

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
        // TODO Auto-generated method stub
        
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
