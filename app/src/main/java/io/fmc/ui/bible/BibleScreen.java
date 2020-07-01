package io.fmc.ui.bible;

import java.util.List;

import io.fmc.network.BibleBook;

public interface BibleScreen {

    void onNewBibleBooks(List<BibleBook> books);

}
