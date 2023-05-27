package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.pojo.*
import com.example.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularMeals>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favouriteMealsLiveData = mealDatabase.mealDao().getAllMeals()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call : Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    randomMealLiveData.value= response.body()!!.meals[0]
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable){
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("SeaFood").enqueue(object : Callback<PopularMealList> {
            override fun onResponse(call: Call<PopularMealList>, response: Response<PopularMealList>) {
                if(response.body() != null){
                    popularItemsLiveData.value= response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                    categoriesLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }
    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemsLiveData(): LiveData<List<PopularMeals>>{
        return popularItemsLiveData
    }
    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }
    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{
        return favouriteMealsLiveData
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

}