package com.example.task.networking

import com.example.task.models.Product
import com.example.task.models.ProductCategories
import com.example.task.models.Products
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET(RestConfig.PRODUCTS_KEY)
    suspend fun getProducts(): Response<Products>

    @GET("${RestConfig.PRODUCTS_KEY}/${RestConfig.PRODUCT_CATEGORIES_KEY}")
    suspend fun getProductCategories(): Response<ProductCategories>

    @GET("${RestConfig.PRODUCTS_KEY}/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String): Response<Product>


}