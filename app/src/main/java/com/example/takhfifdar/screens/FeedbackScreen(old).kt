package com.example.takhfifdar.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FeedbackScreenOld() {

    val stars = remember {
        mutableStateOf(0)
    }
    var sides by remember { mutableStateOf(1) }
    val middleNavController = rememberNavController()
    val scrollState = rememberScrollState()

    val set = ConstraintSet {
        val topSec = createRefFor("topSec")
        val middleSec = createRefFor("middleSec")
        val bottomSec = createRefFor("bottomSec")

        constrain(topSec) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(bottomSec) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(middleSec) {
            top.linkTo(topSec.bottom)
            bottom.linkTo(bottomSec.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraintSet = set) {
        /** TOP START **/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .layoutId("topSec"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Submit a Feedback", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Vendor title", fontSize = 18.sp, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Vendor desc vendor desc vendor desc vendor desc vendor desc vendor desc vendor desc vendor desc vendor desc vendor desc",
                textAlign = TextAlign.Center,
                color = Color(0xFF5E5E5E)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= stars.value) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "",
                        tint = Color(0xFF06C200),
                        modifier = Modifier
                            .height(28.dp)
                            .clickable {
                                stars.value = i
                            }
                    )
                }
            }
            Text(text = rateDescription(stars.value), color = Color(0xFFA0A0A0))
        }
        /** TOP END **/
        /** MIDDLE START **/
        Column(modifier = Modifier.layoutId("middleSec")){

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = { sides = 1 }, modifier = Modifier.weight(1f)) {
                    Text(text = "DownSides")
                }
                OutlinedButton(onClick = { sides = 2 }, modifier = Modifier.weight(1f)) {
                    Text(text = "Upsides")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Divider(
                    Modifier
                        .height(2.dp)
                        .weight(1f), color = if (sides == 1) Color(0xFF06C200) else Color.White
                )
                Divider(
                    Modifier
                        .height(2.dp)
                        .weight(1f), color = if (sides == 2) Color(0xFF06C200) else Color.White
                )
            }
            Divider(modifier = Modifier.fillMaxWidth())
            NavHost(navController = middleNavController, startDestination = "upside") {
                composable("upside") {
                    UpSidesList(scrollState)
                }
            }
        }

        /** MIDDLE END **/
        /** BOTTOM START **/

        /** BOTTOM END **/
    }


}

fun rateDescription(i: Int): String {
    return when (i) {
        1 -> "VeryBad"
        2 -> "Bad"
        3 -> "Moderate"
        4 -> "Good"
        5 -> "Excellent"
        else -> ""
    }
}

@Composable
fun UpSidesList(scrollState: ScrollState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.scrollable(scrollState, Orientation.Vertical)) {
        Card(shape = MaterialTheme.shapes.small, backgroundColor = Color.Green) {
            Text(text = "teeeeeeeeessssttttt")
        }
    }
}

