package com.example.e_commerce_app.wishlist.data.repository

interface WishlistRepository {
   suspend fun isFavorite (productId:String) : Boolean
   suspend fun addToWishlist(productId: String)
   suspend fun deleteFromWishlist(productId: String)
}