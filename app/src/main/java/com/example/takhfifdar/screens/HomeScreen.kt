package com.example.takhfifdar.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.viewmodels.HomeScreenViewModel
import com.example.takhfifdar.ui.customShape.QrButtonShape
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
    val homeScreenNavController = rememberNavController()

    BackHandler {
        context.finishAndRemoveTask()
    }

    LaunchedEffect(key1 = true) {
//        LoggedInUser.user.value = TakhfifdarDatabase.getDatabase(context).UserDao().getUser()
        TakhfifdareApplication.loggedInUser.value =
            TakhfifdarDatabase.getDatabase(context).UserDao().getUser()
    }

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = BackdropScaffoldDefaults.PeekHeight + 40.dp,
        appBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp, modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xff2e3192),
                                Color(0xff8160ed)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .height(BackdropScaffoldDefaults.PeekHeight + 40.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (backdropState.isConcealed) backdropState.reveal()
                                    else backdropState.conceal()
                                }
                            },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(30.dp)
                                    .height(BackdropScaffoldDefaults.PeekHeight)
                                    .padding(2.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "تخفیف داره", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = TakhfifdareApplication.loggedInUser.value?.name ?: "کاربر")
                        }
                        Row {
                            Icon(imageVector = Icons.Default.Payments, contentDescription = "")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = TakhfifdareApplication.loggedInUser.value?.credit ?: "اعتبار")
                        }
                    }
                }

            }

        },
        backLayerContent = {
            if (TakhfifdareApplication.loggedInUser.value == null) {
                BackdropMenuItem(title = "ورود", icon = Icons.Filled.Login) {
                    Navigator.navigateTo(NavTarget.LoginScreen)
                }
            } else {
                BackdropMenuItem(title = "خروچ", icon = Icons.Filled.Logout) {
                    scope.launch {
                        viewModel.signOut()
                        backdropState.conceal()
                    }
                }
                BackdropMenuItem(title = "تنظیمات کاربر", icon = Icons.Filled.Settings) {
                    Navigator.navigateTo(NavTarget.FillUserDataScreen)
                }
            }
            BackdropMenuItem(title = "اسکن کن", icon = Icons.Filled.QrCodeScanner) {
                homeScreenNavController.navigate("tapToScan") {
                    launchSingleTop = true
                    homeScreenNavController.popBackStack()
                }
                scope.launch { backdropState.conceal() }
            }

            BackdropMenuItem(title = "درباره", icon = Icons.Filled.ContactSupport) {
                homeScreenNavController.navigate("aboutUs") {
                    launchSingleTop = true
                    homeScreenNavController.popBackStack()
                }
                scope.launch { backdropState.conceal() }
            }
        },
        frontLayerContent = {
            NavHost(
                navController = homeScreenNavController,
                startDestination = if (TakhfifdareApplication.loggedInUser.value == null) "aboutUs" else "tapToScan",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("aboutUs") {
                    AboutUs()
                }
                composable("tapToScan") {
                    TapToScan(viewModel = viewModel)
                }
            }

        },
        frontLayerShape = MaterialTheme.shapes.large
    ) {

    }

}

val mainSet = ConstraintSet {
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



@Composable
fun TapToScan(viewModel: HomeScreenViewModel) {
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    ConstraintLayout(mainSet, modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                viewModel.proceedToScan()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = QrButtonShape(),
            modifier = Modifier
                .size(160.dp, 140.dp)
                .layoutId("icon"),
            elevation = ButtonDefaults.elevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qrcode),
                    contentDescription = ""
                )
            }
        }
        Button(
            modifier = Modifier
                .layoutId("button")
                .width(160.dp)
                .height(60.dp),
            onClick = {
                viewModel.proceedToScan()
            },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(10.dp)
        ) {
            Text(text = "اسکن کن", fontSize = 26.sp, fontWeight = FontWeight.W900)
        }
        Image(
            painter = painterResource(id = R.drawable.home_bottom),
            contentDescription = "",
            modifier = Modifier
                .layoutId("bg")
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}