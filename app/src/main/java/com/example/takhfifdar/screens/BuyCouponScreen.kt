package com.example.takhfifdar.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.screens.viewmodels.BuyCouponScreenViewMode
import com.example.takhfifdar.util.NumberUnicodeAdapter

@Composable
fun BuyCouponScreen(viewModel: BuyCouponScreenViewMode) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current as Activity

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier.fillMaxWidth(.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(3f),
                    value = viewModel.discountCode.value,
                    onValueChange = { viewModel.discountCode.value = it },
                    label = { Text(text = "کد تخفیف") }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        viewModel.checkDiscountValidity()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(IntrinsicSize.Max)
                ) {
                    if (viewModel.discountCodeLoading.value) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(text = "ثبت", color = Color.White)
                    }
                }
            }
            Text(text = viewModel.discountStatus.value, color = Color(0xFF059700))
        }



        Spacer(modifier = Modifier.height(20.dp))
//        CouponCard(
//            context = context,
//            value = 12000,
//            count = 1,
//            username = viewModel.fullName,
//            pic = R.drawable.card_00,
//            discount = viewModel.discountPercentage.value,
//            viewModel = viewModel,
//            priceInScores = 200
//        )
        CouponCard(
            context = context,
            value = 100000,
            count = 10,
            username = viewModel.fullName,
            pic = R.drawable.card_03,
            discount = viewModel.discountPercentage.value,
            viewModel = viewModel,
            priceInScores = 1500
        )
        CouponCard(
            context = context,
            value = 140000,
            count = 15,
            username = viewModel.fullName,
            pic = R.drawable.card_02,
            discount = viewModel.discountPercentage.value,
            viewModel = viewModel,
            priceInScores = 2250
        )
        CouponCard(
            context = context,
            value = 270000,
            count = 30,
            username = viewModel.fullName,
            pic = R.drawable.card_01,
            discount = viewModel.discountPercentage.value,
            viewModel = viewModel,
            priceInScores = 4500
        )
    }

}

@Composable
fun CouponCard(
    context: Activity,
    value: Int,
    count: Int,
    username: String,
    pic: Int,
    discount: Int,
    viewModel: BuyCouponScreenViewMode,
    priceInScores: Int
) {

    val price = value - (value / 100) * discount

    val set = ConstraintSet {
        val image = createRefFor("image")
        val takhfifdare = createRefFor("takhfifdare")
        val valueTag = createRefFor("valueTag")
        val name = createRefFor("username")
        val countView = createRefFor("count")
        val newOffer = createRefFor("newOffer")

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

        constrain(newOffer) {
            top.linkTo(valueTag.bottom)
            start.linkTo(valueTag.start)
            end.linkTo(valueTag.end)
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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        text = NumberUnicodeAdapter().convert(NumberUnicodeAdapter().format(value)),
                        fontSize = 18.sp,
                        color = if (discount == 0) Color.White else Color.Red,
                        style = if (discount != 0) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(
                            textDecoration = TextDecoration.None
                        ),
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = " تومان",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                }

                if (discount != 0)
                    Text(
                        text = NumberUnicodeAdapter().convert(NumberUnicodeAdapter().format(price)),
                        modifier = Modifier.layoutId("newOffer"),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

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
        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {

//            Button(onClick = {
//                if (TakhfifdareApplication.loggedInUser.value?.score!! < priceInScores) return@Button
//                //  TODO
////                viewModel.buyByScore()
//            }) {
//                Text(text = "خرید با ${NumberUnicodeAdapter().convert(priceInScores.toString())} امتیاز", color = Color.White)
//            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                viewModel.proceedToGateway(
                    price = price.toString(),
                    type = value.toString(),
                    discount = if (viewModel.discountStatus.value != "") viewModel.discountCode.value else "",
                    context = context
                )
            }) {
                Text(text = "خرید", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}