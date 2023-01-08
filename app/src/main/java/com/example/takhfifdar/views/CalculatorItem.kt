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
    state: MutableState<List<CalcDataItem>>,
//    onPriceChange: (String) -> Unit,
//    onDiscountChange: (String) -> Unit,
//    onTotalChange: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(value = state.value[iteration].price, onValueChange = { state.value[iteration].price = it  }, modifier = Modifier.weight(1f))
        TextField(value = state.value[iteration].discount, onValueChange = {  }, modifier = Modifier.weight(1f))
        Text(text = calculate(state.value[iteration].price, state.value[iteration].discount), modifier = Modifier.weight(1f))
    }
}

fun calculate(price: String, discount: String): String {
    Log.e("testing", "price: $price,,, discount: $discount")
    if (price.isBlank() || discount.isBlank()) return ""

    return (price.toInt() - (((price.toInt()) * discount.toInt()) / 100)).toString()
}

data class CalcDataItem(
    var price: String = "",
    var discount: String = "",
    var total: String = ""
)