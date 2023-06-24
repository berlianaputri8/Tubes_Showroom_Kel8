package id.ac.unpas.showroom.persistences

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.unpas.showroom.model.Motor

@Dao
interface MotorDao {
    @Query("SELECT * FROM Motor ORDER BY model DESC")
    fun loadAll(): LiveData<List<Motor>>
    @Query("SELECT * FROM Motor ORDER BY model DESC")
    suspend fun getList(): List<Motor>
    @Query("SELECT * FROM Motor WHERE id = :id")
    suspend fun find(id: String): Motor?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: Motor)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Motor>)
    @Delete
    fun delete(item: Motor)
    @Query("DELETE FROM Motor WHERE id = :id")
    suspend fun delete(id: String)
}