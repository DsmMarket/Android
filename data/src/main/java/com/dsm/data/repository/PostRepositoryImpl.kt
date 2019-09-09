package com.dsm.data.repository

import com.dsm.data.dataSource.post.PostDataSource
import com.dsm.data.mapper.PostCategoryMapper
import com.dsm.domain.entity.PostCategory
import com.dsm.domain.repository.PostRepository
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class PostRepositoryImpl(private val postDataSource: PostDataSource) : PostRepository {

    private val postCategoryMapper = PostCategoryMapper()

    override fun getPostCategory(): Flowable<List<PostCategory>> =
        postDataSource.getPostCategory().map { postCategoryMapper.mapFrom(it.category) }

    override fun postRent(img: MultipartBody.Part, params: Map<String, RequestBody>): Flowable<Unit> =
        postDataSource.postRent(img, params).map {
            if (it.code() != 200) throw HttpException(it)
        }

    override fun postPurchase(img: List<MultipartBody.Part>, params: Map<String, RequestBody>): Flowable<Unit> =
        postDataSource.postPurchase(img, params).map {
            if (it.code() != 200) throw HttpException(it)
        }
}