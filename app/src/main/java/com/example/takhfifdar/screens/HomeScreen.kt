package com.example.takhfifdar.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.takhfifdar.R
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.viewmodels.HomeScreenViewModel
import com.example.takhfifdar.views.BackdropMenuItem
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    context: Activity,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    viewModel: HomeScreenViewModel
) {
    val scope = rememberCoroutineScope()
    var backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)

    BackHandler {
        context.finishAndRemoveTask()
    }

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = BackdropScaffoldDefaults.PeekHeight + 40.dp,
        appBar = {
            TopAppBar(elevation = 0.dp, modifier = Modifier
                .fillMaxWidth()
                .height(BackdropScaffoldDefaults.PeekHeight + 40.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
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
                        Text(text = "تخفیف داره", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "")
                            Text(text = viewModel.user.value.name)
                        }
                        Row {
                            Icon(imageVector = Icons.Default.Payments, contentDescription = "")
                            Text(text = viewModel.user.value.credit)
                        }
                    }
                }

            }

        },
        backLayerContent = {
            BackdropMenuItem(title = "خروج") {
                scope.launch {
                    TakhfifdarDatabase.getDatabase(context).UserDao().deleteUsers()
                    Navigator.navigateTo(NavTarget.LoginScreen)
                }
            }
        },
        frontLayerContent = {
            ConstraintLayout(mainSet, modifier = Modifier.fillMaxSize()) {
                Card(modifier = Modifier
                    .clickable {
                        val camPermission =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (camPermission != PackageManager.PERMISSION_GRANTED) launcher.launch(Manifest.permission.CAMERA)
                        else Navigator.navigateTo(navTarget = NavTarget.QrScanner)
                    }
                    .layoutId("icon")
                    .size(150.dp, 130.dp), elevation = 10.dp, shape = RoundedCornerShape(50.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = "", modifier = Modifier.padding(20.dp))
                }
                Card(modifier = Modifier
                    .clickable {
                        val camPermission =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (camPermission != PackageManager.PERMISSION_GRANTED) launcher.launch(Manifest.permission.CAMERA)
                        else Navigator.navigateTo(navTarget = NavTarget.QrScanner)
                    }
                    .layoutId("button")
                    .size(150.dp, 40.dp),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(CornerSize(20.dp))
                ) {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "اسکن کن", fontSize = 24.sp, fontWeight = FontWeight.W900)
                    }
                }
                Image(painter = painterResource(id = R.drawable.home), contentDescription = "", modifier = Modifier
                    .layoutId("bg")
                    .fillMaxWidth(), contentScale = ContentScale.FillWidth)
            }
        },
        frontLayerShape = MaterialTheme.shapes.large
    ) {

    }

}

val mainSet = ConstraintSet() {
    val bg = createRefFor("bg")
    val button = createRefFor("button")
    val icon = createRefFor("icon")

    constrain(bg) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(icon) {
        top.linkTo(parent.top, margin = 130.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(button) {
        top.linkTo(icon.bottom, margin = 10.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}
