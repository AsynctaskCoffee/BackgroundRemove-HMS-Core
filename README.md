# BackgroundRemove-HMS-Core
BackgroundErase with image-segmentation model HMS Core


![Preview](/previews/img1.png)   ![Preview](/previews/img2.gif)


### Methods

```kotlin
fun onStartProcess()
fun onEndProcess()
fun onResultReady(bitmap: Bitmap?)
fun onError(exception: Exception?)
```

### Lib usage

```kotlin
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
```


