package com.dsm.dsmmarketandroid.presentation.model

sealed class PostCategoryModel {
    data class ParentCategory(val category: String): PostCategoryModel()

    data class ChildCategory(val category: String, val parent: String): PostCategoryModel()
}