package com.example.imageprocbrightnessandroidkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mImageView: ImageView
    private lateinit var mBitmap: Bitmap
    private lateinit var mBtnReset: Button
    private lateinit var mSeekBarAlpha: SeekBar
    private lateinit var mTextViewAlphaValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()
    }

    /**
     * レイアウトの初期設定
     */
    private fun initLayout() {
        mImageView = findViewById(R.id.image)
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)

        mBtnReset = findViewById(R.id.reset)
        mBtnReset?.setOnClickListener { onClickBtnReset() }

        mSeekBarAlpha = findViewById(R.id.alpha_seekBar)
        mSeekBarAlpha?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                var alphaValue = "$p1 %"
                mTextViewAlphaValue?.text = alphaValue

                var rightness = Brightness()
                var mutableBitmap = rightness.goImageProcessing(mBitmap, p1)
                mImageView.setImageBitmap(mutableBitmap.copy(Bitmap.Config.ARGB_8888, false))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        mTextViewAlphaValue = findViewById(R.id.alpha_value)
    }

    /**
     * リセットボタンのクリックイベント
     */
    private fun onClickBtnReset() {
        mImageView?.setImageBitmap(mBitmap)
    }
}
