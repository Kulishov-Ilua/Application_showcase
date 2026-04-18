package ru.kulishov.application_showcase.presentation.category_screen

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetCategoryAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetPopularAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.SearchAppUseCase
import ru.kulishov.application_showcase.domain.usecase.category.GetCategoryUseCase
import ru.kulishov.application_showcase.domain.usecase.logo.GetLogoUseCase
import ru.kulishov.application_showcase.presentation.BaseViewModel
import ru.kulishov.application_showcase.presentation.home_screen.HomeScreenViewModel.HomeScreenUiState
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getCategoryAppUseCase: GetCategoryAppUseCase,
    private val getPopularAppUseCase: GetPopularAppUseCase,
    private val getLogoUseCase: GetLogoUseCase,
    private val searchAppUseCase: SearchAppUseCase
): BaseViewModel(){
    private val _uiState = MutableStateFlow<CategoryScreenUiState>(CategoryScreenUiState.Loading)
    val uiState: StateFlow<CategoryScreenUiState> = _uiState.asStateFlow()

    private val _category = MutableStateFlow<List<Category>>(emptyList())
    val category: StateFlow<List<Category>> = _category.asStateFlow()
    private val _apps = MutableStateFlow<List<AppMetadataWithLogo>>(emptyList())
    val apps: StateFlow<List<AppMetadataWithLogo>> = _apps.asStateFlow()

    private val _search_apps = MutableStateFlow<List<AppMetadataWithLogo>>(emptyList())
    val search_apps: StateFlow<List<AppMetadataWithLogo>> = _search_apps.asStateFlow()


    init {
        launch {
            _uiState.value= CategoryScreenUiState.Loading
            getCategories()
            _uiState.value= CategoryScreenUiState.Success
        }

    }
    fun openCategory(id:Int){
        launch {
            _uiState.value= CategoryScreenUiState.LoadingApps
            getCategoryApp(id)
            _uiState.value= CategoryScreenUiState.Apps
        }
    }

    fun openCategoryList(){
        launch {
            _uiState.value= CategoryScreenUiState.Loading
            getCategories()
            _uiState.value= CategoryScreenUiState.Success
        }
    }
     suspend fun getCategories() {
             _category.value = getCategoryUseCase()
    }

    suspend fun getCategoryApp(id:Int) {
            if(id>0){
                _apps.value = getCategoryAppUseCase(id, 10)
                apps.value.map {
                    async {
                        val fLogo = getLogoUseCase(it.id)
                        if (fLogo != null) {
                            it.logo = fLogo.logo
                        }
                    }
                }
            }else{
                _apps.value = getPopularAppUseCase( 10)
                apps.value.map {
                    async {
                        val fLogo = getLogoUseCase(it.id)
                        if (fLogo != null) {
                            it.logo = fLogo.logo
                        }
                    }
                }
            }

    }


    fun searchApps(text: String) {
        launch {
            _search_apps.value = emptyList()
            _search_apps.value = searchAppUseCase(text)
            //Log.d(null,_search_apps.value.toString())

            _search_apps.value.map {
                async {
                    val fLogo = getLogoUseCase(it.id)
                    if (fLogo != null) {
                        it.logo = fLogo.logo
                    }
                }
            }
        }
    }

    sealed class CategoryScreenUiState {
        object Loading : CategoryScreenUiState()
        object LoadingApps : CategoryScreenUiState()
        object Apps : CategoryScreenUiState()
        object Success : CategoryScreenUiState()
        data class Error(val e: String) : CategoryScreenUiState()
    }
}