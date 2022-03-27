package me.dreamerzero.titleannouncer.paper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;

public class ToastCommands {
    //TODO: How can i implement this? now i can access nms, but... how? xD
    public LiteralArgumentBuilder<CommandSourceStack> bossbar(){
        return LiteralArgumentBuilder.<CommandSourceStack>literal("announcetoast")
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("global").build())
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("send").build())
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("self").build())
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("world").build());
    }
}
