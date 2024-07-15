package com.devj.dcine.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val api: MarvelApi) : PagingSource<Int,MarvelCharacter>() {
    val NETWORK_PAGE_SIZE = 30
    private val INITIAL_LOAD_SIZE = 1

    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        return try {

            val position = params.key ?: INITIAL_LOAD_SIZE
            val offset = if (params.key != null) ((position) * NETWORK_PAGE_SIZE)  else INITIAL_LOAD_SIZE


            val limit = params.loadSize
            val response = api.getMarvelCharacters(offset = offset, limit = limit)

            val nextKey = if (response.data.results.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }



            LoadResult.Page(prevKey = null, nextKey = nextKey, data = response.data.results.filter { !it.thumbnail.path.endsWith("image_not_available") } )

        }catch (e: Exception){
           LoadResult.Error(e)
        }
    }

}