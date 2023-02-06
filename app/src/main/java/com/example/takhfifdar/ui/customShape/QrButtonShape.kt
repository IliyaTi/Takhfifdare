package com.example.takhfifdar.ui.customShape

import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class QrButtonShape: androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        with(path) {
            moveTo(0f, 0f)
            addRoundRect(
                RoundRect(
                Rect(topLeft = Offset(0f, 0f), bottomRight = Offset(size.width, size.height - 40)),
                cornerRadius = CornerRadius(100f, 100f)
            )
            )
//            drawRoundRect(Color.Black, size = Size(size.width, size.height - 40), cornerRadius = CornerRadius(160f, 160f))
            moveTo(size.width / 2, size.height - 40)
            lineTo(size.width / 2, size.height)
            lineTo((size.width / 2) - 70, size.height - 40)
        }
        return Outline.Generic(path)
    }
}