package com.devj.dcine.presenter

import android.content.res.Resources
import android.icu.number.Scale
import android.util.Log
import android.widget.ImageView.ScaleType
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.devj.dcine.R
import com.devj.dcine.data.MarvelCharacter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val characterList = homeViewModel.marvelCharacters.collectAsLazyPagingItems()

    Scaffold(
      topBar = {
        TopAppBar(title = { Text("Universo de Marvel") })
      }
    ) { paddingValues ->

        when{
            characterList.loadState.refresh is LoadState.Loading ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                    )
                }
            characterList.loadState.hasError -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error")
            }
            else -> {
                CharacterList(
                    characterList = characterList,
                    modifier = Modifier.padding(paddingValues)
                )

                if(characterList.loadState.append is LoadState.Loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            }
        }

    }


}

@Composable
fun CharacterList(characterList: LazyPagingItems<MarvelCharacter>, modifier: Modifier = Modifier) {
    val width = LocalConfiguration.current.screenWidthDp

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive( (width * 0.45).dp),
        contentPadding = PaddingValues(8.dp)
        ) {
        items(characterList.itemCount){ index ->
            characterList[index]?.let {
               if ( it.thumbnail.path.endsWith("image_not_available") ) return@items
                CharacterItem(character = it)
            }
        }
    }
}

@Composable
fun CharacterItem(character: MarvelCharacter) {
    val url = "${character.thumbnail.path}.${character.thumbnail.extension}"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            AsyncImage(
                model = url,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                onError = { error ->
                    Log.e("CharacterItem", "Error loading image: $error")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(2.dp)
                    .clip(MaterialTheme.shapes.small)


            )
        }
        Text(
            text = character.name,
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                .padding(2.dp)
        )
    }


}
