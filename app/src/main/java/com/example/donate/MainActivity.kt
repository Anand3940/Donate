package com.example.donate

import MainActivityPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.donate.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mMainActivityPagerAdapter: MainActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

//        binding.buttonLogout.setOnClickListener{
//          FirebaseAuth.getInstance().signOut()
//          val intent = Intent(this,Sign_In::class.java)
//          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
//                  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//          startActivity(intent)
//          finish()
//      }

        initViews()
    }

    private fun initViews() {
        mTabLayout = mBinding.tabLayout
        mViewPager = mBinding.viewPager

        mMainActivityPagerAdapter = MainActivityPagerAdapter(this, supportFragmentManager)
        mViewPager.adapter = mMainActivityPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)


    }


}