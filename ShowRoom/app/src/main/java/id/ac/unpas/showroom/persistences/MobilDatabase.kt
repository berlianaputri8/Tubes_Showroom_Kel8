package id.ac.unpas.showroom.persistences


import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.showroom.model.Mobil

@Database(entities = [Mobil::class], version = 1)
abstract class MobilDatabase : RoomDatabase() {
    abstract fun mobilDao(): MobilDao
}