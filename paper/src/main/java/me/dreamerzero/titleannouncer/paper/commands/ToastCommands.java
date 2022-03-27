package me.dreamerzero.titleannouncer.paper.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;

import me.dreamerzero.titleannouncer.paper.utils.ToastUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.world.item.ItemStack;

public final class ToastCommands {
    private ToastCommands(){}
    public static LiteralArgumentBuilder<CommandSourceStack> toast(){
        return LiteralArgumentBuilder.<CommandSourceStack>literal("announcetoast")
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("global")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {
                            ItemStack item = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD));
                            Component title = MiniMessage.miniMessage().deserialize(cmd.getArgument("title", String.class));
                            Component description = MiniMessage.miniMessage().deserialize(cmd.getArgument("description", String.class));
                            for(var player : Bukkit.getServer().getOnlinePlayers()){
                                ToastUtils.sendToast(player, title, description, item);
                            }
                            return 1;
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                            .executes(cmd -> {
                                ItemInput input = ItemArgument.getItem(cmd, "item");
                                ItemStack item = input.createItemStack(1, false);
                                Component title = MiniMessage.miniMessage().deserialize(cmd.getArgument("title", String.class));
                                Component description = MiniMessage.miniMessage().deserialize(cmd.getArgument("description", String.class));
                                for(var player : Bukkit.getServer().getOnlinePlayers()){
                                    ToastUtils.sendToast(player, title, description, item);
                                }
                                return 1;
                            })
                        )
                    )
                )
            )
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("send")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {

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
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("self")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {

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
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("world")
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("title", StringArgumentType.string())
                    .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("description", StringArgumentType.string())
                        .executes(cmd -> {

                            return 1;
                        })
                        .then(RequiredArgumentBuilder.<CommandSourceStack, ItemInput>argument("item", ItemArgument.item())
                            .executes(cmd -> {

                                return 1;
                            })
                        )
                    )
                )
            );
    }
}
