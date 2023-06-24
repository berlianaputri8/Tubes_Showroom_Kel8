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
import id.ac.unpas.showroom.ui.theme.Red
import id.ac.unpas.showroom.ui.theme.Green
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun FormMotorScreen(navController : NavHostController, id: String? = null, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<MotorViewModel>()
    val model = remember { mutableStateOf(TextFieldValue("")) }
    val warna = remember { mutableStateOf(TextFieldValue("")) }
    val kapasitas = remember { mutableStateOf(TextFieldValue("")) }
    val tanggal_rilis = remember { mutableStateOf(TextFieldValue("")) }
    val harga = remember { mutableStateOf(TextFieldValue("")) }
    val tanggalDialogState = rememberMaterialDialogState()
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val buttonLabel = if (isLoading.value) "Mohon tunggu..." else "Simpan"
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            label = { Text(text = "Model") },
            value = model.value,
            onValueChange = {
                model.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Model") },
        )
        OutlinedTextField(
            label = { Text(text = "Warna") },
            value = warna.value,
            onValueChange = {
                warna.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization =
                KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text
            ),
            placeholder = { Text(text = "XXXXX") }
        )
        OutlinedTextField(
            label = { Text(text = "Kapasitas") },
            value = kapasitas.value,
            onValueChange = {
                kapasitas.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "2") },
        )

        OutlinedTextField(
            label = { Text(text = "Tanggal Rilis") },
            value = tanggal_rilis.value,
            onValueChange = {
                tanggal_rilis.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
            .clickable {
            tanggalDialogState.show()
        },
            placeholder = { Text(text = "yyyy-mm-dd") } ,
            enabled = false
        )

        OutlinedTextField(
            label = { Text(text = "Harga") },
            value = harga.value,
            onValueChange = {
                harga.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "100000000") }
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Green,
            contentColor = Red
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Green,
            contentColor = Red
        )
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(modifier = Modifier.weight(5f), onClick = {
                if (id == null) {
                    scope.launch {
                        viewModel.insert(
                            model.value.text,
                            warna.value.text,
                            kapasitas.value.text,
                            tanggal_rilis.value.text,
                            harga.value.text
                        )
                    }
                } else {
                    scope.launch {
                        viewModel.update(
                            id,
                            model.value.text,
                            warna.value.text,
                            kapasitas.value.text,
                            tanggal_rilis.value.text,
                            harga.value.text
                        )
                    }
                }
                navController.navigate("motor")
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
                warna.value = TextFieldValue("")
                kapasitas.value = TextFieldValue("")
                tanggal_rilis.value = TextFieldValue("")
                harga.value = TextFieldValue("")
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
            viewModel.loadItem(id) { motor ->
                motor?.let {
                    model.value = TextFieldValue(motor.model)
                    warna.value = TextFieldValue(motor.warna)
                    kapasitas.value = TextFieldValue(motor.kapasitas)
                    tanggal_rilis.value = TextFieldValue(motor.tanggal_rilis)
                    harga.value = TextFieldValue(motor.harga)
                }
            }
        }
    }

    MaterialDialog(dialogState = tanggalDialogState, buttons = {
        positiveButton("OK")
        negativeButton("Batal")
    }) {
        datepicker { date ->
            tanggal_rilis.value = TextFieldValue(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
        }
    }
}
