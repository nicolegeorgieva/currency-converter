package com.example.currencyconverter.screen.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactScreen() {
    val viewModel: ContactViewModel = viewModel()
    val uiState = viewModel.uiState()

    ContactUi(
        uiState = uiState,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
private fun ContactUi(
    uiState: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "first name")
            Text(text = "last name")
            Text(text = "phone number")
        }

        for (contact in uiState.contacts) {
            Row {
                Column {
                    Text(text = contact.firstName, fontWeight = FontWeight.Bold)
                    Text(text = contact.lastName, fontWeight = FontWeight.Bold)
                    Text(text = contact.phoneNumber)
                }

                Spacer(modifier = Modifier.weight(1f))

                FilledIconButton(
                    onClick = {
                        /* doSomething() */
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}