package rezende.israel.isnotev2.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rezende.israel.isnotev2.model.Nota

@Dao
interface NotaDao {

    @Insert(onConflict = REPLACE)
    suspend fun salva(note: Nota)

    @Query("SELECT * FROM Nota")
    fun buscaTodas(): Flow<List<Nota>>

    @Query("SELECT * FROM Nota WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Nota>

    @Query("DELETE FROM Nota WHERE id = :id")
    suspend fun remove(id: Long)

}