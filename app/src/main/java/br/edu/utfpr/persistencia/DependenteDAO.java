package br.edu.utfpr.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.model.Dependente;

@Dao
public interface DependenteDAO {

    @Insert
    long insert(Dependente dependente);

    @Delete
    void delete(Dependente dependente);

    @Update
    void update(Dependente dependente);

    @Query("SELECT * FROM dependente WHERE id = :id")
    Dependente queryForId(long id);

    @Query("SELECT * FROM dependente ORDER BY nome ASC")
    List<Dependente> queryAll();
}



