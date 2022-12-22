package com.example.e_commerce_app.shared.data

import com.example.e_commerce_app.product_detail.domain.ProductDetails
import com.example.e_commerce_app.product_list.domain.Product
import com.example.e_commerce_app.shared.data.repository.ApiResult


interface ProductRepository{
    suspend fun getProductList(): ApiResult<List<Product>>
    suspend fun getProductDetails(productId:String) : ProductDetails
}
