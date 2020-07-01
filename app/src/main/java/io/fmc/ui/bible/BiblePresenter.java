package io.fmc.ui.bible;

import android.util.Log;

import javax.inject.Inject;

import io.fmc.network.BibleApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BiblePresenter {

    private BibleApi bibleApi;
    private BibleScreen bibleScreen;
    private CompositeDisposable compositeDisposable;

    @Inject
    public BiblePresenter(BibleApi bibleApi) {
        this.bibleApi = bibleApi;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void bind(BibleScreen bibleScreen) {
        this.bibleScreen = bibleScreen;
    }

    public void fetchBibleBooks(String bibleId) {
        compositeDisposable.add(bibleApi.getBibleBooks(bibleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booksResponse -> bibleScreen.onNewBibleBooks(booksResponse.data),
                        throwable -> Log.e("TEST", "Error in fetch Bible books", throwable)));
//        compositeDisposable.add(bibleApi.getBibleBooks(bibleId).subscribeOn(Scheduler.))
    }

    public void unbind() {
        compositeDisposable.clear();
    }
}
