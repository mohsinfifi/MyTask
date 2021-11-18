package com.example.task.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.adapters.CategoriesAdapter
import com.example.task.adapters.ProductsAdapter
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.models.Product
import com.example.task.networking.Resource
import com.example.task.utils.AppConstants.Companion.CART_ITEMS
import com.example.task.utils.Utility
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), ProductsAdapter.OnItemClickListener,
    CategoriesAdapter.OnItemClickListener {

    private val mViewModel: ProductsViewModel by viewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var productCategories: ArrayList<String> = ArrayList()
    private var products: ArrayList<Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesAdapter = CategoriesAdapter(this)
        productsAdapter = ProductsAdapter(this)
        mBinding.rvCategories.adapter = categoriesAdapter
        mBinding.rvProducts.adapter = productsAdapter

        if (Utility.isNetWorkAvailable(requireContext())) {
            mViewModel.getProductCategories()
            mViewModel.getProducts()
        } else {
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }

        mViewModel.productCategoriesResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { categoriesList ->
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.rvCategories.visibility = View.VISIBLE
                        if (categoriesList.size > 0) {
                            productCategories.clear()
                            productCategories.add("All")
                            productCategories.addAll(categoriesList)
                            categoriesAdapter.submitList(productCategories)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mViewModel.productsResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { productsList ->
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.rvProducts.visibility = View.VISIBLE
                        products.clear()
                        products.addAll(productsList)
                        productsAdapter.submitList(products)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    /*  private fun filterSelectedCategory(categoryName: String) {
          if (products.isNullOrEmpty()) {
              if (categoryName == "All") {
                  mAdapter.currentList.clear()
                  mAdapter.submitList(products)
              } else {
                  val filteredProducts = products.filter { it.category == categoryName }
                  Toast.makeText(requireContext(), filteredProducts.size, Toast.LENGTH_SHORT)
                      .show()
                  mAdapter.currentList.clear()
                  mAdapter.submitList(filteredProducts)
              }

          }
      }*/

    override fun onItemClick(product: Product) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(product.id.toString())
        )
    }

    @SuppressLint("SetTextI18n")
    override fun addToCart(product: Product) {
        CART_ITEMS.add(product)
        mBinding.tvCartDetails.text = "You have ${CART_ITEMS.size} items in the Cart"
    }


    override fun onItemClick(category: String) {

        if (products.isNotEmpty()) {
            if (category == "All") {
                productsAdapter.submitList(products)
            } else {
                val filteredProducts = products.filter { it.category == category }
                productsAdapter.submitList(filteredProducts)
            }

        }
    }


}