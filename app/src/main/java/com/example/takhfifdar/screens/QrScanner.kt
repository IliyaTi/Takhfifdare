package com.example.takhfifdar.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.budiyev.android.codescanner.*
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.viewmodels.QrCodeScannerViewModel
import com.example.takhfifdar.util.runOnMain
import org.json.JSONObject

@Composable
fun QrScanner(viewModel: QrCodeScannerViewModel) {

    lateinit var codeScanner: CodeScanner
    var storeId by remember { mutableStateOf(0) }
    var storeName by remember { mutableStateOf("") }
    var loadingState by remember { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val scannerView = CodeScannerView(context)
            codeScanner = CodeScanner(context, scannerView)
            codeScanner.camera = CodeScanner.CAMERA_BACK
            codeScanner.formats = CodeScanner.ALL_FORMATS

            codeScanner.autoFocusMode = AutoFocusMode.SAFE
            codeScanner.scanMode = ScanMode.SINGLE
            codeScanner.isAutoFocusEnabled = true
            codeScanner.isFlashEnabled = false

            codeScanner.decodeCallback = DecodeCallback {
                codeScanner.stopPreview()
                runOnMain {
                    try {
                        loadingState = true
                        val vendorObj = JSONObject(it.text)
                        storeId = vendorObj.getInt("id")
                        storeName = vendorObj.getString("username")
                        val job = viewModel.useCredit(storeId, vendorObj)
                        job.join()
                        codeScanner.releaseResources()
                        loadingState = false
                    } catch (e: Exception) {
                        loadingState = false
                        Toast.makeText(context, "بارکد معتبر نیست", Toast.LENGTH_SHORT).show()
                        codeScanner.startPreview()
                    }

                }
            }
            codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnMain {
                    Toast.makeText(
                        context, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            codeScanner.startPreview()
            scannerView
        }

    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = "لطفا بارکد مورد نظر را روبروی دوربین خود قرار دهید", color = Color.White)
    }

    if (loadingState) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { }
            .background(Color(0x94000000)), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(200.dp))
        }
    }
}