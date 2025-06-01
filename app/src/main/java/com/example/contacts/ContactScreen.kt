package com.example.contacts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("My Contacts", style = MaterialTheme.typography.headlineLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ContactEvent.ShoeDialog) },
                containerColor = Color.Cyan,
                contentColor = Color.Red
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adding Contact")
            }
        },
        modifier = Modifier.padding(3.dp)
    ) { padding ->
        if (state.isAddingContact) {
            AddContactDialog(state = state, onEvent = onEvent)
        }
        WavyBackground()
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(state.contacts) { contact ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        contentColor = Color.Green
                    ),
                    onClick = {}
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp),
                                text = contact.name,
                                fontSize = 20.sp
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                text = contact.contactNumber,
                                fontSize = 15.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                onEvent(ContactEvent.DeleteContact(contact))
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete A Contact")
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun WavyBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background gradient
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF6C6C7),
                        Color(0xFFB8D8F6),
                        Color(0xFFF7A9A8),
                        Color(0xFF7E8CE0)
                    ),
                    start = Offset.Zero,
                    end = Offset(size.width, size.height)
                ),
                size = size
            )
            // Wavy shapes (simplified for demonstration)
            val path = Path().apply {
                moveTo(0f, size.height * 0.3f)
                cubicTo(
                    size.width * 0.25f, size.height * 0.25f,
                    size.width * 0.75f, size.height * 0.35f,
                    size.width, size.height * 0.3f
                )
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
                close()
            }
            drawPath(path, Color(0x66A6B9F7))
            val path2 = Path().apply {
                moveTo(0f, size.height * 0.7f)
                cubicTo(
                    size.width * 0.3f, size.height * 0.8f,
                    size.width * 0.7f, size.height * 0.6f,
                    size.width, size.height * 0.7f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path2, Color(0x66F7A9A8))
        }
    }
}