package com.example.e_commerce_app.product_list.presentation


sealed class ProductListViewState {
    object Loading : ProductListViewState()
    object Error : ProductListViewState()
    data class Content(val productList: List<ProductCardViewState>) : ProductListViewState()
}

fun ProductListViewState.Content.updateFavoriteProduct(
    productId: String,
    isFavorite: Boolean
): ProductListViewState.Content {
    return ProductListViewState.Content(productList = this.productList.map { viewState ->
        if (viewState.id == productId) {
            viewState.copy(isFavorite = isFavorite)
        } else {
            viewState
        }
    }
    )
}
