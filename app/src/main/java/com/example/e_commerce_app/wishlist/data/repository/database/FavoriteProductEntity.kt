package com.example.e_commerce_app.wishlist.data.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "product_name") val name: String
)


