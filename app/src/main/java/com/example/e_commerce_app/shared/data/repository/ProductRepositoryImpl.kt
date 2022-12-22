package com.example.e_commerce_app.shared.data.repository

import com.example.e_commerce_app.product_detail.domain.ProductDetails
import com.example.e_commerce_app.product_list.domain.Product
import com.example.e_commerce_app.product_list.presentation.ProductCardViewState
import com.example.e_commerce_app.shared.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val service: ProductService) :
    ProductRepository {

    override suspend fun getProductList(): ApiResult<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(
                service.getListProduct().map {
                    Product(
                        id= it.id,
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        imageUrl = it.imageUrl
                    )
                })
            }
            catch (exception:Exception){
                 ApiResult.Failure(exception)
            }

        }
    }

    override suspend fun getProductDetails(productId: String): ProductDetails {
        return withContext(Dispatchers.IO){
            service.getDetailsProduct(productId).run {
                ProductDetails(
                    this.title,
                    this.description,
                    this.full_description,
                    "US $ ${this.price}",
                    this.imageUrl,
                    this.pros,
                    this.cons
                )
            }
        }
    }
}