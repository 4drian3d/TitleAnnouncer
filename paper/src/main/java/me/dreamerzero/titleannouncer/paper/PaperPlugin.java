package me.dreamerzero.titleannouncer.paper;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import me.dreamerzero.titleannouncer.common.AnnouncerPlugin;
import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import me.dreamerzero.titleannouncer.common.commands.ActionbarCommands;
import me.dreamerzero.titleannouncer.common.commands.BossbarCommands;
import me.dreamerzero.titleannouncer.common.commands.ChatCommands;
import me.dreamerzero.titleannouncer.common.commands.TitleCommands;
import me.dreamerzero.titleannouncer.common.format.MiniPlaceholdersFormatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;
import me.dreamerzero.titleannouncer.paper.commands.ToastCommands;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public final class PaperPlugin extends JavaPlugin implements AnnouncerPlugin<CommandSourceStack> {
    
    @Override
    public void onEnable(){
        TitleAnnouncer.formatter(this.getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")
            ? new MiniPlaceholdersFormatter()
            : new RegularFormatter()
        );

        CommandAdapter<CommandSourceStack> adapter = new PaperAdapter(this);

        registerActionbar(adapter);
        registerBossbar(adapter);
        registerTitle(adapter);
        registerChat(adapter);

        this.getCommandDispatcher().getDispatcher().register(ToastCommands.toast());
    }

    private CraftServer getMCServer(){
        return (CraftServer)this.getServer();
    }

    @SuppressWarnings("all")
    private Commands getCommandDispatcher(){
        return this.getMCServer().getServer().vanillaCommandDispatcher;
    }

    @Override
    public void registerActionbar(CommandAdapter<CommandSourceStack> adapter) {
        this.getCommandDispatcher().getDispatcher().register(
            new ActionbarCommands<CommandSourceStack>(adapter)
                .actionbar(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("worldName", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            this.getServer().getWorlds().stream().map(World::getName).forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("message", StringArgumentType.string())
                            .executes(cmd -> {
                                World world = this.getServer().getWorld(cmd.getArgument("worldName", String.class));
                                if(world != null){
                                    world.sendActionBar(
                                        MiniMessage.miniMessage().deserialize(
                                            cmd.getArgument("message", String.class)));
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }).build()
                        )
                    )
                )
        );
        
    }

    @Override
    public void registerBossbar(CommandAdapter<CommandSourceStack> adapter) {
        this.getCommandDispatcher().getDispatcher().register(
            new BossbarCommands<CommandSourceStack>(adapter)
                .bossbar(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("world", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<CommandSourceStack, Float>argument("time", FloatArgumentType.floatArg())
                            .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("message", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("color", StringArgumentType.word())
                                    .suggests((ctx, builder) -> {
                                        BossBar.Color.NAMES.keys().forEach(builder::suggest);
                                        return builder.buildFuture();
                                    })
                                    .executes(cmd -> {
                                        World world = Bukkit.getServer().getWorld(cmd.getArgument("world", String.class));
                                        if(world == null) return 0;
                                        BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                        if(color == null) return 0;
                                        Component msg = MiniMessage.miniMessage().deserialize(cmd.getArgument("message", String.class));
                                        Float time = FloatArgumentType.getFloat(cmd, "time");

                                        adapter.createBossBarTask()
                                            .sendBossBar(world, time, msg, color, BossBar.Overlay.PROGRESS);
                                        return 1;
                                    })
                                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("overlay", StringArgumentType.word())
                                        .suggests((ctx, builder) -> {
                                            BossBar.Overlay.NAMES.keys().forEach(builder::suggest);
                                            return builder.buildFuture();
                                        })
                                        .executes(cmd -> {
                                            World world = Bukkit.getServer().getWorld(cmd.getArgument("world", String.class));
                                            if(world == null) return 0;
                                            BossBar.Color color = BossBar.Color.NAMES.value(cmd.getArgument("color", String.class));
                                            BossBar.Overlay overlay = BossBar.Overlay.NAMES.value(cmd.getArgument("overlay", String.class));
                                            if(color == null || overlay == null) return 0;
                                            Component msg = MiniMessage.miniMessage().deserialize(cmd.getArgument("message", String.class));
                                            Float time = FloatArgumentType.getFloat(cmd, "time");

                                            adapter.createBossBarTask().sendBossBar(world, time, msg, color, overlay);
                                            return 1;
                                        })

                                    )
                                )
                            )
                        )
                    )
                )
        );
        
    }

    @Override
    public void registerChat(CommandAdapter<CommandSourceStack> adapter) {
        this.getCommandDispatcher().getDispatcher().register(
            new ChatCommands<CommandSourceStack>(adapter)
                .chat(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("worldName", StringArgumentType.word())
                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("message", StringArgumentType.string())
                            .executes(cmd -> {
                                World world = this.getServer().getWorld(cmd.getArgument("worldName", String.class));
                                if(world == null) return 0;

                                world.sendMessage(MiniMessage.miniMessage().deserialize(cmd.getArgument("message", String.class)));
                                return 1;
                            })
                        )
                    )));
        
    }

    @Override
    public void registerTitle(CommandAdapter<CommandSourceStack> adapter) {
        this.getCommandDispatcher().getDispatcher().register(
            new TitleCommands<CommandSourceStack>(adapter)
                .title(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("worldName", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            this.getServer().getWorlds().stream().map(World::getName).forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                            .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("subtitle", StringArgumentType.string())
                                .executes(cmd -> {
                                    World world = this.getServer().getWorld(cmd.getArgument("worldName", String.class));
                                    if(world == null) return 0;

                                    Title title = Title.title(
                                    MiniMessage.miniMessage().deserialize(
                                        cmd.getArgument("title", String.class)), 
                                    MiniMessage.miniMessage().deserialize(
                                        cmd.getArgument("subtitle", String.class)));
                                    world.showTitle(title);

                                    return 1;
                                })
                            )
                        )
                    )
                ));
        
    }
}
