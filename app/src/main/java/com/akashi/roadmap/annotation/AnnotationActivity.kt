package com.akashi.roadmap.annotation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.akashi.annotations.BindView
import com.akashi.roadmap.R
import com.akashi.roadmap.databinding.ActivityAnnotationBinding

class AnnotationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnnotationBinding

    @BindView(R.id.tv)
    val textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}