package com.example.e_commerce_app.wishlist.domain

import com.example.e_commerce_app.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class AddOrRemoveFromWishListUseCase @Inject constructor(
    private val isProductInWishlistUseCase: IsProductInWishlistUseCase,
    private val wishlistRepository: WishlistRepository

) {
    suspend fun execute(productId:String){
        if(isProductInWishlistUseCase.execute(productId)){
            wishlistRepository.deleteFromWishlist(productId)
        }
        else{
            wishlistRepository.addToWishlist(productId)
        }
    }
}