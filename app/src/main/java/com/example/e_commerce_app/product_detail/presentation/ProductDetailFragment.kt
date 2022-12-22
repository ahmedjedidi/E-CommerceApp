package com.example.e_commerce_app.product_detail.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.e_commerce_app.R
import com.example.e_commerce_app.databinding.FragmentProductDetailsBinding
import com.example.e_commerce_app.databinding.FragmentProductListBinding
import com.example.e_commerce_app.product_list.presentation.ProductListViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
   private lateinit var binding : FragmentProductDetailsBinding
   private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("productId")?.let {
            viewModel.loadProduct(it)
            viewModel.viewState.observe(viewLifecycleOwner){ viewState ->
                updateUI(viewState)
            }

            Log.d("productId", it)
        }
    }

    fun updateUI(viewState: ProductDetailViewState){
        when (viewState) {
            is ProductDetailViewState.Content -> {
                binding.loadingView.isVisible = false
                val product= viewState.productDetail
                binding.viewProductTitle.text = product.title
                binding.viewPrice.text = product.price
                binding.viewShortDescription.text = product.description
                binding.viewFullDescription.text = product.full_description
                Glide.with(requireContext()).load(product.imageUrl).into(binding.viewProductImage)
            }
            ProductDetailViewState.Error -> {
                binding.loadingView.isVisible = false
            }
            ProductDetailViewState.Loading -> {
                binding.loadingView.isVisible = true
            }
        }
    }
}