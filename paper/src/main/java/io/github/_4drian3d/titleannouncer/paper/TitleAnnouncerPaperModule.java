package io.github._4drian3d.titleannouncer.paper;

import com.google.inject.AbstractModule;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncerMainModule;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;

public final class TitleAnnouncerPaperModule extends AbstractModule {
    @Override
    protected void configure() {
        this.install(new TitleAnnouncerMainModule());

        this.bind(PlatformAdapter.class).to(TitleAnnouncerPaperAdapter.class);
    }
}
