import org.jspecify.annotations.NullMarked;

@NullMarked
module io.github._4drian3d.titleannouncer.velocity {
  requires com.google.guice;
  requires com.velocitypowered.api;
  requires io.github._4drian3d.titleannouncer.common;
  requires net.kyori.adventure;

  requires static transitive org.jetbrains.annotations;
  requires static transitive org.jspecify;
  requires org.checkerframework.checker.qual;
}