package com.example.e_commerce_app.product_list.presentation

import org.junit.Test

class ProductListViewStateTest {

    @Test
    fun `correct product view state is updated` ()
    {
        val content = ProductListViewState.Content(
            productList = (0..9).map {
                ProductCardViewState(
                    "id$it",
                    "",
                    "",
                    "",
                    "",
                    false
                )
            }
        )
       val result = content.updateFavoriteProduct("id5",true)
       assert(result.productList[5].isFavorite)
       assert(!result.productList[4].isFavorite)
    }
}