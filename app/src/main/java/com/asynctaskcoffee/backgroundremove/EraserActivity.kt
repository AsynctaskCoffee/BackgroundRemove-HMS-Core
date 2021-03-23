package com.asynctaskcoffee.backgroundremove

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker


class EraserActivity : AppCompatActivity(), EraserListener {

    lateinit var result: ImageView
    lateinit var target: ImageView
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eraser)
        initViews()
    }

    private fun erase() {
        val bitmapInput = BitmapFactory.decodeResource(
            resources,
            R.drawable.human_three
        )
        Eraser.eraseAndReturnResult(
            bitmapInput,
            this@EraserActivity
        )
    }

    private fun initViews() {
        result = findViewById(R.id.result)
        target = findViewById(R.id.target)
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

    fun selectImage(view: View) {
        ImagePicker.with(this)
            .start { resultCode, data ->
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        fileUri = data?.data
                        val bitmap = BitmapFactory.decodeFile(fileUri?.path)
                        target.setImageBitmap(bitmap)
                        Eraser.eraseAndReturnResult(
                            bitmap,
                            this@EraserActivity
                        )
                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(
                            this@EraserActivity,
                            ImagePicker.getError(data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(this@EraserActivity, "Task Cancelled", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }
}