package com.image.viewer.demo.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRef()
        clicks()
    }

    abstract fun clicks()
    abstract fun initRef()

}
