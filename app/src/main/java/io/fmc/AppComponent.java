package io.fmc;

import javax.inject.Singleton;

import dagger.Component;
import io.fmc.network.DataModule;
import io.fmc.ui.bible.BibleFragment;

@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

//    void inject(FellowshipApplication fellowshipApplication);

    void inject(BibleFragment bibleFragment);

}
