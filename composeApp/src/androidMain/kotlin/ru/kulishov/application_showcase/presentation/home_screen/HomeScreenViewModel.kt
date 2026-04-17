package ru.kulishov.application_showcase.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.model.Story
import ru.kulishov.application_showcase.domain.model.StoryWithPhoto
import ru.kulishov.application_showcase.domain.usecase.add.GetAddUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetAppMetadateUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetCategoryAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetPopularAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.SearchAppUseCase
import ru.kulishov.application_showcase.domain.usecase.category.GetCategoryUseCase
import ru.kulishov.application_showcase.domain.usecase.logo.GetLogoUseCase
import ru.kulishov.application_showcase.domain.usecase.photo.GetPhotoUseCase
import ru.kulishov.application_showcase.domain.usecase.story.GetLastStoriesUseCase
import ru.kulishov.application_showcase.presentation.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchAppUseCase: SearchAppUseCase,
    private val getPopularAppUseCase: GetPopularAppUseCase,
    private val getCategoryAppUseCase: GetCategoryAppUseCase,
    private val getLogoUseCase: GetLogoUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getLastStoriesUseCase: GetLastStoriesUseCase,
    private val getAddUseCase: GetAddUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getAppMetadateUseCase: GetAppMetadateUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _category = MutableStateFlow<List<Category>>(emptyList())
    val category: StateFlow<List<Category>> = _category.asStateFlow()
    private val _apps = MutableStateFlow<List<AppMetadataWithLogo>>(emptyList())
    val apps: StateFlow<List<AppMetadataWithLogo>> = _apps.asStateFlow()

    private val _search_apps = MutableStateFlow<List<AppMetadataWithLogo>>(emptyList())
    val search_apps: StateFlow<List<AppMetadataWithLogo>> = _search_apps.asStateFlow()



//    init {
//        launch {
//            getPopularApps(10)
//            getCategories()
//        }
//    }

    suspend fun getPopularApps(limit: Int) {
        _apps.value = getPopularAppUseCase(limit)
        apps.value.map {
            async {
                val fLogo = getLogoUseCase(it.id)
                if (fLogo != null) {
                    it.logo = fLogo.logo
                    Log.d(null, "addlogo")
                }
            }
        }

    }

    suspend fun getCategories() {
        _category.value = getCategoryUseCase()
    }

     fun getCategoryApp(id:Int) {
         launch {
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

    suspend fun getStories(): List<StoryWithPhoto> {
        var ret = emptyList<StoryWithPhoto>()
        getLastStoriesUseCase().map {
            val photo = getPhotoUseCase(it.photoId)
            val appMetadata = getAppMetadateUseCase(it.appId)
            var appMetadataWithLogo: AppMetadataWithLogo? = null
            if (appMetadata != null) {
                val logo = getLogoUseCase(it.appId)
                if (logo != null) {
                    appMetadataWithLogo = AppMetadataWithLogo(
                        id = appMetadata.id,
                        name = appMetadata.name,
                        category = appMetadata.category,
                        short_description = appMetadata.short_description,
                        grade = appMetadata.grade,
                        logo = logo.logo
                    )
                }
            }
            if (photo != null) {
                ret += StoryWithPhoto(it, photo.data, appMetadataWithLogo)
            } else {
                ret += StoryWithPhoto(it, null, appMetadataWithLogo)
            }

        }
        return ret
    }

    suspend fun getAdds(): List<StoryWithPhoto> {
        var ret = emptyList<StoryWithPhoto>()
        getAddUseCase().map {

            val photo = getPhotoUseCase(it.photoId)
            val appMetadata = getAppMetadateUseCase(it.appId)
            Log.d(null,appMetadata!!.name)
            var appMetadataWithLogo: AppMetadataWithLogo? = null
            if (appMetadata != null) {
                val logo = getLogoUseCase(it.appId)
                if (logo != null) {
                    appMetadataWithLogo = AppMetadataWithLogo(
                        id = appMetadata.id,
                        name = appMetadata.name,
                        category = appMetadata.category,
                        short_description = appMetadata.short_description,
                        grade = appMetadata.grade,
                        logo = logo.logo
                    )
                }
            }
            if (photo != null) {
                ret += StoryWithPhoto(
                    Story(
                        it.id, it.appId, it.photoId, it.createdAt
                    ), photo.data, appMetadataWithLogo
                )
            } else {
                ret += StoryWithPhoto(
                    Story(
                        it.id, it.appId, it.photoId, it.createdAt
                    ), null, appMetadataWithLogo
                )
            }

        }
        return ret
    }


    sealed class HomeScreenUiState {
        object Loading : HomeScreenUiState()
        object Success : HomeScreenUiState()
        data class Error(val e: String) : HomeScreenUiState()
    }
}