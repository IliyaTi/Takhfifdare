package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.takhfifdar.screens.viewmodels.BuyCouponScreenViewMode

@Composable
fun BuyCouponScreen(viewModel: BuyCouponScreenViewMode) {
    val scrollState = rememberScrollState()

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val discountCode = remember { mutableStateOf("") }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(modifier = Modifier.fillMaxWidth(.8f), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier.weight(3f),
                    value = discountCode.value,
                    onValueChange = { discountCode.value = it },
                    label = { Text(text = "کد تخفیف") }
                )
                Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = "اعمال کد")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        CouponCard(
            value = "۵۰,۰۰۰",
            count = 5,
            username = viewModel.fullName,
            pic = R.drawable.card_03
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "خرید")
        }
        Spacer(modifier = Modifier.height(20.dp))
        CouponCard(
            value = "۱۰۰,۰۰۰",
            count = 10,
            username = viewModel.fullName,
            pic = R.drawable.card_02
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "خرید")
        }
        Spacer(modifier = Modifier.height(20.dp))
        CouponCard(
            value = "۱۵۰,۰۰۰",
            count = 15,
            username = viewModel.fullName,
            pic = R.drawable.card_01
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "خرید")
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun CouponCard(value: String, count: Int, username: String, pic: Int) {

    val set = ConstraintSet {
        val image = createRefFor("image")
        val takhfifdare = createRefFor("takhfifdare")
        val valueTag = createRefFor("valueTag")
        val name = createRefFor("username")
        val countView = createRefFor("count")

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

        constrain(name) {
            bottom.linkTo(parent.bottom, 20.dp)
            start.linkTo(parent.start, 30.dp)
        }

        constrain(countView) {
            bottom.linkTo(parent.bottom, 20.dp)
            end.linkTo(parent.end, 30.dp)
        }

    }

    ConstraintLayout(set, modifier = Modifier.fillMaxWidth(0.8f)) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Image(
                painter = painterResource(id = pic),
                contentDescription = "",
                modifier = Modifier.layoutId("image")
            )
            Text(
                text = "تخفیف داره",
                modifier = Modifier.layoutId("takhfifdare"),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(modifier = Modifier.layoutId("valueTag")) {
                Text(
                    text = "مبلغ ",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
                Text(
                    text = value,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
                Text(
                    text = " تومان",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
            }
            Column(
                modifier = Modifier.layoutId("count"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد", color = Color.White)
                Text(text = count.toString(), color = Color.White)
            }
            Column(
                modifier = Modifier.layoutId("username"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "مشخصات", color = Color.White)
                Text(text = username, color = Color.White, fontWeight = FontWeight.W400)
            }
        }
    }
}