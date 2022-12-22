package com.example.e_commerce_app.product_list.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_app.*
import com.example.e_commerce_app.databinding.FragmentProductListBinding
import com.example.e_commerce_app.product_list.presentation.ProductCardListAdapter
import com.example.e_commerce_app.product_list.presentation.ProductCardViewState
import com.example.e_commerce_app.product_list.presentation.ProductListViewModel
import com.example.e_commerce_app.product_list.presentation.ProductListViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val adapter = ProductCardListAdapter(::onItemClicked,::onFavoriteClicked)



    private lateinit var binding: FragmentProductListBinding

    private val viewModel : ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.viewProductList.adapter = adapter
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUI(viewState)
        }
        viewModel.loadProducts()
    }

    private fun updateUI(viewState: ProductListViewState) {
        when (viewState) {
            is ProductListViewState.Content -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.productList)
            }
            ProductListViewState.Error -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }

    private fun onItemClicked(viewState: ProductCardViewState){
        val bundle = Bundle()
        bundle.putString("productId",viewState.id)
        findNavController().navigate(R.id.action_productListFragment_to_fragmentProductDetails,bundle)
    }

    private fun onFavoriteClicked(productCardViewState: ProductCardViewState) {
        viewModel.favoriteIconClicked(productCardViewState.id)

    }

}