package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.PopularMealList
import com.example.easyfood.pojo.PopularMeals
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class CategoryMealsViewModel:ViewModel() {
    val mealsLiveData = MutableLiveData <List<PopularMeals>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object :Callback<PopularMealList>{
            override fun onResponse(call: Call<PopularMealList>, response: Response<PopularMealList>) {
                if(response.body() != null){
                    mealsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }

        })
    }
    fun observeMealsLiveData(): LiveData<List<PopularMeals>> {
        return mealsLiveData
    }
}