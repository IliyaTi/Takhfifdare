package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication

@Composable
fun BuyCouponScreen() {
    val scrollstate = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().scrollable(scrollstate, orientation = Orientation.Vertical), horizontalAlignment = Alignment.CenterHorizontally) {
        CouponCard(value = "۵۰,۰۰۰", count = 5, username = TakhfifdareApplication.loggedInUser.value?.name ?: "", pic = R.drawable.card_03, modifier = Modifier.padding(20.dp))
        CouponCard(value = "۱۰۰,۰۰۰", count = 10, username = TakhfifdareApplication.loggedInUser.value?.name ?: "", pic = R.drawable.card_02, modifier = Modifier.padding(20.dp))
        CouponCard(value = "۱۵۰,۰۰۰", count = 15, username = TakhfifdareApplication.loggedInUser.value?.name ?: "", pic = R.drawable.card_01, modifier = Modifier.padding(20.dp))
    }

}

@Composable
fun CouponCard(value: String, count: Int, username: String, pic: Int, modifier: Modifier) {

    val set = ConstraintSet {
        val image = createRefFor("image")
        val takhfifdare = createRefFor("takhfifdare")
        val valueTag = createRefFor("valueTag")
        val username = createRefFor("username")
        val count = createRefFor("count")

        constrain(image) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        
        constrain(takhfifdare) {
            top.linkTo(parent.top, 40.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(valueTag) {
            top.linkTo(takhfifdare.bottom, 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(username) {
            bottom.linkTo(parent.bottom, 20.dp)
            start.linkTo(parent.start, 45.dp)
        }

        constrain(count) {
            bottom.linkTo(parent.bottom, 20.dp)
            end.linkTo(parent.end, 45.dp)
        }

    }

    ConstraintLayout(set, modifier = modifier) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Image(painter = painterResource(id = pic), contentDescription = "", modifier = Modifier.layoutId("image"))
            Text(text = "تخفیف داره", modifier = Modifier.layoutId("takhfifdare"), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Row(modifier = Modifier.layoutId("valueTag")) {
                Text(text = "مبلغ ", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W500)
                Text(text = value, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W500)
                Text(text = " تومان", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W500)
            }
            Column(modifier = Modifier.layoutId("count"), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "تعداد", color = Color.White)
                Text(text = count.toString(), color = Color.White)
            }
            Column(modifier = Modifier.layoutId("username"), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "مشخصات", color = Color.White)
                Text(text = username, color = Color.White, fontWeight = FontWeight.W400)
            }
        }
    }
}