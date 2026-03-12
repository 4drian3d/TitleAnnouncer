package io.github._4drian3d.titleannouncer.common.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Singleton
public final class BossBarManager {
  private static final ScheduledExecutorService BOSSBAR_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
  private static final Map<UUID, Map<BossBarTask, ScheduledFuture<?>>> BOSSBAR_TASKS = new ConcurrentHashMap<>();

  @Inject
  private Formatter formatter;

  public void sendBossBar(
      final Audience audience,
      final @Range(from = 1, to = Integer.MAX_VALUE) int seconds,
      final String content,
      final BossBar.Color color,
      final BossBar.Overlay type
  ) {
    if (audience instanceof ForwardingAudience forwardingAudience) {
      //noinspection OverrideOnly
      for (final Audience singleAudience : forwardingAudience.audiences()) {
        this.sendBossBar(singleAudience, seconds, content, color, type);
      }
      return;
    }
    final BossBar bar = BossBar.bossBar(formatter.audienceFormat(content, audience), 1, color, type);
    audience.showBossBar(bar);

    audience.get(Identity.UUID).ifPresent(id ->
      BOSSBAR_TASKS.compute(id, (a, b) -> {
        if (b == null) {
          b = new HashMap<>();
        }
        final float finalTime = 1f / seconds;
        var task = new BossBarTask(formatter, content, bar, audience, finalTime);
        b.put(task, BOSSBAR_EXECUTOR.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS));
        return b;
      })
    );
  }

  public void cancelTasksByUUID(UUID uuid) {
    final Iterator<Map.Entry<BossBarTask, ScheduledFuture<?>>> iterator = BOSSBAR_TASKS.get(uuid).entrySet().iterator();
    while (iterator.hasNext()) {
      final Map.Entry<BossBarTask, ScheduledFuture<?>> entry = iterator.next();
      final BossBarTask bossBarTask = entry.getKey();
      bossBarTask.viewer.hideBossBar(bossBarTask.bossBar);
      iterator.remove();
    }
  }

  private record BossBarTask(
      Formatter formatter,
      String content,
      BossBar bossBar,
      Audience viewer,
      float percentageToRemove
  ) implements Runnable {
    @Override
    public void run() {
      calculateTitle();
      calculateProgress();
    }

    void calculateTitle() {
      bossBar.name(formatter.audienceFormat(content, viewer));
    }

    void calculateProgress() {
      final float progress = bossBar.progress();
      final float toRest = progress - this.percentageToRemove;
      if (toRest < 0f) {
        stop();
        return;
      }
      bossBar.progress(toRest);
    }

    void stop() {
      viewer.hideBossBar(bossBar);
      BOSSBAR_TASKS.remove(viewer.get(Identity.UUID).orElseThrow()).get(this).cancel(false);
    }
  }
}
