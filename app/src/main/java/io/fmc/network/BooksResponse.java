package io.fmc.network;

import java.util.List;

public class BooksResponse {
    public List<BibleBook> data;
//    public List data;

    public BooksResponse(List<BibleBook> bibleBooks) {
        this.data = bibleBooks;
    }

    @Override
    public String toString() {
        return "BooksResponse{" +
                "bibleBooks=" + data +
                '}';
    }
}
