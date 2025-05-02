package com.ahmed.weatherapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TabsWithPager() {
    val tabs = listOf("Home", "Profile", "Settings")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f) // fill available space
        ) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> ProfileScreen()
                2 -> SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Welcome to Home", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProfileScreen() {
    Text("This is your Profile", modifier = Modifier.padding(16.dp))
}

@Composable
fun SettingsScreen() {
    Text("Settings page", modifier = Modifier.padding(16.dp))
}
