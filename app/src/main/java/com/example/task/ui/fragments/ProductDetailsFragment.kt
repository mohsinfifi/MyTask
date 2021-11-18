package com.example.task.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.task.databinding.FragmentProductDetailsBinding
import com.example.task.networking.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val mViewModel: ProductsViewModel by viewModels()
    private lateinit var mBinding: FragmentProductDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var productId = ""
        arguments?.let { productId = ProductDetailsFragmentArgs.fromBundle(it).productId }
        mViewModel.getProductDetails(productId)
        mViewModel.productResponse.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Resource.Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { product ->
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.clMain.visibility = View.VISIBLE
                        product.let {
                            Glide.with(mBinding.ivProductImage).load(it.image)
                                .into(mBinding.ivProductImage)
                            mBinding.tvProductTitle.text = "Title : ${it.title}"
                            mBinding.tvProductPrice.text = "Price : ${it.price}$"
                            mBinding.tvProductCategory.text = "Category : ${it.category}"
                            mBinding.tvProductDescription.text = "Description : ${it.description}"
                            mBinding.tvProductRating.text = "Product Rating : ${it.rating.rate}"
                        }

                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


}