package com.example.takhfifdar.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BackdropMenuItem(title: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .fillMaxSize()
            .clickable { onClick() }
        ) {
        Icon(imageVector = Icons.Filled.Logout, contentDescription = "icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, fontStyle = FontStyle.Italic, fontSize = 16.sp)
    }
}