package com.example.takhfifdar.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.takhfifdar.R
import com.example.takhfifdar.screens.classes.RatingItem

@Composable
fun FeedbackScreen(navController: NavController) {

    BackHandler {
        navController.navigate("HomeScreen") {
            popUpTo("SplashScreen") {
                inclusive = true
            }
        }
    }

    val selectedTab = remember { mutableStateOf(1) }

    val posFeedBack = remember {
        mutableStateListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        )
    }
    val negFeedBack = remember {
        mutableStateListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        )
    }

    val selectedRate = remember {
        mutableStateOf(0)
    }

    val wholeSet = ConstraintSet {
        val logo = createRefFor("logo")
        val vendorName = createRefFor("name")
        val rating = createRefFor("rating")
        val tabs = createRefFor("tabs")

        constrain(logo) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(vendorName) {
            top.linkTo(logo.bottom)
            end.linkTo(parent.end)
        }

        constrain(rating) {
            top.linkTo(vendorName.bottom, 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(tabs) {
            height = Dimension.fillToConstraints
            top.linkTo(rating.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }

    }

    val tabsSet = ConstraintSet {
        val tabs = createRefFor("tabs")
        val content = createRefFor("content")
        val submit = createRefFor("submit")

        constrain(tabs) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(submit) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        constrain(content) {
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
            top.linkTo(tabs.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(submit.top)
        }


    }


    ConstraintLayout(wholeSet, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .layoutId("logo")
                .size(150.dp)
        )
        Text(
            text = "نام فروشگاه",
            fontSize = 36.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier
                .layoutId("name")
                .padding(end = 36.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 18.dp)
                .layoutId("rating"),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (i in 1..5) {
                Image(
                    painter = painterResource(ratingOptions[i - 1].imgRes),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(if (selectedRate.value == i) ratingOptions[i - 1].color else Color.Gray),
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { selectedRate.value = i }
                        .size(48.dp)
                )
            }
        }

        ConstraintLayout(modifier = Modifier.layoutId("tabs"), constraintSet = tabsSet) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("tabs"),
            ) {
                OutlinedButton(
                    onClick = { selectedTab.value = 1 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedTab.value == 1) Color(
                            0xffE5E5E5
                        ) else Color(0xffF2F2F2)
                    ),
                    shape = RoundedCornerShape(topStart = 20.dp),
                    border = BorderStroke(0.dp, Color(0xffffffff)),
                    elevation = ButtonDefaults.elevation()
                ) {
                    Text(text = "نظرات منفی", fontSize = 24.sp)
                }
                OutlinedButton(
                    onClick = { selectedTab.value = 2; },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedTab.value == 1) Color(
                            0xffF2F2F2
                        ) else Color(0xffE5E5E5)
                    ),
                    shape = RoundedCornerShape(topEnd = 20.dp),
                    border = BorderStroke(0.dp, Color(0xffffffff)),
                    elevation = ButtonDefaults.elevation()
                ) {
                    Text(text = "نظرات مثبت", fontSize = 24.sp)
                }
            }
            if (selectedTab.value == 1)
            /** Negative feedback page : START **/
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .layoutId("content")
                        .background(Color(0xffE5E5E5))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in 0..4) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = "رفتار غیر محترمانه", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        checked = negFeedBack[i],
                                        onCheckedChange = { negFeedBack[i] = !negFeedBack[i] },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in 5..9) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = "رفتار غیر محترمانه", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        checked = negFeedBack[i],
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        onCheckedChange = { negFeedBack[i] = !negFeedBack[i] },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            /** Negative feedback page : END **/
            else
            /** POSITIVE feedback page : START **/
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .layoutId("content")
                        .background(Color(0xffE5E5E5))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in 0..4) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = "رفتار محترمانه", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        checked = posFeedBack[i],
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        onCheckedChange = { posFeedBack[i] = !posFeedBack[i] },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in 5..9) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = "رفتار محترمانه", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        checked = posFeedBack[i],
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        onCheckedChange = { posFeedBack[i] = !posFeedBack[i] },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            /** POSITIVE feedback page : END **/

            Row(
                modifier = Modifier
                    .layoutId("submit")
                    .background(Color(0xffE5E5E5)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xffE5E5E5),
                        contentColor = Color(0xff009245)
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "ثبت نظر", style = MaterialTheme.typography.h6)
                }
                Button(
                    modifier = Modifier
                        .weight(2f)
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xff009245)),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "ثبت بازخورد",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}

val ratingOptions: List<RatingItem> = listOf(
    RatingItem(R.drawable.excellent, Color(0xff339048), "عالی"),
    RatingItem(R.drawable.good, Color(0xff92C442), "خوب"),
    RatingItem(R.drawable.moderate, Color(0xFFF9D800), "معمولی"),
    RatingItem(R.drawable.bad, Color(0xffF3AD3A), "بد"),
    RatingItem(R.drawable.awful, Color(0xffB6292D), "خیلی بد")
)


//@Preview(showBackground = true)
//@Composable
//fun FeedbackPreview() {
//    FeedbackScreen()
//}