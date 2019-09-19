package com.dsm.data.dataSource.recommend

import com.dsm.data.remote.entity.RecommendListEntity
import io.reactivex.Flowable

interface RecommendDataSource {

    fun getRecommendProduct(postId: Int): Flowable<RecommendListEntity>

    fun getRelatedProduct(postId: Int, type: Int): Flowable<RecommendListEntity>
}