package com.example.takhfifdar.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BackdropMenuItem(
    title: String,
    backgroundColor: Brush = Brush.horizontalGradient(listOf(Color(0xff2e3192), Color(0xff8160ed))),
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(60.dp)
            .fillMaxSize()
            .clickable { onClick() }
            .background(backgroundColor)
    ) {
        Icon(imageVector = icon, contentDescription = "icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, fontStyle = FontStyle.Italic, fontSize = 16.sp, color = Color.White)
    }
}