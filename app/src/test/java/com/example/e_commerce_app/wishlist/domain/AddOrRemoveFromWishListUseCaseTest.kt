package com.example.e_commerce_app.wishlist.domain

import com.example.e_commerce_app.wishlist.data.repository.WishlistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddOrRemoveFromWishListUseCaseTest {

    private val isProductInWishlistUseCase  = mockk<IsProductInWishlistUseCase>()
    private val wishlistRepository = mockk<WishlistRepository>(relaxed = true)
    private lateinit var useCase : AddOrRemoveFromWishListUseCase


    @Before
    fun setup(){
        useCase = AddOrRemoveFromWishListUseCase(
            isProductInWishlistUseCase,
            wishlistRepository
        )
    }


    @Test
    fun `product is not in the wishlist so add method is called`() = runTest {
        coEvery {
            isProductInWishlistUseCase.execute(any())
        } returns false
        useCase.execute("12")
        coVerify { wishlistRepository.addToWishlist("12") }
    }

    @Test
    fun `product is in the wishlist so delete method is called`() = runTest {
        coEvery {
            isProductInWishlistUseCase.execute(any())
        } returns true
        useCase.execute("12")
        coVerify { wishlistRepository.deleteFromWishlist("12") }
    }
}