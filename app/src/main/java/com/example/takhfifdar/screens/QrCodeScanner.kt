package com.example.takhfifdar.screens

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.takhfifdar.util.QrCodeAnalyzer


@Composable
fun QrCodeScanner(navController: NavController) {
    var code by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    // A surface container using the 'background' color from the theme
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colors.background
//    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val preview = Preview.Builder().build()
                    val selector =
                        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(previewView.width, previewView.height))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(ctx),
                        QrCodeAnalyzer { result ->
                            code = result
                            navController.navigate("FeedbackScreen")
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifeCycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )

        }
            Text(
                text = "لطفا بارکد مورد نظر را مقابل دوربین قرار دهید",
                color = Color(0xFFEBEBEB),
                modifier = Modifier.fillMaxWidth().background(Color(0x8F000000)).padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )
//    }
}