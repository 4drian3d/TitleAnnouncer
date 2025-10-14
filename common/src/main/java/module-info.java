import org.jspecify.annotations.NullMarked;

@NullMarked
module io.github._4drian3d.titleannouncer.common {
  requires com.google.guice;
  requires io.github.miniplaceholders.api;
  requires net.kyori.adventure;
  requires net.kyori.adventure.text.minimessage;
  requires org.jetbrains.annotations;
  requires org.slf4j;
  requires org.spongepowered.configurate;
  requires org.spongepowered.configurate.hocon;
  requires org.jspecify;

  exports io.github._4drian3d.titleannouncer.common.adapter;
  exports io.github._4drian3d.titleannouncer.common;
  exports io.github._4drian3d.titleannouncer.common.annotations;
}