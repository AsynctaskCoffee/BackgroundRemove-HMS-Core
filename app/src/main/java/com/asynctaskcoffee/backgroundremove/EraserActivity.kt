package com.asynctaskcoffee.backgroundremove

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EraserActivity : AppCompatActivity(), EraserListener {

    lateinit var result: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eraser)
        initViews()
    }

    private fun erase() {
        val bitmapInput = BitmapFactory.decodeResource(
            resources,
            R.drawable.human
        )
        Eraser.eraseAndReturnResult(
            bitmapInput,
            this@EraserActivity
        )
    }

    private fun initViews() {
        result = findViewById(R.id.result)
    }

    override fun onStartProcess() {

    }

    override fun onEndProcess() {

    }

    override fun onResultReady(bitmap: Bitmap?) {
        result.setImageBitmap(bitmap)
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(this@EraserActivity, exception?.message, Toast.LENGTH_SHORT).show()
    }

    fun eraseBackground(view: View) {
        erase()
    }
}