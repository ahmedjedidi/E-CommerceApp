package com.example.e_commerce_app.product_detail.presentation

import com.example.e_commerce_app.product_detail.domain.ProductDetails

sealed class ProductDetailViewState {
    object Loading : ProductDetailViewState()
    object Error : ProductDetailViewState()
    data class Content(val productDetail:ProductDetails):ProductDetailViewState()


}
