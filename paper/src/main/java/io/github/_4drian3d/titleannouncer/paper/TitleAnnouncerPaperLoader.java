package io.github._4drian3d.titleannouncer.paper;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public final class TitleAnnouncerPaperLoader implements PluginLoader {
  @Override
  public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {
    final MavenLibraryResolver resolver = new MavenLibraryResolver();

    final RemoteRepository mavenCentral = new RemoteRepository
        .Builder("central-mirror", "default", "https://repo.papermc.io/repository/maven-public/")
        .build();
    final Dependency configurateHocon = new Dependency(
        new DefaultArtifact("org.spongepowered:configurate-hocon:4.2.0"),
        null
    );
    final Dependency configurateAdventure = new Dependency(
        new DefaultArtifact("net.kyori:adventure-serializer-configurate4:4.25.0"),
        null
    );
    final Dependency guice = new Dependency(
        new DefaultArtifact("com.google.inject:guice:7.0.0"),
        null
    );

    resolver.addRepository(mavenCentral);
    resolver.addDependency(configurateHocon);
    resolver.addDependency(configurateAdventure);
    resolver.addDependency(guice);

    classpathBuilder.addLibrary(resolver);
  }
}
