package com.devj.dcine.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val marvelSource: CharacterPagingSource) {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PREFETCH_DISTANCE = 3
    }

    fun getMarvelCharacters(): Flow<PagingData<MarvelCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { marvelSource }).flow
    }

}