package com.example.takhfifdar.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Divider
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalLayoutDirection
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//import com.example.takhfifdar.util.NumberUnicodeAdapter
//
//@Composable
//fun HistoryScreen(viewModel: HistoryScreenViewModel) {
//
//    LaunchedEffect(key1 = true) {
//        viewModel.loading.value = true
//        viewModel.loadData()
//    }
//
//    Box(modifier = Modifier
//        .fillMaxSize()
////        .background(Color(0xFF340080))
//    ) {
//
//        Image(
//            painter = painterResource(id = com.example.takhfifdar.R.drawable.bg),
//            contentDescription = "",
////            colorFilter = ColorFilter.tint(Color(0xFF3F0099)),
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds
//        )
//
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//
//            if (viewModel.items.isNotEmpty()) {
//                items(viewModel.items) {
//                    HistoryItem(item = it)
//                }
//            } else {
//                item() {
//                    Row(modifier = Modifier.fillParentMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//                        Text(text = "تاریخچه شما خالی است", fontWeight = FontWeight.Bold)
//                    }
//                }
//            }
//
//        }
//
//        if (viewModel.loading.value)
//        Box(modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0x6A000000))) {
//            CircularProgressIndicator(color = Color.White, strokeWidth = 12.dp)
//        }
//    }
//}
//
//
//@Composable
//fun HistoryItem(item: Transaction) {
//    CompositionLocalProvider(LocalLayoutDirection.provides(LayoutDirection.Rtl)) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp), shape = RoundedCornerShape(20.dp),
//            backgroundColor = Color(0xFF9C79FF)
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 15.dp, vertical = 5.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "شماره فاکتور: ${item.billSerial}")
//                    Text(text = "تاریخ: ${item.date}")
//                }
//                Divider(modifier = Modifier.fillMaxWidth())
//                Text(
//                    text = item.vendorName,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    modifier = Modifier.padding(20.dp)
//                )
//                Text(text = "تخفیف ارائه شده: " + NumberUnicodeAdapter().convert(item.discountPercent) + " درصد")
//                Text(
//                    text = "تلفن تماس: " + NumberUnicodeAdapter().convert(item.vendorPhone),
//                    modifier = Modifier.padding(horizontal = 20.dp)
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//
//        }
//    }
//}
//
