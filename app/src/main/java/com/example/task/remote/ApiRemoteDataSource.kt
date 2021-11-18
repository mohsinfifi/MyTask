package com.example.task.remote

import com.example.task.networking.ApiService
import com.example.task.networking.BaseDataSource
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun getProducts() = getResult {
        apiService.getProducts()
    }

    suspend fun getProductCategories() = getResult {
        apiService.getProductCategories()
    }

    suspend fun getProductDetails(productId: String) = getResult {
        apiService.getProductDetails(productId)
    }

}