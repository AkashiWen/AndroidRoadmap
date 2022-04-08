package com.akashi.roadmap.annotation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.akashi.annotations.BindView
import com.akashi.roadmap.R

class AnnotationActivity : AppCompatActivity() {

    @BindView(R.id.tv)
    var textView: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)

        AButterKnife.bind(this)

        // 如果AButterKnife.bind(this)成功生效就会显示下面文字
        textView?.text = "AButterKnife!!"
    }


}