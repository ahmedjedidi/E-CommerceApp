package com.example.e_commerce_app.product_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_app.shared.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor (val repository:ProductRepository) : ViewModel() {
    private val _viewState = MutableLiveData<ProductDetailViewState>()
    val viewState:LiveData<ProductDetailViewState>
    get() = _viewState

    fun loadProduct(productId:String){
        viewModelScope.launch {
            _viewState.postValue(ProductDetailViewState.Loading)
            val product = repository.getProductDetails(productId)
            _viewState.postValue(ProductDetailViewState.Content(product))
        }
    }
}