package com.example.jetsubmission.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetsubmission.R
import com.example.jetsubmission.di.Injection
import com.example.jetsubmission.ui.ViewModelFactory
import com.example.jetsubmission.ui.common.UiState
import com.example.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun DetailScreen(
    bookId: Int,
    viewModel: DetailBookViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getBookById(bookId = bookId)
            }
            is UiState.Success -> {
                val data = it.data
                DetailContent(
                    image = data.photo,
                    title = data.title,
                    author = data.author,
                    id = data.id,
                    genre = data.genres,
                    price = data.price,
                    detail = data.detail,
                    pages = data.pages,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateBook(id, state)
                    }
                )
            }
            is UiState.Error -> {
                Text(text = it.errorMessage)
            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    author: String,
    id: Int,
    genre: String,
    price: String,
    detail: String,
    pages: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .padding(top = 80.dp)
                    .clip(RoundedCornerShape(40.dp))
            )

            IconButton(
                onClick = onBackClick,
                modifier = modifier
                    .padding(16.dp)
                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = modifier,
                )
            }

            IconButton(
                onClick = { onFavoriteButtonClicked(id, isFavorite) },
                modifier = modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.TopEnd),
                ) {
                Icon(
                    imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = if (!isFavorite) stringResource(id = R.string.add_favorite) else stringResource(
                        id = R.string.remove_favorite
                    ),
                    modifier = modifier,
                    tint = if (!isFavorite) Color.Black else Color.Red
                )
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = genre,
                modifier = modifier
                    .align(Alignment.Bottom),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                text = price,
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
            Text(
                text = pages,
                modifier = modifier
                    .align(Alignment.Bottom),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp
                )
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier.padding(top = 12.dp)
        )
        Text(
            text = stringResource(id = R.string.author, author),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            modifier = modifier.padding(top = 8.dp),
        )
        Text(
            text = stringResource(id = R.string.synopsis),
            style = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left
            ),
            modifier = modifier.fillMaxWidth(1f)
        )
        Text(
            text = detail,
            modifier = modifier.fillMaxWidth(1f),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Justify
            )
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetSubmissionTheme() {
        DetailContent(
            image = R.drawable.boy_mole_horse_fox,
            title = "The Boy, the Mole, the Fox and the Horse",
            author = "Charlie Mackesy",
            id = 1,
            genre = "Fiction",
            price = "$22.99",
            detail = "“What do you want to be when you grow up? asked the mole.\n\n“Kind,” said the boy.\n\nCharlie Mackesy offers inspiration and hope in uncertain times in this beautiful book, following the tale of a curious boy, a greedy mole, a wary fox and a wise horse who find themselves together in sometimes difficult terrain, sharing their greatest fears and biggest discoveries about vulnerability, kindness, hope, friendship and love. The shared adventures and important conversations between the four friends are full of life lessons that have connected with readers of all ages.",
            pages = "128 pages",
            isFavorite = true,
            onBackClick = {  },
            onFavoriteButtonClicked = { _, _ -> }
        )
    }
}