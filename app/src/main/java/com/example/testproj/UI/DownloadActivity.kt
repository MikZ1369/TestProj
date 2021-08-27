package com.example.testproj.UI

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.testproj.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DownloadActivity: AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var id = 0
    private lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        val bundle = intent.extras
        val nameTextView = findViewById<TextView>(R.id.name_text)
        val imageView = findViewById<ImageView>(R.id.image)
        val buttonDownload = findViewById<Button>(R.id.download_button)
        val buttonDelete = findViewById<Button>(R.id.delete_button)
        if (getFile().exists()) {
            buttonDelete.visibility = View.VISIBLE
        }
        bundle?.getString("name")?.let { nameTextView.text = it }
        bundle?.getInt("id")?.let { id = it }
        bundle?.getString("imageURL")?.let{
            Glide.with(this).asBitmap().load(it).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    imageView.setImageBitmap(resource)
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        }
        buttonDownload.setOnClickListener {
            if (isStoragePermissionGranted()) {
                saveImage()
                buttonDelete.visibility = View.VISIBLE
            }
        }
        buttonDelete.setOnClickListener {
            if (isStoragePermissionGranted()) {
                deleteImage()
                buttonDelete.visibility = View.GONE
            }
        }
    }

    fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    private fun saveImage() {
        scope.launch {
            val file = getFile()
            if (file.exists())
                file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush();
                out.close();
            } catch (e: Exception) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DownloadActivity, "File saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteImage() {
        scope.launch {
            val file = getFile()
            if (file.exists()) {
                file.canonicalFile.delete()
                if (file.exists()) {
                    applicationContext.deleteFile(file.name)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DownloadActivity, "File deleted: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getFile(): File {
        val myDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fname = "Image-" + id + ".jpg"
        return File(myDir, fname)
    }
}