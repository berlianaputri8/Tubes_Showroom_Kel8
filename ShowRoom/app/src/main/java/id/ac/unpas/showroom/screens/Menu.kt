package id.ac.unpas.showroom.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import id.ac.unpas.showroom.R

enum class Menu (
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
){
    HOME(R.string.home, Icons.Default.Home, "home"),
    MOBIL(R.string.mobil, Icons.Default.ShoppingCart, "mobil"),
    MOTOR(R.string.motor, Icons.Default.ShoppingCart, "motor"),
    PROMO(R.string.promo, Icons.Default.Favorite, "promo");

    companion object {
        fun getTabFromResource(@StringRes resource: Int) : Menu
        {
            return when (resource) {
                R.string.home -> HOME
                R.string.mobil -> MOBIL
                R.string.motor -> MOTOR
                else -> PROMO
            }
        }
    }
}
