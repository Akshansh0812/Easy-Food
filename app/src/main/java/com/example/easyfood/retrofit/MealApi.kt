package com.example.easyfood.retrofit

import com.example.easyfood.pojo.CategoryList
import com.example.easyfood.pojo.PopularMealList
import com.example.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String) : Call<PopularMealList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c")categoryName: String):Call<PopularMealList>

    @GET("search.php")
    fun searchMeals(@Query("s")searchQuery: String):Call<MealList>
}