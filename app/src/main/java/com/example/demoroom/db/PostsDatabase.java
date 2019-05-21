package com.example.demoroom.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {

    private static PostsDatabase INSTANCE;

    private final static List<Post> POSTS = Arrays.asList(
            new Post("Titulo de Prueba Uno", "Es un hecho establecido hace demasiado tiempo que un lector se distraerá con el contenido del texto de un sitio mientras que mira su diseño."),
            new Post("Titulo de Prueba Dos", "Es un hecho establecido hace demasiado tiempo que un lector se distraerá con el contenido del texto de un sitio mientras que mira su diseño."),
            new Post("Titulo de Prueba Tres","Es un hecho establecido hace demasiado tiempo que un lector se distraerá con el contenido del texto de un sitio mientras que mira su diseño.")
    );


    public abstract PostDao postDao();

    private static final Object sLock = new Object();

    public static PostsDatabase getInstance(final Context context){
        synchronized (sLock) {
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PostsDatabase.class, "Posts.db")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(
                                        () -> getInstance(context).postDao().saveAll(POSTS));
                            }
                        })
                        .build();


            }
            return INSTANCE;
        }

    }
}
