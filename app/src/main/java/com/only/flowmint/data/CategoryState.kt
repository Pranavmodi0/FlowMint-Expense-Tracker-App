package com.only.flowmint.data

import androidx.compose.ui.graphics.Color
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


data class CategoryState(
    val newCatColor: Color = Color.White,
    val newCategoryName: String = "",
    val colorPickerShow: Boolean = false,
    val categories: List<Category> = listOf()
)

class Category() : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()

    private var colorValue: String = "0,0,0"
    var name: String = ""
    val color: Color
        get() {
            val colorComponents = colorValue.split(",")
            val (red, green, blue) = colorComponents
            return Color(red.toFloat(), green.toFloat(), blue.toFloat())
        }

    constructor(
        name: String,
        color: Color
    ) : this() {
        this.name = name
        this.colorValue = "${color.red}, ${color.green}, ${color.blue}"
    }
}
