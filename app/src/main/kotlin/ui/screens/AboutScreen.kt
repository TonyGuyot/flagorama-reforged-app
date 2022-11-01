/*
 * Copyright (C) 2022 Tony Guyot - All rights reserved.
 */
package io.github.tonyguyot.flagorama.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tonyguyot.flagorama.BuildConfig
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.TopLevelAppBar
import io.github.tonyguyot.flagorama.ui.theme.AppTheme
import io.github.tonyguyot.flagorama.ui.theme.monofettFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onOpenDrawerClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopLevelAppBar(stringResource(R.string.title_about), onOpenDrawerClick) }
    ) { paddingValues ->
        AboutScreenContent(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun AboutScreenContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = 25.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AboutHeader()
        AboutTitle(stringResource(R.string.app_name))
        AboutSubtitle(formatVersion())
        Spacer(modifier = Modifier.height(20.dp))
        AboutDescription(stringResource(R.string.info_description))
        Spacer(modifier = Modifier.height(20.dp))
        AboutCopyright(stringResource(R.string.app_copyright))
        Spacer(modifier = Modifier.height(20.dp))
        CreditsTitle(stringResource(R.string.info_credits_photo_title))
        stringArrayResource(R.array.info_credits_photo_items).forEach {
            CreditsItem(item = it)
        }
        Spacer(modifier = Modifier.height(10.dp))
        CreditsTitle(stringResource(R.string.info_credits_font_title))
        stringArrayResource(R.array.info_credits_font_items).forEach {
            CreditsItem(item = it)
        }
    }
}

@Composable
private fun formatVersion(): String {
    val versionTag = stringResource(R.string.info_version, BuildConfig.VERSION_NAME)
    val debugTag = stringResource(R.string.info_debug)
    return if (BuildConfig.DEBUG) "$versionTag $debugTag" else versionTag
}

@Composable
private fun AboutHeader() {
    val image: Painter = painterResource(id = R.drawable.photo1)
    Image(
        painter = image,
        contentDescription = stringResource(R.string.about_image_description),
        modifier = Modifier.widthIn(100.dp, 400.dp)
    )
}

@Composable
private fun AboutTitle(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        fontFamily = monofettFontFamily,
        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
    )
}

@Composable
private fun AboutSubtitle(subtitle: String) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun AboutDescription(description: String) {
    Text(
        text = description,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun AboutCopyright(copyright: String) {
    Text(text = copyright, color = MaterialTheme.colorScheme.primary)
}

@Composable
private fun CreditsTitle(title: String) {
    Text(text = title, style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun CreditsItem(item: String) {
    Text(text = item, style = MaterialTheme.typography.bodySmall)
}

@Preview(showBackground = true)
@Composable
private fun DefaultAboutScreenPreview() {
    AppTheme {
        AboutScreen {}
    }
}