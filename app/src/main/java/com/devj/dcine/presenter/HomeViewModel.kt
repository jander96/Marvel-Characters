package com.devj.dcine.presenter

import androidx.lifecycle.ViewModel
import com.devj.dcine.data.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

        val marvelCharacters = marvelRepository.getMarvelCharacters()


}