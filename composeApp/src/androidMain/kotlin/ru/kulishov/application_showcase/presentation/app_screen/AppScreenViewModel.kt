package ru.kulishov.application_showcase.presentation.app_screen

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kulishov.application_showcase.domain.model.App
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.usecase.app.GetAppUseCase
import ru.kulishov.application_showcase.domain.usecase.category.GetCategoryUseCase
import ru.kulishov.application_showcase.domain.usecase.logo.GetLogoUseCase
import ru.kulishov.application_showcase.domain.usecase.photo.GetAppPhotos
import ru.kulishov.application_showcase.domain.usecase.photo.GetPhotoUseCase
import ru.kulishov.application_showcase.presentation.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AppScreenViewModel @Inject constructor(
    private val getAppUseCase: GetAppUseCase,
    private val getAppPhotos: GetAppPhotos,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getLogoUseCase: GetLogoUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<AppScreenUiState>(AppScreenUiState.LoadingPhoto)
    val uiState: StateFlow<AppScreenUiState> = _uiState.asStateFlow()

    private val _photo = MutableStateFlow<List<Photo>>(emptyList())
    val photo: StateFlow<List<Photo>> = _photo.asStateFlow()
    private val _app = MutableStateFlow<AppWithLogo>(
        AppWithLogo(
            App(
                id = -1,
                name = "",
                author = "",
                category = -1,
                grade = 0.0f,
                gradesCount = 0,
                age = 0,
                description = "",
                short_description = ""
            )
        )
    )
    val app: StateFlow<AppWithLogo> = _app.asStateFlow()

    fun setApp(nApp: AppMetadataWithLogo){
        launch {
            _uiState.value= AppScreenUiState.Loading
            _app.value.app.name=nApp.name
            _app.value.app.id=nApp.id
            _app.value.app.category=nApp.category
            _app.value.app.short_description=nApp.short_description
            _app.value.app.grade=nApp.grade
            _app.value.logo=nApp.logo
            _photo.value=emptyList()

            _uiState.value= AppScreenUiState.LoadingData
            getApp()
            _uiState.value= AppScreenUiState.LoadingPhoto
            getFhotos()
            _uiState.value= AppScreenUiState.Success

        }
    }

    fun refreshApp(){
        launch {
            _uiState.value= AppScreenUiState.Loading
            val fApp = getAppUseCase(_app.value.app.id)
            if (fApp==null){
                _uiState.value= AppScreenUiState.Error("404")
            }else{
                _app.value.app=fApp.app
                val fLogo = getLogoUseCase(_app.value.app.id)
                if(fLogo!=null){
                    _app.value.logo=fLogo.logo
                }
                val categories = getCategoryUseCase()
                val fCategory= categories.find { it.id==_app.value.app.category }
                if(fCategory!=null){
                    _app.value.category=fCategory
                }
                _uiState.value= AppScreenUiState.LoadingPhoto
                getFhotos()
                _uiState.value= AppScreenUiState.Success

            }

        }
    }
    fun setId(id:Int){
        _app.value.app.id=id
    }
    suspend fun getFhotos(){
        _photo.value=emptyList()
        getAppPhotos(_app.value.app.id).map {

            val fPhoto = getPhotoUseCase(it)
            if(fPhoto!=null&&_photo.value.find { it.id==fPhoto.id }==null){
                _photo.value+=fPhoto
            }
        }

    }
    suspend fun getApp() {

            val fApp = getAppUseCase(_app.value.app.id)
            if (fApp != null) {
                _app.value.app.age = fApp.app.age
                _app.value.app.author = fApp.app.author
                _app.value.app.gradesCount = fApp.app.gradesCount
                _app.value.app.description = fApp.app.description
                val categories = getCategoryUseCase()
                val fCategory= categories.find { it.id==_app.value.app.category }
                if(fCategory!=null){
                    _app.value.category=fCategory
                }
            }
    }

    fun closePhotoScreen(){
        _uiState.value= AppScreenUiState.Success
    }

    fun openPhotoScreen(){
        _uiState.value= AppScreenUiState.Photo
    }
    sealed class AppScreenUiState {
        object Loading : AppScreenUiState()

        object LoadingData : AppScreenUiState()
        object LoadingPhoto : AppScreenUiState()
        object Success : AppScreenUiState()
        object Photo: AppScreenUiState()
        data class Error(val e: String) : AppScreenUiState()
    }
}