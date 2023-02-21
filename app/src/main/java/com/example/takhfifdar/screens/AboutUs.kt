package com.example.takhfifdar.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AboutUs(context: Context) {

    val set = ConstraintSet {
        val content = createRefFor("content")
        val indicator = createRefFor("indicator")
        val anim = createRefFor("anim")

        constrain(anim) {
            top.linkTo(parent.top, (-20).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(indicator) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(content) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(indicator.top)
        }
    }


    val slidePage = rememberPagerState(0)

    ConstraintLayout(set) {

//        val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.scrolldown) }
//        val animationState = rememberLottieAnimationState(autoPlay = true, repeatCount = 4)
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.scrolldown))
        LottieAnimation(composition = composition, modifier = Modifier.layoutId("anim").size(80.dp), iterations = Int.MAX_VALUE)

        HorizontalPager(count = 6, state = slidePage, modifier = Modifier.layoutId("content")) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                when (it) {
                    0 -> Page0()
                    1 -> Page1()
                    2 -> Page2()
                    3 -> Page3()
                    4 -> Page4()
                    5 -> Page5(context)
                }
            }
        }

        Column(modifier = Modifier.layoutId("indicator")) {
            Spacer(modifier = Modifier.padding(4.dp))

            DotsIndicator(
                totalDots = 6,
                selectedIndex = slidePage.currentPage,
                selectedColor = Color(0xFF4F30FF),
                unSelectedColor = Color(0x70000000),
            )

            Spacer(
                modifier = Modifier
                    .padding(4.dp)
                    .height(20.dp)
            )
        }

    }

}

@Composable
fun Page0() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide0),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        )
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "داستان تخفیف داره",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = "ماجرای تخفیف داره از آن جایی شروع شد که ما تصمیم به بیان حرف تازه ای در دنیای تخفیف ها گرفتیم.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "در واقع استراتژی تخفیف داره به گونه ای طراحی شده، که فرصتی ویژه در اختیار خریداران قرار میدهد.",
                textAlign = TextAlign.Center
            )
            Text(
                text = "تخفیف داره صرفا داشتن یک تخفیف ساده برای کالاهای انتخابی شما نیست، بلکه !",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "حجم گسترده ای از حق انتخابه!",
                color = Color.Red,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Page1() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide1),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
        )
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "در حال حاضر تخفیف داره به صورت گسترده در سطح تهران، کرج و سمنان اصناف مختلف را نظیر پوشاک، استخر، طلا و جواهرات، کافه، رستوران، لوازم و خدمات الکترونیکی، عینک و... را پوشش میدهد.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "وسعت فعالیت تخفیف داره فقط محدود به کالا ها نیست بلکه گستردگی این مجموعه در زمینه های مختلف خدماتی و درمانی مانند: دندان پزشکی، کلینیک های زیبایی، آرایشگاه ها و... نیز است.",
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
fun Page2() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide2),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(.65f)
                .padding(vertical = 40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 15.dp)
                .fillMaxHeight(.6f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "در تخفیف داره میتوانید فقط با خرید یک کوپن، کالا های مورد نظر خودتان را تا سقف ۸۰٪ تخفیف خریداری کنید.",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Page3() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide3),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(.8f)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "در پنل شما امکان خرید انواع کارت فراهم شده است که هر کدام از کارت ها دارای تعداد مختلفی کوپن تخفیف هستند.",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Page4() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide4),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(.65f)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "تخفیف داره، بدون محدودیت مکانی خدمات خود را با پوشش دهی در سطح کشور به شما ارایه میدهد.",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Page5(context: Context) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.slide5),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(.65f)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "کوپن های تخفیف داره محدودیت زمانی ندارند، بنابراین بعد از خرید، شما میتوانید در هر زمانی که مایل بودید از کد تخفیف خود استفاده کنید.",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    if (TakhfifdareApplication.loggedInUser.value == null) {
                        Navigator.navigateTo(NavTarget.LoginScreen)
                    } else {
                        Toast.makeText(context, "شما وارد شده اید", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "ورود / ثبت نام", fontSize = 20.sp, color = Color.White)
            }

        }
    }
}


@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}