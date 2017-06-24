package se.sodapop.sudokuscanner

import android.content.Loader
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import com.google.android.cameraview.CameraView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class ScannerActivity() : AppCompatActivity() {

    private lateinit var mCameraView: CameraView
    private val TAG = "SudokuScanner"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

//        mCameraView = findViewById(R.id.camera) as CameraView
//        mCameraView.addCallback(object : CameraView.Callback() {
//            override fun onCameraOpened(cameraView: CameraView?) {
//                Log.d(TAG, "onCameraOpened")
//            }
//
//            override fun onCameraClosed(cameraView: CameraView?) {
//                Log.d(TAG, "onCameraClosed")
//            }
//
//            override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
//                Log.d(TAG, "onPictureTaken")
//            }
//        })

        OpenCVLoader.initDebug()

        val bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sudoku)
        val rgba = Mat()

        Utils.bitmapToMat(bitmap, rgba)
        val edges = Mat(rgba.size(), CvType.CV_8UC1)
        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4)
        Imgproc.Canny(edges, edges, 80.0, 100.0)
        val b = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(edges, b)
        val img = findViewById(R.id.imageView) as ImageView
        img.setImageBitmap(b)
    }

    override fun onResume() {
        super.onResume()
//        mCameraView.start()
    }

    override fun onPause() {
//        mCameraView.stop()
        super.onPause()
    }
}
