/*
 * Copyright (C) 2022 Tony Guyot - All rights reserved.
 */
package io.github.tonyguyot.flagorama.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tonyguyot.flagorama.ui.common.TopLevelAppBar
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.InfoCard
import io.github.tonyguyot.flagorama.ui.common.LinkButton
import io.github.tonyguyot.flagorama.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceScreen(
    modifier: Modifier = Modifier,
    onOpenDrawerClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopLevelAppBar(stringResource(R.string.title_source), onOpenDrawerClick) }
    ) { paddingValues ->
        SourceScreenContent(
            Modifier
                .fillMaxSize()
                .padding(paddingValues))
    }
}

@Composable
private fun SourceScreenContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = 15.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val titles = stringArrayResource(R.array.source_titles)
        val contents = stringArrayResource(R.array.source_contents)
        titles.forEachIndexed { index, title ->
            InfoCard(title = title, content = contents[index])
        }
        Spacer(Modifier.height(20.dp))
        LinkButton(
            label = stringResource(R.string.source_goto_github),
            icon = painterResource(R.drawable.ic_action_github),
            iconDescription = stringResource(R.string.source_link_description),
            uri = "https://github.com/TonyGuyot/flagorama-reforged-app"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultSourceScreenPreview() {
    AppTheme {
        SourceScreen {}
    }
}