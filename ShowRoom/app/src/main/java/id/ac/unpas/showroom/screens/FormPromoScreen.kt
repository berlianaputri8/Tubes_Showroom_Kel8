package id.ac.unpas.showroom.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import id.ac.unpas.showroom.ui.theme.Red
import id.ac.unpas.showroom.ui.theme.Green
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun FormPromoScreen(navController : NavHostController, id: String? = null, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<PromoViewModel>()
    val model = remember { mutableStateOf(TextFieldValue("")) }
    val tanggal_awal = remember { mutableStateOf(TextFieldValue("")) }
    val tanggal_akhir = remember { mutableStateOf(TextFieldValue("")) }
    val persentase = remember { mutableStateOf(TextFieldValue("")) }
    val tanggalDialogState = rememberMaterialDialogState()
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val buttonLabel = if (isLoading.value) "Mohon tunggu..." else "Simpan"
    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        OutlinedTextField(
            label = { Text(text = "Model") },
            value = model.value,
            onValueChange = {
                model.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Model") }
        )

        OutlinedTextField(
            label = { Text(text = "Tanggal Awal") },
            value = tanggal_awal.value,
            onValueChange = {
                tanggal_awal.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    tanggalDialogState.show()
                },
            placeholder = { Text(text = "2") },
            enabled = false
        )

        OutlinedTextField(
            label = { Text(text = "Tanggal Akhir") },
            value = tanggal_awal.value,
            onValueChange = {
                tanggal_akhir.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    tanggalDialogState.show()
                },
            placeholder = { Text(text = "DD/MM/YY") },
            enabled = false
        )

        OutlinedTextField(
            label = { Text(text = "Persentase") },
            value = persentase.value,
            onValueChange = {
                persentase.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "%") }
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Green,
            contentColor = Red
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Green,
            contentColor = Red
        )
        Row (modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            Button(modifier = Modifier.weight(5f), onClick = {
                if (id == null) {
                    scope.launch {
                        viewModel.insert(
                            model.value.text,
                            tanggal_awal.value.text,
                            tanggal_akhir.value.text,
                            persentase.value.text,

                        )
                    }
                } else {
                    scope.launch {
                        viewModel.update(
                            id,
                            model.value.text,
                            tanggal_awal.value.text,
                            tanggal_akhir.value.text,
                            persentase.value.text,
                        )
                    }
                }
                navController.navigate("promo")
            }, colors = loginButtonColors) {
                Text(
                    text = buttonLabel,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
            Button(modifier = Modifier.weight(5f), onClick = {
                model.value = TextFieldValue("")
                tanggal_awal.value = TextFieldValue("")
                tanggal_akhir.value = TextFieldValue("")
                persentase.value = TextFieldValue("")
            }, colors = resetButtonColors) {
                Text(
                    text = "Reset",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
    viewModel.isLoading.observe(LocalLifecycleOwner.current) {
        isLoading.value = it
    }

    if (id != null) {
        LaunchedEffect(scope) {
            viewModel.loadItem(id) { promo ->
                promo?.let {
                    model.value = TextFieldValue(promo.model)
                    tanggal_awal.value = TextFieldValue(promo.tanggal_awal)
                    tanggal_akhir.value = TextFieldValue(promo.tanggal_akhir.toString())
                    persentase.value = TextFieldValue(promo.persentase.toString())
                }
            }
        }
    }
    MaterialDialog(dialogState = tanggalDialogState, buttons = {
        positiveButton("OK")
        negativeButton("Batal")
    }) {
        datepicker { date ->
            tanggal_awal.value = TextFieldValue(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
            tanggal_akhir.value = TextFieldValue(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
        }
    }
}