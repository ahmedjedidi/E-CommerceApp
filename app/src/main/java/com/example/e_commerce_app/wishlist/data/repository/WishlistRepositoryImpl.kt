package com.example.e_commerce_app.wishlist.data.repository

import com.example.e_commerce_app.wishlist.data.repository.database.FavoriteProductEntity
import com.example.e_commerce_app.wishlist.data.repository.database.WishlistDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {
    override suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO){
            wishlistDao.isProductFavorite(productId) != null
        }


    }

    override suspend fun addToWishlist(productId: String) {
        return withContext(Dispatchers.IO){
            wishlistDao.addProductToFavorite(FavoriteProductEntity(productId,""))
        }

    }

    override suspend fun deleteFromWishlist(productId: String) {
        return withContext(Dispatchers.IO){
            wishlistDao.deleteProductFromFavorite(FavoriteProductEntity(productId,""))
        }

    }
}