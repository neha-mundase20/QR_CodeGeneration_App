package com.example.qr_codegeneration

import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.EnumMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun generateQRCodeBitmap(text: String, width: Int, height: Int): Bitmap? {
        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

        try {
            val qrCodeWriter = QRCodeWriter()
//            Convert URL to binary matrix
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints)
//            A BitMatrix is used to represent information in a compact form.
//            For example, in the context of QR codes, each cell in the BitMatrix
//            corresponds to a pixel in the QR code image.
//            The binary value of each cell determines whether the pixel should be
//            black (1) or white (0) in the QR code.

            val qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

//            Now we received a matrix of 0's and 1's,
//            now we need to create a QR Code bitmap
//            from the obtained binary matrix

            for (x in 0 until width) {
                for (y in 0 until height) {
                    qrCodeBitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }
            return qrCodeBitmap

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun onGenerateButtonClicked(view: View) {
        val qrImage = findViewById<ImageView>(R.id.qrImage)
        val width = qrImage.width
        val height = qrImage.height
        val url = findViewById<TextInputEditText>(R.id.inputURL).text.toString()
        val qrCodeBitmap = generateQRCodeBitmap(url,width,height)
        qrImage.setImageBitmap(qrCodeBitmap)
    }
}