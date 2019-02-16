package io.fmc.network;

public class BibleBook {
    String id;
    String bookId;
    String number;
    String reference;

    public BibleBook(String id, String bookId, String number, String reference) {
        this.id = id;
        this.bookId = bookId;
        this.number = number;
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getNumber() {
        return number;
    }

    public String getReference() {
        return reference;
    }
}
