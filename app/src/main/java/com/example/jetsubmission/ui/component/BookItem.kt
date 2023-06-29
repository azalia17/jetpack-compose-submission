package com.example.jetsubmission.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsubmission.R
import com.example.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun BookItem(
    image: Int,
    title: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(1.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.book_cover),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = price,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BookItemPreview() {
    JetSubmissionTheme {
        BookItem(image = R.drawable.boy_mole_horse_fox, title = "The Boy, THe Horse and The Fox", "$34.9")
    }
}