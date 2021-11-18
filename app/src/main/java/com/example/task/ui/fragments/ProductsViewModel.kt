package com.example.task.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.models.Product
import com.example.task.models.ProductCategories
import com.example.task.models.Products
import com.example.task.networking.Resource
import com.example.task.repositories.ProductsRepository
import com.example.task.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    var productsResponse: MutableLiveData<Resource<Products>> =
        MutableLiveData<Resource<Products>>()

    var productCategoriesResponse: MutableLiveData<Resource<ProductCategories>> =
        MutableLiveData<Resource<ProductCategories>>()

    var productResponse: MutableLiveData<Resource<Product>> =
        MutableLiveData<Resource<Product>>()


    fun getProducts(): LiveData<Resource<Products>> {
        viewModelScope.launch {
            try {
                productsResponse.value = repository.getProducts()
            } catch (ex: Exception) {
                productsResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
        return productsResponse
    }

    fun getProductCategories(): LiveData<Resource<ProductCategories>> {
        viewModelScope.launch {
            try {
                productCategoriesResponse.value = repository.getProductCategories()
            } catch (ex: Exception) {
                productCategoriesResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
        return productCategoriesResponse
    }


    fun getProductDetails(productId: String): LiveData<Resource<Product>> {
        viewModelScope.launch {
            try {
                productResponse.value = repository.getProductDetails(productId)
            } catch (ex: Exception) {
                productResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
        return productResponse
    }


}