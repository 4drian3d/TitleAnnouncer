package me.dreamerzero.titleannouncer.paper;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import me.dreamerzero.titleannouncer.common.AnnouncerPlugin;
import me.dreamerzero.titleannouncer.common.TitleAnnouncer;
import me.dreamerzero.titleannouncer.common.commands.ActionbarCommands;
import me.dreamerzero.titleannouncer.common.commands.CommandAdapter;
import me.dreamerzero.titleannouncer.common.format.MiniPlaceholdersFormatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;
import me.dreamerzero.titleannouncer.paper.commands.ToastCommands;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class PaperPlugin extends JavaPlugin implements AnnouncerPlugin {
    
    @Override
    public void onEnable(){
        TitleAnnouncer.setFormatter(this.getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")
            ? new MiniPlaceholdersFormatter()
            : new RegularFormatter()
        );

        CommandAdapter adapter = new PaperAdapter();

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
    public void registerActionbar(CommandAdapter adapter) {
        this.getCommandDispatcher().getDispatcher().register(
            new ActionbarCommands<CommandSourceStack>(adapter, CommandSourceStack::getBukkitEntity)
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
    public void registerBossbar(CommandAdapter adapter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerChat(CommandAdapter adapter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerTitle(CommandAdapter adapter) {
        // TODO Auto-generated method stub
        
    }
}
