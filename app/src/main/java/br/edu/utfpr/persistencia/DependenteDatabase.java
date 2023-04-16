package br.edu.utfpr.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.model.Dependente;

@Database(entities = {Dependente.class}, version = 1, exportSchema = false)
public abstract class DependenteDatabase extends RoomDatabase {

    public abstract DependenteDAO dependenteDao();

    private static DependenteDatabase instance;

    public static DependenteDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (DependenteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            DependenteDatabase.class,
                            "dependentes.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}