package com.example.e_commerce_app.di

import android.content.Context
import androidx.room.Room
import com.example.e_commerce_app.shared.data.repository.ApiClient
import com.example.e_commerce_app.shared.data.ProductRepository
import com.example.e_commerce_app.shared.data.repository.ProductRepositoryImpl
import com.example.e_commerce_app.shared.data.repository.ProductService
import com.example.e_commerce_app.wishlist.data.repository.WishlistRepository
import com.example.e_commerce_app.wishlist.data.repository.WishlistRepositoryImpl
import com.example.e_commerce_app.wishlist.data.repository.database.AppDatabase
import com.example.e_commerce_app.wishlist.data.repository.database.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Provides
    fun provideProductService() : ProductService = ApiClient.getService()

    @Provides
    fun provideproductRepositoryImpl(
        service: ProductService
    ): ProductRepositoryImpl = ProductRepositoryImpl(service)

    @Provides
    fun provideProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository = productRepositoryImpl

    @Provides
    fun provideWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ) : WishlistRepository = wishlistRepositoryImpl


    @Provides
    fun provideWishlistRepositoryImpl(
        wishlistDao: WishlistDao
    ):WishlistRepositoryImpl = WishlistRepositoryImpl(wishlistDao)

    @Provides
    fun provideWishlistDao(@ApplicationContext context:Context):WishlistDao{
        val instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ecommerce_database"
        ).build()
        return instance.wishListDao()
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher{
        return Dispatchers.Main
    }
}