package com.only.flowmint.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

data class AuthState(
    val authEmail: String = "",
    val authProfile: String = "",
    val categories: List<Authentication> = listOf()
)

class Authentication() : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    var email: String = ""
    var profile: String = ""

    constructor(
        email: String,
        profile: String
    ) : this() {
        this.email = email
        this.profile = profile
    }
}


