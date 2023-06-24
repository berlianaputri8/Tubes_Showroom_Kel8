package id.ac.unpas.showroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Promo (
    @PrimaryKey val id : String,
    val model : String,
    val tanggal_awal : String,
    val tanggal_akhir : String,
    val persentase : String
)