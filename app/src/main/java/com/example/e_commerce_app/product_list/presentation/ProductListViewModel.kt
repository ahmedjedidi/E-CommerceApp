package com.example.e_commerce_app.product_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_app.shared.data.ProductRepository
import com.example.e_commerce_app.shared.data.repository.ApiResult
import com.example.e_commerce_app.wishlist.data.repository.WishlistRepository
import com.example.e_commerce_app.wishlist.data.repository.database.FavoriteProductEntity
import com.example.e_commerce_app.wishlist.domain.AddOrRemoveFromWishListUseCase
import com.example.e_commerce_app.wishlist.domain.IsProductInWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private  val repository : ProductRepository,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase,
    private val isProductInWishlistUseCase: IsProductInWishlistUseCase,
    private val dispatcher : CoroutineDispatcher = Dispatchers.Main
    ) : ViewModel() {

   private val _viewState = MutableLiveData<ProductListViewState>()
   val viewState : LiveData<ProductListViewState>
   get() = _viewState


    fun loadProducts(){
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(ProductListViewState.Loading)
            val result = repository.getProductList()
            when (result){
                is ApiResult.Failure -> {
                    _viewState.postValue(ProductListViewState.Error)
                }
                is ApiResult.Success -> {
                    val productListViewState= result.data.map { product ->
                        ProductCardViewState(
                            id= product.id,
                            title = product.title,
                            description = product.description,
                            price = "$ usd ${product.price}",
                            imageUrl = product.imageUrl,
                            isFavorite = isProductInWishlistUseCase.execute(product.id)
                        )
                    }
                    _viewState.postValue(ProductListViewState.Content(productListViewState))

                }
            }
        }

    }

    fun favoriteIconClicked(productId:String){
        viewModelScope.launch {
            addOrRemoveFromWishListUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.postValue(
                    content.updateFavoriteProduct(
                        productId,
                        isProductInWishlistUseCase.execute(productId)
                    )
                )

            }
        }
    }
}