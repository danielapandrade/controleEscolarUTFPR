package br.edu.utfpr.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.model.Evento;

@Database(entities = {Evento.class}, version = 1, exportSchema = false)
public abstract class EventoDatabase extends RoomDatabase {

    public abstract EventoDAO eventoDAO();

    private static EventoDatabase instance;

    public static EventoDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (DependenteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            EventoDatabase.class,
                            "eventos.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
