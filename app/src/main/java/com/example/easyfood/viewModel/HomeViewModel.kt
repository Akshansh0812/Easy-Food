package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.*
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularMeals>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

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
}