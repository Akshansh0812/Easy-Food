package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewClass

class MealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var mealMvvm : MealViewClass
    private lateinit var youtubeLink : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewClass::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private fun observerMealDetailsLiveData(){
        mealMvvm.observerMealDetailsLiveData().observe(this
        ) { value ->
            onResponseCase()
            binding.tvCategoryInfo.text = "Category : ${value!!.strCategory}"
            binding.tvAreaInfo.text = "Area : ${value.strArea}"
            binding.tvContent.text = value.strInstructions

            youtubeLink = value.strYoutube
        }
    }
    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

}