package com.anilpaudel.features.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.domain.SourceDto
import com.anilpaudel.features.PurpleGrey80
import com.anilpaudel.features.R

/**
 * Created by Anil Paudel on 26/08/2025.
 */
@Composable
fun NewsSingleItem(
    modifier: Modifier = Modifier,
    article: ArticleDto,
    onClick: (ArticleDto) -> Unit
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
        modifier = Modifier.padding(5.dp)
    ) {

        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .clickable {
                        onClick(article)
                    }
            ) {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(Modifier.height(2.dp))
                    article.description?.let {
                        Text(
                            text = it,
                            maxLines = 2,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 5.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
                Spacer(Modifier.height(2.dp))
                article.author?.let {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "Author: $it",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

    }

}

@Composable
fun SourceSingleItem(
    modifier: Modifier = Modifier,
    source: SourceDto,
    onCheckedChange: (SourceDto, Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        source.name?.let { name ->
            Text(text = name)
            Spacer(Modifier.weight(1f))
            Checkbox(checked = source.isSaved, onCheckedChange = { isChecked ->
                onCheckedChange(source, isChecked)
            })
        }
    }
}