package com.example.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adapters.CategoriesAdapter
import com.example.easyfood.adapters.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.CategoryList
import com.example.easyfood.pojo.PopularMeals
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    companion object{
        const val MEAL_ID = "com.example.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.example.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularMeals()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesMeals()

    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesMeals() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) { categories ->
            categoriesAdapter.setCategoryList(categories as ArrayList<Category>)

        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularMeals() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner,
        ){mealList->
                popularItemsAdapter.setMeals(mealList as ArrayList<PopularMeals>)
        }
    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun observerRandomMeal(){
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)

                randomMeal = value
            }
        })
    }
}
