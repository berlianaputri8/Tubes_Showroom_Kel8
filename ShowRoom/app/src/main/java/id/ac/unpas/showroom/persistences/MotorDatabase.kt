package id.ac.unpas.showroom.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.showroom.model.Motor

@Database(entities = [Motor::class], version = 1)
abstract class MotorDatabase : RoomDatabase() {
    abstract fun motorDao(): MotorDao
}