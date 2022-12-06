package com.example.testdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter= OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                   onboardingImage = R.drawable.welcome,
                    title = "Welcome To Test Droid!",
                    description = "Detect pixels that are failing on your screen."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.welcome2,
                    title = "Wait a minute",
                    description = "You can also take other tests for free to find out if something happens with your device."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.welcome3,
                    title= "Ok here we go",
                    description = "asdasdasda"
                )
            )
        )
        val onboardingViewPager= findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode=
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener{
            if (onboardingViewPager.currentItem+1 < onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }
        findViewById<TextView>(R.id.textSkip).setOnClickListener{
            navigateToHomeActivity()
        }
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener{
            navigateToHomeActivity()
        }
    }

    private fun navigateToHomeActivity(){
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }

    private fun setupIndicators(){
        indicatorsContainer= findViewById(R.id.indicatorsContainer)
        val indicators= arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams=
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                   ContextCompat.getDrawable(
                       applicationContext,
                       R.drawable.indicator_inactive_background
                   )
                )
                it.layoutParams= layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount= indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i== position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}