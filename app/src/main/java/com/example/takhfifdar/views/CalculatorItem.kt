package com.example.takhfifdar.views

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun CalculatorItem(
    iteration: Int,
    state: MutableState<MutableList<CalcDataItem>>,
    onPriceChange: (String) -> Unit,
//    onDiscountChange: (String) -> Unit,
//    onTotalChange: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(value = state.value[iteration].price, onValueChange = { onPriceChange(it) }, modifier = Modifier.weight(1f))
        TextField(value = state.value[iteration].discount, onValueChange = {  }, modifier = Modifier.weight(1f))
        Text(text = calculateDiscount(state.value[iteration].price, state.value[iteration].discount), modifier = Modifier.weight(1f))
    }
}

fun calculateDiscount(price: String, discount: String): String {
    Log.e("testing", "price: $price,,, discount: $discount")
    if (price.isBlank() || discount.isBlank()) return ""
    if (price.count { it == '.' } > 1 || discount.count { it == '.' } > 1) {return "!"}

    return (price.toFloat() - (((price.toFloat()) * discount.toFloat()) / 100)).toString()
}

fun calculateTotal(a: String, b: String, c: String, d: String): String {
    var res = 0.0
    if (a == "!" || b == "!" || c == "!" || d == "!" )  return ""
    if (a.isNotBlank()) {
        res += a.toFloat()
    }
    if (b.isNotBlank()) {
        res += b.toFloat()
    }
    if (c.isNotBlank()) {
        res += c.toFloat()
    }
    if (d.isNotBlank()) {
        res += d.toFloat()
    }
    return res.toString()
}

data class CalcDataItem(
    var price: String = "",
    var discount: String = "",
    var total: String = ""
)