import org.jspecify.annotations.NullMarked;

@NullMarked
module io.github._4drian3d.titleannouncer.paper {
  requires com.google.guice;
  requires io.github._4drian3d.titleannouncer.common;
  requires net.kyori.adventure;
  requires org.bukkit;
  requires static org.jetbrains.annotations;
  requires static transitive org.jspecify;
}