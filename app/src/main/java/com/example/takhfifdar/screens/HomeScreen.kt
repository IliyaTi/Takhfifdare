package com.example.takhfifdar.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.GetUserBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.viewmodels.HomeScreenViewModel
import com.example.takhfifdar.ui.customShape.QrButtonShape
import com.example.takhfifdar.util.NumberUnicodeAdapter
import com.example.takhfifdar.views.BackdropMenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    context: Activity,
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
        if (TakhfifdareApplication.loggedInUser.value != null) {
            val user = CoroutineScope(Dispatchers.IO).async {
                RetrofitInstance.api.getUser(
                    TakhfifdarDatabase.getDatabase(context).TokenDao().getToken().token,
                    GetUserBody(
                        TakhfifdarDatabase.getDatabase(context).UserDao().getUser()!!.id.toString()
                    )
                )
            }
            if (user.await().isSuccessful) {
                TakhfifdareApplication.loggedInUser.value = user.await().body()
            } else {
                TakhfifdareApplication.loggedInUser.value =
                    TakhfifdarDatabase.getDatabase(context).UserDao().getUser()
            }
        }

    }

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = if (TakhfifdareApplication.loggedInUser.value != null) BackdropScaffoldDefaults.PeekHeight + 40.dp else BackdropScaffoldDefaults.PeekHeight,
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
                    .height(if (TakhfifdareApplication.loggedInUser.value != null) BackdropScaffoldDefaults.PeekHeight + 40.dp else BackdropScaffoldDefaults.PeekHeight)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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
                            Text(
                                text = "تخفیف داره",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                        if (TakhfifdareApplication.loggedInUser.value != null)
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(text = "امتیاز شما :", color = Color.White)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = NumberUnicodeAdapter().convert(TakhfifdareApplication.loggedInUser.value!!.score.toString()), color = Color.White)
                                }
                            }
                    }
                    if (TakhfifdareApplication.loggedInUser.value != null)
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
                                Text(
                                    text = TakhfifdareApplication.loggedInUser.value?.name
                                        ?: "کاربر",
                                    color = Color.White
                                )
                            }
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                Row {
                                    Text("اعتبار شما :", color = Color.White)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = NumberUnicodeAdapter().convert(TakhfifdareApplication.loggedInUser.value?.credit!!),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                }

            }

        },
        backLayerContent = {
            if (TakhfifdareApplication.loggedInUser.value == null) {
                BackdropMenuItem(title = "ورود/ثبت نام", icon = Icons.Filled.Login) {
                    Navigator.navigateTo(NavTarget.LoginScreen)
                }
            } else {
                BackdropMenuItem(title = "اسکن کن", icon = Icons.Filled.QrCodeScanner) {
                    homeScreenNavController.navigate("tapToScan") {
                        launchSingleTop = true
                        homeScreenNavController.popBackStack()
                    }
                    scope.launch { backdropState.conceal() }
                }
                BackdropMenuItem(title = "ویرایش اطلاعات", icon = Icons.Filled.Settings) {
                    Navigator.navigateTo(NavTarget.FillUserDataScreen)
                }
                BackdropMenuItem(title = "خرید کوپن", icon = Icons.Default.ShoppingCart) {
                    Navigator.navigateTo(NavTarget.BuyCouponScreen)
                }
                BackdropMenuItem(title = "فروشگاه ها", icon = Icons.Default.Storefront) {

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://takhfifdare.com/"))
                    context.startActivity(intent)
                }
            }
            BackdropMenuItem(title = "پنل فروشندگان", icon = Icons.Default.Store) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://seller.takhfifdare.com/Otp/index")
                )
                context.startActivity(intent)
            }
            BackdropMenuItem(title = "کسب و کار", icon = Icons.Default.MonetizationOn) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://marketer.takhfifdare.com/Register/index")
                )
                context.startActivity(intent)
            }
            BackdropMenuItem(title = "درباره ما", icon = Icons.Filled.ContactSupport) {
                homeScreenNavController.navigate("aboutUs") {
                    launchSingleTop = true
                    homeScreenNavController.popBackStack()
                }
                scope.launch { backdropState.conceal() }
            }
            if (TakhfifdareApplication.loggedInUser.value != null) {
                BackdropMenuItem(title = "خروج", icon = Icons.Filled.Logout) {
                    scope.launch {
                        viewModel.signOut()
                        backdropState.conceal()
                    }
                }
            }
        },
        frontLayerContent = {
            NavHost(
                navController = homeScreenNavController,
                startDestination = if (TakhfifdareApplication.loggedInUser.value == null) "aboutUs" else "tapToScan",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("aboutUs") {
                    AboutUs(context)
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
    val lottie = createRefFor("lottie")
    val bg = createRefFor("bg")
    val container = createRefFor("container")
    val fab = createRefFor("fab")

    constrain(lottie) {
        top.linkTo(parent.top, (-20).dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(bg) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(container) {
        top.linkTo(lottie.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(fab) {
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
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

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.scrolldown))
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .layoutId("lottie")
                .size(80.dp),
            iterations = Int.MAX_VALUE
        )

        Column(
            modifier = Modifier
                .layoutId("container")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                        viewModel.proceedToScan()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = QrButtonShape(),
                    modifier = Modifier
                        .size(120.dp, 100.dp)
                        .layoutId("icon"),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 20.dp, top = 6.dp),
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
                        .width(120.dp)
                        .height(40.dp),
                    onClick = {
                        viewModel.proceedToScan()
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Text(text = "اسکن کن", fontSize = 18.sp, fontWeight = FontWeight.W900)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "یا", fontSize = 28.sp, fontWeight = FontWeight.W900)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    OutlinedTextField(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(.7f),
                        value = viewModel.storeSerial.value,
                        onValueChange = { viewModel.storeSerial.value = it },
                        shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                        label = {
                            Text(text = "کد فروشگاه")
                        }
                    )
                    Button(
                        onClick = { viewModel.onSerialButtonClick() },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        elevation = ButtonDefaults.elevation(10.dp)
                    ) {
                        if (viewModel.serialLoading.value)
                            CircularProgressIndicator(color = Color.Black, strokeWidth = 2.dp)
                        else
                            Text(
                                text = "ثبت",
                                color = Color.Black,
                                fontWeight = FontWeight.W900,
                                fontSize = 18.sp
                            )
                    }
                }
            }
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
