package me.dreamerzero.titleannouncer.common.configuration;

import java.nio.file.Path;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import net.kyori.adventure.serializer.configurate4.ConfigurateComponentSerializer;

public final class Loader {
    private Loader(){}
    public static final Configuration loadConfig(Path pluginPath) {
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .defaultOptions(defaultOptions -> defaultOptions
                .shouldCopyDefaults(true)
                .header("TitleAnnouncer | by 4drian3d")
                .serializers(
                    serializers -> serializers
                        .registerAll(ConfigurateComponentSerializer.configurate().serializers())
                )
            )
            .path(pluginPath.resolve("config.conf"))
            .build();

        Configuration config;

        try {
            CommentedConfigurationNode node = loader.load();
            config = node.get(Configuration.class);
            node.set(Configuration.class, config);
            loader.save(node);
            return config;
        } catch (ConfigurateException e) {
            //TODO: Replace with slf4j logger
            e.printStackTrace();
            return null;
        }
    }
}