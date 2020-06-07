package com.example.imageprocbrightnessandroidkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private lateinit var mBitmap: Bitmap
        private const val READ_REQUEST_CODE: Int = 42
        private const val ERROR_MESSAGE_SELECT_IMAGE = "画像選択のエラーが発生しました"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        var imageView: ImageView = findViewById(R.id.image)
                        imageView.setImageBitmap(image)
                        mBitmap = image
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, ERROR_MESSAGE_SELECT_IMAGE, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * レイアウトの初期設定
     */
    private fun initLayout() {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)

        var btnReset: Button = findViewById(R.id.reset)
        btnReset?.setOnClickListener { onClickBtnReset() }

        var seekBarAlpha: SeekBar = findViewById(R.id.alpha_seekBar)
        seekBarAlpha?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                var alphaValue = "$p1 %"
                var textViewAlphaValue: TextView = findViewById(R.id.alpha_value)
                textViewAlphaValue?.text = alphaValue

                var brightness = Brightness()
                var mutableBitmap = brightness.goImageProcessing(mBitmap, p1)
                var imageView: ImageView = findViewById(R.id.image)
                imageView.setImageBitmap(mutableBitmap.copy(Bitmap.Config.ARGB_8888, false))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        var btnImageSelect: Button = findViewById(R.id.select_image)
        btnImageSelect?.setOnClickListener { onClickBtnSelectImage() }
    }

    /**
     * リセットボタンのクリックイベント
     */
    private fun onClickBtnReset() {
        var imageView: ImageView = findViewById(R.id.image)
        imageView?.setImageBitmap(mBitmap)
    }

    /**
     * 画像選択のクリックイベント
     */
    private fun onClickBtnSelectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }
}