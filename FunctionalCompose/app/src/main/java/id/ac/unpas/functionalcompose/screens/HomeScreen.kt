package id.ac.unpas.functionalcompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column {
//        Text(text = "Home")
        Text(text = "Aplikasi showroom adalah sebuah platform digital yang dirancang " +
                "untuk memfasilitasi pengalaman belanja virtual bagi pengguna. " +
                "Aplikasi ini bertujuan untuk memberikan pengguna akses ke berbagai " +
                "produk atau jasa yang ditawarkan oleh berbagai merek atau perusahaan " +
                "tanpa harus pergi ke toko fisik", fontSize = 20.sp)
    }
}