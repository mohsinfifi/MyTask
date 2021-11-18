package com.example.task.utils

import com.example.task.models.Product

class AppConstants {

    companion object {

        const val DB_NAME = "GroceryDatabase"
        const val PRODUCTS_TABLE = "ProductsTable"
        const val CATEGORIES_TABLE = "CategoriesTable"

        val CART_ITEMS: ArrayList<Product> = ArrayList()

    }

}