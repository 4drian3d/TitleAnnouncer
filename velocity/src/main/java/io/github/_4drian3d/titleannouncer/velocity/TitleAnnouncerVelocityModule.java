package io.github._4drian3d.titleannouncer.velocity;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncerMainModule;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.annotations.DataFolder;

import java.nio.file.Path;

public final class TitleAnnouncerVelocityModule extends AbstractModule {
  private final Path path;

  TitleAnnouncerVelocityModule(Path path) {
    this.path = path;
  }

  @Override
  protected void configure() {
    this.bind(Path.class).annotatedWith(DataFolder.class).toInstance(path);
    this.install(new TitleAnnouncerMainModule());
    this.bind(PlatformAdapter.class).to(TitleAnnouncerVelocityAdapter.class);
    this.bind(Key.get(new TypeLiteral<PlatformAdapter<?>>() {
    })).to(TitleAnnouncerVelocityAdapter.class);
  }
}
