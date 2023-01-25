package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.takhfifdar.R
import androidx.compose.ui.unit.sp
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.GetUserBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun PaymentResultScreen(id: Int) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        CoroutineScope(Dispatchers.IO).launch {
            val req = RetrofitInstance.api.getUser(
                token = TakhfifdarDatabase.getDatabase(context).TokenDao().getToken().token,
                body = GetUserBody(TakhfifdarDatabase.getDatabase(context).UserDao().getUser()!!.id.toString())
            )
            if (req.isSuccessful) {
                TakhfifdareApplication.loggedInUser.value = req.body()
                TakhfifdarDatabase.getDatabase(context).UserDao().updateUser(req.body()!!)
            }
        }
    }

    if (id == 1)
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                colorFilter = ColorFilter.tint(Color(0xFF092900)),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint = Color.Green, modifier = Modifier.size(50.dp))
                    Text(
                        text = "عملیات با موفقیت انجام شدْ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                    Text(
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center,
                        color = Color(0xDAFFFFFF),
                        text = "تراکنش شما با موفقیت انجام شد و به اعتبار شما افزوده شد."
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(onClick = {
                        Navigator.navigateTo(NavTarget.HomeScreen)
                    }) {
                        Text(text = "بازگشت به برنامه")
                    }
                }
            }
        }
    else
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                colorFilter = ColorFilter.tint(Color(0xFF350000)),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = "", tint = Color.Red, modifier = Modifier.size(50.dp))
                    Text(
                        text = "متاسفانه عملیات با شکست مواجه شد",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Text(
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center,
                        color = Color(0xDAFFFFFF),
                        text = "این تمام چیزیه که ما میدونیم. لطفا در صورت عدم بازگشت وجه به حساب تا حداکثر ۷۲ ساعت دیگر، موضوع را با پشتیبانی بانک رفاه مطرح کنید."
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(onClick = {
                        Navigator.navigateTo(NavTarget.HomeScreen)
                    }) {
                        Text(text = "بازگشت به برنامه")
                    }
                }
            }
        }
}  