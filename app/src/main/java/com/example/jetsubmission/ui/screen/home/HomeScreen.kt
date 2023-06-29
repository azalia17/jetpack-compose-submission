package com.example.jetsubmission.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetsubmission.di.Injection
import com.example.jetsubmission.model.Book
import com.example.jetsubmission.model.BooksData
import com.example.jetsubmission.ui.ViewModelFactory
import com.example.jetsubmission.ui.common.UiState
import com.example.jetsubmission.ui.component.BookItem
import com.example.jetsubmission.ui.component.SearchBar
import com.example.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks()
            }
            is UiState.Success -> {
                HomeContent(book = uiState.data, navigateToDetail = navigateToDetail, modifier = modifier, query = query, queryChange = viewModel::search)
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    book: List<Book>,
    modifier: Modifier = Modifier,
    query: String,
    navigateToDetail: (Int) -> Unit,
    queryChange: (String) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = queryChange)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(book) {
                BookItem(
                    image = it.photo,
                    title = it.title,
                    price = it.price,
                    modifier = Modifier.clickable {
                        navigateToDetail(it.id)
                    }
                )
            }
        }
    }


}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreenPreview() {
    JetSubmissionTheme {
        HomeContent(book = BooksData.books, navigateToDetail = {}, query = "", queryChange = {})
    }
}