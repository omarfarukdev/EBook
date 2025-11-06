package com.example.ebook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import androidx.compose.ui.graphics.Color

@Composable
fun PdfViewerScreen(url:String) {
    var isDarkMode by remember { mutableStateOf(false) }
    val pdfState = rememberVerticalPdfReaderState(resource = ResourceType.Remote(url),isZoomEnable = true)
    VerticalPDFReader(state = pdfState,modifier= Modifier.fillMaxSize().background(color = Color.Gray))

}