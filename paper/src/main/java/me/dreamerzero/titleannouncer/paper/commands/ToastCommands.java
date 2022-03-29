package me.dreamerzero.titleannouncer.paper.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import me.dreamerzero.titleannouncer.paper.utils.ToastUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class ToastCommands {
    private ToastCommands(){}
    public static LiteralArgumentBuilder<CommandSourceStack> toast(){
        return LiteralArgumentBuilder.<CommandSourceStack>literal("announcetoast")
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("global")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {
                            CustomItemPackage pk = CustomItemPackage.of(cmd, defaultItem);
                            for(var player : Bukkit.getServer().getOnlinePlayers()){
                                ToastUtils.sendToast(player, pk);
                            }
                            return 1;
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                            .executes(cmd -> {
                                CustomItemPackage pk = CustomItemPackage.of(cmd);
                                for(var player : Bukkit.getServer().getOnlinePlayers()){
                                    ToastUtils.sendToast(player, pk);
                                }
                                return 1;
                            })
                        )
                    )
                )
            )
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("send")
                .then(RequiredArgumentBuilder.<CommandSourceStack, EntitySelector>argument("player", EntityArgument.player())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                            .executes(cmd -> {
                                CustomItemPackage pk = CustomItemPackage.of(cmd, defaultItem);
                                ServerPlayer player = EntityArgument.getPlayer(cmd, "player");
                                ToastUtils.sendToast(player.getBukkitEntity(), pk);
                                return 1;
                            })
                            .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                                .executes(cmd -> {
                                    CustomItemPackage pk = CustomItemPackage.of(cmd);
                                    ServerPlayer player = EntityArgument.getPlayer(cmd, "player");
                                    ToastUtils.sendToast(player.getBukkitEntity(), pk);
                                    return 1;
                                })
                            )
                        )
                    )
                )
                
            )
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("self")
                .requires(src -> src.getBukkitSender() instanceof Player)
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {
                            CustomItemPackage pk = CustomItemPackage.of(cmd, defaultItem);
                            CraftPlayer source = (CraftPlayer)cmd.getSource().getBukkitSender();
                            ToastUtils.sendToast(source, pk);
                            return 1;
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                            .executes(cmd -> {
                                CustomItemPackage pk = CustomItemPackage.of(cmd);
                                CraftPlayer source = (CraftPlayer)cmd.getSource().getBukkitSender();
                                ToastUtils.sendToast(source, pk);
                                return 1;
                            })
                        )
                    )
                )
            )
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("world", StringArgumentType.word())
                    .suggests((ctx, builder) -> {
                        Bukkit.getServer().getWorlds().stream().map(World::getName).forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                            .executes(cmd -> {
                                CustomItemPackage pk = CustomItemPackage.of(cmd, defaultItem);
                                World world = Bukkit.getServer().getWorld(cmd.getArgument("world", String.class));
                                if(world == null) return 0;
                                for(var player : world.getPlayers()){
                                    ToastUtils.sendToast(player, pk);
                                }
                                return 1;
                            })
                            .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                                .executes(cmd -> {

                                    return 1;
                                })
                            )
                        )
                    )
                )
                
            );
    }

    public record CustomItemPackage(ItemStack item, Component title, Component description){
        static CustomItemPackage of(CommandContext<CommandSourceStack> context){
            ItemInput input = ItemArgument.getItem(context, "item");
            ItemStack item;
            try {
                item = input.createItemStack(1, false);
            } catch(CommandSyntaxException e){
                item = defaultItem;
            }
            Component title = MiniMessage.miniMessage().deserialize(context.getArgument("title", String.class));
            Component description = MiniMessage.miniMessage().deserialize(context.getArgument("description", String.class));
    
            return new CustomItemPackage(item, title, description);
        }

        static CustomItemPackage of(CommandContext<CommandSourceStack> context, ItemStack item){
            Component title = MiniMessage.miniMessage().deserialize(context.getArgument("title", String.class));
            Component description = MiniMessage.miniMessage().deserialize(context.getArgument("description", String.class));
    
            return new CustomItemPackage(item, title, description);
        }
    }

    private static final ItemStack defaultItem = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD));
}
