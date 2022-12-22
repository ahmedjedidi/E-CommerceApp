package com.example.e_commerce_app.shared.data.repository

import com.example.e_commerce_app.product_detail.data.ProductDetailsEntity
import com.example.e_commerce_app.product_list.data.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getListProduct(): List<ProductEntity>

    @GET("productDetails")
    suspend fun getDetailsProduct(@Query("productId") productId: String): ProductDetailsEntity
}