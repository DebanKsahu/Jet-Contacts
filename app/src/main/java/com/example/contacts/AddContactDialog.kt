package com.example.contacts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = state.isAddingContact,
        enter = scaleIn(tween(300))+ fadeIn(tween(300))
    ) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(ContactEvent.HideDialog)
            },
            title = {Text(text = "Add Contact")},
            text = {
                Column {
                    TextField(
                        value = state.name,
                        onValueChange = {onEvent(ContactEvent.SetName(it))},
                        placeholder = { Text(text = "Name") }
                    )
                    TextField(
                        value = state.phoneNumber,
                        onValueChange = {onEvent(ContactEvent.SetContactNumber(it))},
                        placeholder = { Text(text = "Contact Number") }
                    )
                }
                   },
            confirmButton = {
                Button(
                    onClick = {onEvent(ContactEvent.SaveContact)},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Yes", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {onEvent(ContactEvent.HideDialog)},
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                }
            },
        )
    }
}