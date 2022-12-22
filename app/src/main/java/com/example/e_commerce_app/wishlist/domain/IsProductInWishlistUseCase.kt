package com.example.e_commerce_app.wishlist.domain

import com.example.e_commerce_app.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class IsProductInWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId:String): Boolean{
     return wishlistRepository.isFavorite(productId)
    }
}