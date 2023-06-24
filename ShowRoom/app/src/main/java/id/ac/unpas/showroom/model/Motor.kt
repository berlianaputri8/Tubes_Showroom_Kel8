package id.ac.unpas.showroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Motor(
    @PrimaryKey val id: String,
    val model: String,
    val warna: String,
    val kapasitas: String,
    val tanggal_rilis: String,
    val harga: String
)
