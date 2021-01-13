package com.smarttersstudio.mlkitface

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.TextureView
import androidx.camera.core.CameraX
import androidx.core.content.ContextCompat

private const val CAMERA_PERMISSION_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

  private lateinit var cameraView: TextureView
  private lateinit var facePointsView: FacePointsView

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    cameraView = findViewById(R.id.camera_view)
    facePointsView = findViewById(R.id.face_points_view)

    cameraView.post {
      setUpCameraX()
    }

      if(!hasCameraPermissions())
        requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    else{

        /**
         * Initialize Camera
         */

        setUpCameraX()
    }


    }

  private fun setUpCameraX() {
    cameraView.post {
      CameraX.unbindAll()
      val displayMetrics = DisplayMetrics().also { cameraView.display.getRealMetrics(it) }
      val screenSize = Size(displayMetrics.widthPixels, displayMetrics.heightPixels)
      val aspectRatio = Rational(displayMetrics.widthPixels, displayMetrics.heightPixels)
      val rotation = cameraView.display.rotation


      val autoFitPreviewAnalysis = AutoFitPreviewAnalysis.build(screenSize, aspectRatio, rotation, cameraView,facePointsView)

      CameraX.bindToLifecycle(this, autoFitPreviewAnalysis.previewUseCase, autoFitPreviewAnalysis.analysisUseCase)
    }

  }

  private fun hasCameraPermissions(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        /**
         * Initialize Camera
         */

        setUpCameraX()
      }
    }
  }

}
