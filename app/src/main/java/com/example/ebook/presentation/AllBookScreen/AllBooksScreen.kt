package com.example.ebook.presentation.AllBookScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ebook.presentation.UIcomponent.Bookcart
import com.example.ebook.presentation.ViewModel

@Composable
fun AnimateShimmer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    width: Dp = 200.dp,
    height: Dp = 20.dp
) {
    // MUST be composable and have default params if you call AnimateShimmer()
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Box(
        modifier = modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush)
    )
}

@Composable
fun AllBooksScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.BringAllBooks()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        val res = viewModel.state.value

        when {
            res.isLoading -> {
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(count = 10) {
                        AnimateShimmer()
                    }
                }
            }

            res.error.isNotEmpty() -> {
                Text(text = res.error, modifier = modifier)
            }

            res.items.isNotEmpty() -> {
                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    LazyColumn(modifier = modifier.fillMaxSize()) {
                        items(res.items) {

                            Bookcart(
                                author = it.bookAuthor,
                                imageUrl = it.image,
                                title = it.bookName,
                                description = it.bookDescription,
                                navHostController = navHostController,
                                bookUrl = it.bookUrl
                            )

                        }
                    }
                }
            }

            else -> Text(text = "No Books Available", modifier = modifier)
        }
    }
}
