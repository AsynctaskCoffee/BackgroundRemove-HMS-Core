package com.asynctaskcoffee.backgroundremove

import android.graphics.Bitmap
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting

class Eraser {

    companion object {
        private var mAnalyzer: MLImageSegmentationAnalyzer

        init {
            val analyzerSetting = MLImageSegmentationSetting.Factory()
                .setExact(true)
                .setAnalyzerType(MLImageSegmentationSetting.BODY_SEG)
                .setScene(MLImageSegmentationScene.FOREGROUND_ONLY)
                .create()

            MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(analyzerSetting).also {
                mAnalyzer = it
            }
        }

        @JvmStatic
        fun eraseAndReturnResult(bitmap: Bitmap, eraserListener: EraserListener?) {
            val mlFrame = MLFrame.fromBitmap(bitmap)
            mAnalyzer.asyncAnalyseFrame(mlFrame)
                .addOnSuccessListener { returnImageSegmentation ->
                    eraserListener?.onResultReady(returnImageSegmentation.foreground)
                }
                .addOnFailureListener { exception ->
                    eraserListener?.onError(exception)
                }
        }
    }

}

interface EraserListener {
    fun onStartProcess()
    fun onEndProcess()
    fun onResultReady(bitmap: Bitmap?)
    fun onError(exception: Exception?)
}