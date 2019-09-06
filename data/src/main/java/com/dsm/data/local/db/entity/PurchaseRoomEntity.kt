package com.dsm.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PurchaseRoomEntity(
    @PrimaryKey
    val postId: Int,

    val title: String,

    val img: String,

    val createdAt: String,

    val price: String
)