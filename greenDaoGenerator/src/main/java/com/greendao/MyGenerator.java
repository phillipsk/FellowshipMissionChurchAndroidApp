package com.greendao;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(3, "io.fmc.db"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addAudioMessageEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addAudioMessageEntities(final Schema schema) {
        Entity user = schema.addEntity("AudioMessage");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("name").notNull();
        user.addStringProperty("path");
        user.addBooleanProperty("is_playing");
        return user;
    }
}
