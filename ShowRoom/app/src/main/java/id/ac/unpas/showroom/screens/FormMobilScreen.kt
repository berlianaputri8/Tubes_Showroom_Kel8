package id.ac.unpas.showroom.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.vanpra.composematerialdialogs.MaterialDialog
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.MaterialDialog
import id.ac.unpas.showroom.model.bahanBakar
import id.ac.unpas.showroom.ui.theme.Green
import id.ac.unpas.showroom.ui.theme.Red
import kotlinx.coroutines.launch


@Composable
fun FormMobilScreen(navController : NavHostController, id: String? = null, modifier: Modifier = Modifier) {
    val bahan_bakarDialogState = rememberMaterialDialogState()
    val viewModel = hiltViewModel<MobilViewModel>()
    val merk = remember { mutableStateOf(TextFieldValue("")) }
    val model = remember { mutableStateOf(TextFieldValue("")) }
    val bahan_bakar = remember { mutableStateOf(TextFieldValue("")) }
    val dijual = remember { mutableStateOf(TextFieldValue("")) }
    val deskripsi = remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val buttonLabel = if (isLoading.value) "Mohon tunggu..." else "Simpan"
    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        OutlinedTextField(
            label = { Text(text = "Merk") },
            value = merk.value,
            onValueChange = {
                merk.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Merk") }
        )
        OutlinedTextField(
            label = { Text(text = "Model") },
            value = model.value,
            onValueChange = {
                model.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Sport") }
        )
        OutlinedTextField(
            label = { Text(text = "Bahan Bakar") },
            value = bahan_bakar.value,
            onValueChange = {
                bahan_bakar.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    bahan_bakarDialogState.show()
                },
            placeholder = { Text(text = "Bensin/Solar/Listrik") },
            enabled = false
        )


        OutlinedTextField(
            label = { Text(text = "Dijual") },
            value = dijual.value,
            onValueChange = {
                dijual.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization =
            KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "1") }
        )

        OutlinedTextField(
            label = { Text(text = "Deskripsi") },
            value = deskripsi.value,
            onValueChange = {
                deskripsi.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization =
            KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "") }
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Red,
            contentColor = Green
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
                            merk.value.text,
                            model.value.text,
                            bahanBakar.valueOf(bahan_bakar.value.text),
                            dijual.value.text,
                            deskripsi.value.text

                        )
                    }
                } else {
                    scope.launch {
                        viewModel.update(
                            id,
                            merk.value.text,
                            model.value.text,
                            bahanBakar.valueOf(bahan_bakar.value.text),
                            dijual.value.text,
                            deskripsi.value.text


                        )
                    }
                }
                navController.navigate("mobil")
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
                merk.value = TextFieldValue("")
                model.value = TextFieldValue("")
                bahan_bakar.value = TextFieldValue("")
                dijual.value = TextFieldValue("")
                deskripsi.value = TextFieldValue("")
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
            viewModel.loadItem(id) { mobil ->
                mobil?.let {
                    merk.value = TextFieldValue(mobil.merk)
                    model.value = TextFieldValue(mobil.model)
                    bahan_bakar.value = TextFieldValue(mobil.bahan_bakar.toString())
                    dijual.value = TextFieldValue(mobil.dijual)
                    deskripsi.value = TextFieldValue(mobil.deskripsi)
                }
            }
        }
    }

    MaterialDialog(dialogState = bahan_bakarDialogState, buttons = {
        positiveButton("OK")
        negativeButton("Batal")
    }) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = bahan_bakar.value.text == bahanBakar.Bensin.name,
                    onClick = { bahan_bakar.value = TextFieldValue(bahanBakar.Bensin.name) }
                )
                Text(text = "Bensin")
            }
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = bahan_bakar.value.text == bahanBakar.Solar.name,
                    onClick = { bahan_bakar.value = TextFieldValue(bahanBakar.Solar.name) }
                )
                Text(text = "Solar")
            }
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = bahan_bakar.value.text == bahanBakar.Listrik.name,
                    onClick = { bahan_bakar.value = TextFieldValue(bahanBakar.Listrik.name) }
                )
                Text(text = "Listrik")
            }
        }
    }
}