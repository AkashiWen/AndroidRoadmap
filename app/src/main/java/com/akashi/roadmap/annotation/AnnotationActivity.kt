package com.akashi.roadmap.annotation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.akashi.annotations.BindView
import com.akashi.roadmap.R

class AnnotationActivity : AppCompatActivity() {

//    lateinit var binding: ActivityAnnotationBinding

    @BindView(R.id.tv)
    val textView: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityAnnotationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_annotation)
        AButterKnife.bind(this)
        textView?.text = "AButterKnife!!"
    }


}