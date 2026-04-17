package ru.kulishov.application_showcase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kulishov.application_showcase.data.remote.repository.AddRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.AppMetadataRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.CategoryRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.LogoRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.PhotoRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.StoryRepositoryImpl
import ru.kulishov.application_showcase.domain.repository.AddRepository
import ru.kulishov.application_showcase.domain.repository.AppMetadataRepository
import ru.kulishov.application_showcase.domain.repository.CategoryRepository
import ru.kulishov.application_showcase.domain.repository.LogoRepository
import ru.kulishov.application_showcase.domain.repository.PhotoRepository
import ru.kulishov.application_showcase.domain.repository.StoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppMetadataRepository(
        impl: AppMetadataRepositoryImpl
    ): AppMetadataRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindLogoRepository(
        impl: LogoRepositoryImpl
    ): LogoRepository

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(
        impl: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    @Singleton
    abstract fun bindStoryRepository(
        impl: StoryRepositoryImpl
    ): StoryRepository

    @Binds
    @Singleton
    abstract fun bindAddRepository(
        impl: AddRepositoryImpl
    ): AddRepository

}