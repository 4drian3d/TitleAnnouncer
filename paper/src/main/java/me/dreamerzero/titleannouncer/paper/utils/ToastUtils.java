package me.dreamerzero.titleannouncer.paper.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonObject;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.papermc.paper.adventure.AdventureComponent;
import me.dreamerzero.titleannouncer.paper.commands.ToastCommands.CustomItemPackage;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class ToastUtils {
    private ToastUtils(){}

    private static final String CRITERION_KEY = "announcer_criterion";

    public static Component fromComponent(net.kyori.adventure.text.Component component){
        return new AdventureComponent(component).deepConverted();
    }

    private static final Random rm = new Random();

    public static String randomString(){
        return Integer.toString(rm.nextInt(5000));
    }

    public static void sendToast(Player player, net.kyori.adventure.text.Component title, net.kyori.adventure.text.Component description, ItemStack item){
        ResourceLocation pluginKey = new ResourceLocation("announcer", randomString());
        Advancement advancement = Advancement.Builder.advancement()
            .display(
                item,
                fromComponent(title),
                fromComponent(description),
                null,
                FrameType.GOAL,
                true,
                false,
                false)
            .build(pluginKey);


        Map<String, Criterion> advCriteria = Map.of(CRITERION_KEY, new Criterion(new CriterionTriggerInstance() {
            @Override
            public ResourceLocation getCriterion() {
                return new ResourceLocation("minecraft", "impossible");
            }

            @Override
            public JsonObject serializeToJson(SerializationContext serializationContext) {
                return null;
            }
        }));


        AdvancementProgress advPrg = new AdvancementProgress();
        advPrg.update(advCriteria, Arrays.stream(new String[]{CRITERION_KEY}).toArray(String[][]::new));
        advPrg.getCriterion(CRITERION_KEY).grant();

        Map<ResourceLocation, AdvancementProgress> prg = Map.of(new ResourceLocation(pluginKey.getNamespace()), advPrg);

        CraftPlayer craftPlayer = ((CraftPlayer) player);

        ClientboundUpdateAdvancementsPacket packet = new ClientboundUpdateAdvancementsPacket(false, List.of(advancement), new HashSet<>(), prg);
        craftPlayer.getHandle().connection.send(packet);

        packet = new ClientboundUpdateAdvancementsPacket(true, Collections.emptyList(), Set.of(new ResourceLocation(pluginKey.getNamespace())), Collections.emptyMap());
        craftPlayer.getHandle().connection.send(packet);
    }

    public static void sendToast(Player player, CustomItemPackage pack){
        sendToast(player, pack.title(), pack.description(), pack.item());
    }
}
