package com.example.e_commerce_app.product_detail.domain

data class ProductDetails(
    val title: String,
    val description: String,
    val full_description: String,
    val price: String,
    val imageUrl : String,
    val pros : List<String>,
    val cons : List<String>
)
