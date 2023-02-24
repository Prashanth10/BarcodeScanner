package com.example.barcodescanner.barcode

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(private val context: Context) : ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        Log.d("flagApiAnalyze", mediaImage.toString())
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Pass image to an ML Kit Vision API
            // ...

            //val options = BarcodeScannerOptions.Builder()
             //   .build()

            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        // Handle received barcodes...
                        Log.d("flagApiResult",barcode.rawValue)
                        Toast.makeText(context, "Value: "+ barcode.rawValue, Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Log.d("flagApiFailResult", "failed")
                }
            imageProxy.close()
        }
    }
}