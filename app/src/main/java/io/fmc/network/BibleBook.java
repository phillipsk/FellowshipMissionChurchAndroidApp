package io.fmc.network;

public class BibleBook {

    /*      "id": "EXO",
      "bibleId": "de4e12af7f28f599-01",
      "abbreviation": "Exo",
      "name": "Exodus",
      "nameLong": "The Second Book of Moses, called Exodus"
      */
    String id;
    String abbreviation;
    String name;
    String nameLong;

    public BibleBook(String id, String abbreviation, String name, String nameLong) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.name = name;
        this.nameLong = nameLong;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getNameLong() {
        return nameLong;
    }
}
