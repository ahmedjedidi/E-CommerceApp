package com.example.e_commerce_app.wishlist.data.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishlistDao {

    @Query("SELECT * FROM FavoriteProductEntity WHERE id=:id")
    fun isProductFavorite(id:String):FavoriteProductEntity?


    @Insert
    fun addProductToFavorite(product:FavoriteProductEntity)

    @Delete
    fun deleteProductFromFavorite(product: FavoriteProductEntity)
}