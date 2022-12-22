package com.example.e_commerce_app.product_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.e_commerce_app.product_list.domain.Product
import com.example.e_commerce_app.shared.data.ProductRepository
import com.example.e_commerce_app.shared.data.repository.ApiResult
import com.example.e_commerce_app.wishlist.domain.AddOrRemoveFromWishListUseCase
import com.example.e_commerce_app.wishlist.domain.IsProductInWishlistUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class ProductListViewModelTest{

    @get:Rule
    var rule : TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var ViewModel:ProductListViewModel
    private  val repository = mockk<ProductRepository>()
    private val addOrRemoveFromWishListUseCase = mockk<AddOrRemoveFromWishListUseCase>()
    private val isProductInWishlistUseCase=mockk<IsProductInWishlistUseCase>()

    val listProduct= (0..2).map {
        Product(
            "id-$it","","",6.0,""
        )
    }

    @Before
    fun setup(){
    coEvery {
        isProductInWishlistUseCase.execute(any())
    } returns false
    coEvery {
        isProductInWishlistUseCase.execute("id-1")
    } returns true

    coEvery {
        repository.getProductList()
    } returns ApiResult.Success(listProduct)

        ViewModel = ProductListViewModel(
            repository,
            addOrRemoveFromWishListUseCase,
            isProductInWishlistUseCase,dispatcher)
    }

    @Test
    fun `load method correctly creates the view state`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        ViewModel.viewState.observeForever {
            values.add(it)
        }
        ViewModel.loadProducts()
        dispatcher.scheduler.advanceUntilIdle()

        assert(values[0] is ProductListViewState.Loading)

        assert(values[1] ==
                ProductListViewState.Content(
                    (0..2).map {
                        ProductCardViewState(
                            "id-$it",
                            "",
                            "",
                            "$ usd 6.0",
                            "",
                            it == 1
                        )
                    }
                ))
    }
}