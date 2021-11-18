package com.example.task.repositories

import com.example.task.models.Product
import com.example.task.models.ProductCategories
import com.example.task.models.Products
import com.example.task.networking.Resource
import com.example.task.remote.ApiRemoteDataSource
import com.example.task.utils.Utility
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ProductsRepository @Inject constructor(
    private val remoteDataSource: ApiRemoteDataSource
) {


    suspend fun getProducts(): Resource<Products> {
        val remoteResponse = remoteDataSource.getProducts()
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    suspend fun getProductCategories(): Resource<ProductCategories> {
        val remoteResponse = remoteDataSource.getProductCategories()
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    suspend fun getProductDetails(productId: String): Resource<Product> {
        val remoteResponse = remoteDataSource.getProductDetails(productId)
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

}