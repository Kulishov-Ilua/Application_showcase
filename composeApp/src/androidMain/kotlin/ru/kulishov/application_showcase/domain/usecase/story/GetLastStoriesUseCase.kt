package ru.kulishov.application_showcase.domain.usecase.story

import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.model.Story
import ru.kulishov.application_showcase.domain.repository.PhotoRepository
import ru.kulishov.application_showcase.domain.repository.StoryRepository
import javax.inject.Inject

class GetLastStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(): List<Story> = repository.getLastStories()
}