package com.example.takhfifdar.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.takhfifdar.R
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.classes.RatingItem
import com.example.takhfifdar.screens.viewmodels.FeedbackScreenViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedbackScreen(vendor: String, viewModel: FeedbackScreenViewModel) {

    var vendorId by remember { mutableStateOf(0) }
    var vendorName by remember { mutableStateOf("") }
    var vendorPic by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        val vendorObj = JSONObject(vendor.split("~")[0])
        try {
            vendorPic = vendor.split("~")[1].replace("*", "/")
        } catch (e: Exception){
            Log.e("FEEDBACKSCREEN", "picture path does not exist!")
        }
        vendorId = vendorObj.getInt("id")
        vendorName = vendorObj.getString("username")
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    BackHandler {
        Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
    }


    val wholeSet = ConstraintSet {
        val topPart = createRefFor("topPart")
//        val logo = createRefFor("logo")
//        val vendorName = createRefFor("name")
        val rating = createRefFor("rating")
        val tabs = createRefFor("tabs")
//        val close = createRefFor("close")

        constrain(topPart) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(rating) {
            top.linkTo(topPart.bottom)
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
    
    val topSet = ConstraintSet {
        val bar = createRefFor("bar")
        val vendorName = createRefFor("name")
        val image = createRefFor("image")
        
        constrain(bar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
        
        constrain(vendorName) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        
        constrain(image) {
            height = Dimension.fillToConstraints
            top.linkTo(parent.top)
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

//    if (!viewModel.qrScannerError.value)
    ConstraintLayout(wholeSet, modifier = Modifier.fillMaxSize()) {
        
        ConstraintLayout(topSet, modifier = Modifier
            .layoutId("topPart")
            .height(200.dp)) {

            GlideImage(
                model = vendorPic,
                contentDescription = "",
                modifier = Modifier.layoutId("image"),
                contentScale = ContentScale.Crop
            )

            Row(modifier = Modifier.fillMaxWidth().layoutId("bar").background(Brush.verticalGradient(
                listOf(Color.White, Color.Transparent)
            )), horizontalArrangement = Arrangement.SpaceBetween) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 6.dp, top = 12.dp)
                )

                IconButton(onClick = { Navigator.navigateTo(NavTarget.HomeScreen) }, modifier = Modifier
                    .padding(end = 6.dp, top = 6.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "", modifier = Modifier)
                }
            }



            Box(modifier = Modifier
                .layoutId("name")
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.White),
                        tileMode = TileMode.Decal
                    )
                )) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ) {
                    Text(
                        text = vendorName,
                        fontSize = 28.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .padding(end = 36.dp, start = 36.dp, top = 20.dp),
                        color = Color.Black
                    )
                }
            }


        }

        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 25.dp, vertical = 18.dp)
                .layoutId("rating"),
            verticalAlignment = Alignment.CenterVertically
        ) {

            for (i in 1..5) {
                val rateAnimation = animateDpAsState(
                    targetValue = if (viewModel.selectedRate.value == i) 60.dp else 48.dp,
                    animationSpec = spring(Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium),
                )
                Image(
                    painter = painterResource(ratingOptions[i - 1].imgRes),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(if (viewModel.selectedRate.value == i) ratingOptions[i - 1].color else Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { viewModel.selectedRate.value = i }
                        .size(rateAnimation.value)
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
                    onClick = { viewModel.selectedTab.value = 1 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (viewModel.selectedTab.value == 1) Color(
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
                    onClick = { viewModel.selectedTab.value = 2; },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (viewModel.selectedTab.value == 1) Color(
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
            /** Negative feedback page : START **/
                AnimatedVisibility(
                    visible = viewModel.selectedTab.value == 1,
                    modifier = Modifier.layoutId("content"),
                    enter = slideInHorizontally() { -1050 },
                    exit = slideOutHorizontally() { -1050 }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
//                            .layoutId("content")
                            .background(Color(0xffE5E5E5))
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 20.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.weight(1f)
                            ) {
                                for (i in 0..2) {
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(6.dp)
                                    ) {
                                        Text(text = viewModel.negFeedBack[i].first, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Checkbox(
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color(
                                                    0xff009245
                                                )
                                            ),
                                            checked = viewModel.negFeedBack[i].second,
                                            onCheckedChange = {
                                                viewModel.negFeedBack[i] = Pair(
                                                    viewModel.negFeedBack[i].first,
                                                    !viewModel.negFeedBack[i].second
                                                )
                                            },
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 6.dp)
                            ) {
                                for (i in 3..5) {
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(6.dp)
                                    ) {
                                        Text(text = viewModel.negFeedBack[i].first, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Checkbox(
                                            checked = viewModel.negFeedBack[i].second,
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color(
                                                    0xff009245
                                                )
                                            ),
                                            onCheckedChange = {
                                                viewModel.negFeedBack[i] = Pair(
                                                    viewModel.negFeedBack[i].first,
                                                    !viewModel.negFeedBack[i].second
                                                )
                                            },
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            /** Negative feedback page : END **/
            /** POSITIVE feedback page : START **/

            AnimatedVisibility(
                visible = viewModel.selectedTab.value == 2,
                modifier = Modifier.layoutId("content"),
                enter = slideInHorizontally() { 1050 },
                exit = slideOutHorizontally() { 1050 }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
//                        .layoutId("content")
                        .background(Color(0xffE5E5E5))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in 0..3) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = viewModel.posFeedBack[i].first, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        checked = viewModel.posFeedBack[i].second,
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        onCheckedChange = {
                                            viewModel.posFeedBack[i] = Pair(
                                                viewModel.posFeedBack[i].first,
                                                !viewModel.posFeedBack[i].second
                                            )
                                        },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp)
                        ) {
                            for (i in 4..7) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(text = viewModel.posFeedBack[i].first, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Checkbox(
                                        checked = viewModel.posFeedBack[i].second,
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(
                                                0xff009245
                                            )
                                        ),
                                        onCheckedChange = {
                                            viewModel.posFeedBack[i] = Pair(
                                                viewModel.posFeedBack[i].first,
                                                !viewModel.posFeedBack[i].second
                                            )
                                        },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
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
                    onClick = { viewModel.openDialog.value = true }
                ) {
                    Text(text = "ثبت نظر", style = MaterialTheme.typography.h6)
                }
                Button(
                    modifier = Modifier
                        .weight(2f)
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xff009245)),
                    onClick = {
                        scope.launch {
                            if (viewModel.selectedRate.value != 0) {
                                viewModel.sendFeedback(vendorId, vendorName)
                            } else {
                                Toast.makeText(
                                    context,
                                    "لطفا میزان رضایت خود را مشخص کنید",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    enabled = !viewModel.loadingState.value
                ) {
                    if (!viewModel.loadingState.value)
                        Text(
                            text = "ثبت بازخورد",
                            color = Color.White,
                            style = MaterialTheme.typography.h6
                        )
                    else
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(30.dp)
                        )
                }
            }
        }
    }
//    else
//        Column(modifier = Modifier.fillMaxSize()) {
//            Icon(imageVector = Icons.Default.QrCode2, contentDescription = "")
//            Text(text = "کد اسکن شده معتبر نمیباشد")
//        }

    if (viewModel.openDialog.value) {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            AlertDialog(
                onDismissRequest = { viewModel.openDialog.value = false },
                confirmButton = {
                    Button(onClick = { viewModel.openDialog.value = false }) {
                        Text(text = "ثبت")
                    }
                },
                text = {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { viewModel.openDialog.value = false },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "CloseDialog"
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = "ثبت نظر",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.6).dp),
                            value = viewModel.comment.value,
                            maxLines = 10,
                            onValueChange = { viewModel.comment.value = it })
                    }
                }
            )
        }
    }

    if (viewModel.loadingState.value) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { })
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