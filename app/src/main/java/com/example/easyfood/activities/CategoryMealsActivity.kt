package com.example.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapters.CategoryMealsAdapter
import com.example.easyfood.databinding.ActivityCategoryMealsBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding :ActivityCategoryMealsBinding
    private lateinit var categoryMvvm : CategoryMealsViewModel
    private lateinit var categoryMealAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMvvm = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMvvm.observeMealsLiveData().observe(this) { mealsList ->
            binding.tvCategoryCount.text = "Total Dishes : ${mealsList!!.size}"
            categoryMealAdapter.setMealsList(mealsList)
        }
    }


    private fun prepareRecyclerView() {
        categoryMealAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply{
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }
}