package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.Story

interface StoryRepository {
    suspend fun getLastStories(): List<Story>
}