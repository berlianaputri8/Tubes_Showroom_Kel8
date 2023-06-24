package id.ac.unpas.showroom.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class bahanBakar {
    Bensin,
    Solar,
    Listrik
}

@Entity
@Immutable
data class Mobil(
    @PrimaryKey val id: String,
    val merk: String,
    val model: String,
    val bahan_bakar: bahanBakar,
    val dijual: String,
    val deskripsi : String
)
