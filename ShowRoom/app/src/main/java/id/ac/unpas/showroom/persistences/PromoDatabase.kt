package id.ac.unpas.showroom.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.showroom.model.Promo

@Database(entities = [Promo::class], version = 1)
abstract class PromoDatabase : RoomDatabase() {
    abstract fun promoDao(): PromoDao
}