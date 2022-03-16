package com.image.viewer.demo.views.activities

import android.view.View
import com.image.viewer.demo.base.BaseActivity
import com.image.viewer.demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initBindingRef(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initRoot(): View? {
        return mBinding?.root
    }

    override fun initRef() {}

    override fun clicks() {}

    //destroying binding when activity is finished
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}