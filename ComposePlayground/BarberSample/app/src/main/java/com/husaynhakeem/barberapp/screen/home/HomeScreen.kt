package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BlurCircular
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.husaynhakeem.barberapp.data.barbers
import com.husaynhakeem.barberapp.data.services
import kotlinx.coroutines.launch

const val SCREEN_NAME_HOME = "home"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                toggleDrawer = {
                    if (scaffoldState.drawerState.isOpen) {
                        scope.launch { scaffoldState.drawerState.close() }
                    } else {
                        scope.launch { scaffoldState.drawerState.open() }
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar() },
        drawerContent = { DrawerContent() },
    ) {
        Content(
            modifier = Modifier.padding(it),
        )
    }
}

@Composable
private fun TopBar(
    toggleDrawer: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Icon(
            modifier = Modifier.clickable { toggleDrawer() },
            imageVector = Icons.Default.Menu,
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
        )
    }
}

@Composable
private fun BottomNavigationBar() {
    BottomNavigation {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.BlurCircular,
                    contentDescription = null,
                )
            },
            selected = true,
            onClick = { },
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                )
            },
            selected = false,
            onClick = { },
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                )
            },
            selected = false,
            onClick = { },
        )
    }
}

@Composable
private fun DrawerContent() {
    Text("Drawer title", modifier = Modifier.padding(16.dp))
    Divider()
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        PromoCard(
            modifier = Modifier.fillMaxWidth(),
            label = "Accumulate 100 points and get a free visit",
            showPromo = {},
        )
        Barbers(
            modifier = Modifier.fillMaxWidth(),
            barbers = barbers,
            showAll = {},
            showBarber = {},
        )
        Services(
            modifier = Modifier.fillMaxWidth(),
            services = services,
            showAll = {},
            showService = {},
        )
    }
}