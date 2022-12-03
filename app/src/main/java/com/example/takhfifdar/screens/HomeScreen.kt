package com.example.takhfifdar.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.takhfifdar.MainActivityViewModel
import com.example.takhfifdar.R
import com.example.takhfifdar.data.database.User
import com.example.takhfifdar.views.BackdropMenuItem
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavController,
    context: Activity,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    userId: String
) {
    val scope = rememberCoroutineScope()
    var backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val menuExpanded = remember { mutableStateOf(false) }

    BackHandler {
        context.finishAndRemoveTask()
    }

    BackdropScaffold(
        scaffoldState = backdropState,
        appBar = {
            TopAppBar(elevation = 0.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            if (backdropState.isConcealed) backdropState.reveal()
                            else backdropState.conceal()
                        }
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.menu_logo),
                            contentDescription = "",
                            modifier = Modifier.height(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Takhfif Daar!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

        },
        backLayerContent = {
            BackdropMenuItem(title = "Qr Scanner") {
                val camPermission =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (camPermission != PackageManager.PERMISSION_GRANTED) launcher.launch(Manifest.permission.CAMERA)
                if (camPermission == PackageManager.PERMISSION_GRANTED) navController.navigate("QrScanner")
            }
        },
        frontLayerContent = {

        },
        frontLayerShape = MaterialTheme.shapes.large
    ) {

    }

}
