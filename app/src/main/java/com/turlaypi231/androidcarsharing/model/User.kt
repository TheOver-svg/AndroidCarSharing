package com.turlaypi231.androidcarsharing.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long? = null,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,
    val password: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)