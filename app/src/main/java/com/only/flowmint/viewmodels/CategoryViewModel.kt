package com.only.flowmint.viewmodels


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.flowmint.MyApp
import com.only.flowmint.data.Category
import com.only.flowmint.data.CategoryState
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryState())
    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    private val db = MyApp.db

    init {
        _uiState.update { currentState ->
            currentState.copy(
                categories = db.query<Category>().find()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.query<Category>().asFlow().collect{ changes->
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = changes.list
                    )
                }
            }
        }
    }

    fun setNewCategoryColor(color: Color){
        _uiState.update { currentState ->
            currentState.copy(
                newCatColor = color
            )
        }
    }

    fun setNewCategoryName(name: String){
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryName = name
            )
        }
    }

    fun showColorPicker(){
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShow = true
            )
        }
    }

    fun hideColorPicker(){
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShow = false
            )
        }
    }

//    fun createOnBoarding() {
//        viewModelScope.launch(Dispatchers.IO) {
//            db.write {
//                this.copyToRealm(Board(
//                    _uiStates.value.Boards
//                ))
//            }
//        }
//    }

    fun createNewCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                this.copyToRealm(Category(
                    _uiState.value.newCategoryName,
                    _uiState.value.newCatColor
                ))
            }
            _uiState.update { currentState ->
                currentState.copy(
                    newCatColor = Color.White,
                    newCategoryName = ""
                )
            }
        }
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                val categoryToDelete = this.query<Category>("id == $0", category.id).find().first()
                delete(categoryToDelete)
            }
        }
    }
}