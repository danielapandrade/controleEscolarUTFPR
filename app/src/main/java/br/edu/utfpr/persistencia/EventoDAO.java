package br.edu.utfpr.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.model.Evento;

@Dao
public interface EventoDAO {

    @Insert
    long insert(Evento evento);

    @Delete
    void delete(Evento evento);

    @Update
    void update(Evento evento);

    @Query("SELECT * FROM evento WHERE id = :id")
    Evento queryForId(long id);

    @Query("SELECT * FROM evento ORDER BY evento ASC")
    List<Evento> queryAll();

}
