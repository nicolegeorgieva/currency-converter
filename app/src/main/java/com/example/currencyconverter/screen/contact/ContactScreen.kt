package com.example.currencyconverter.screen.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

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
        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SortOption(
                text = "first name",
                selected = uiState.sortedBy == SortedBy.FIRST_NAME
            ) {
                onEvent(ContactEvent.OnFirstNameSort)
            }

            SortOption(
                text = "last name",
                selected = uiState.sortedBy == SortedBy.LAST_NAME
            ) {
                onEvent(ContactEvent.OnLastNameSort)
            }

            SortOption(
                text = "phone number",
                selected = uiState.sortedBy == SortedBy.PHONE_NUMBER
            ) {
                onEvent(ContactEvent.OnPhoneNumberSort)
            }
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
                        onEvent(ContactEvent.OnDeleteContact(contact))
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete, contentDescription = "Delete"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(onClick = {
                onEvent(ContactEvent.OnShowContactDialog(true))
            }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add contact"
                )
            }
        }

        AddContactDialog(
            showContactDialog = uiState.showContactDialog,
            onShowContactDialog = {
                onEvent(ContactEvent.OnShowContactDialog(it))
            },
            firstName = uiState.firstName,
            onFirstNameChange = {
                onEvent(ContactEvent.OnFirstNameChange(it))
            },
            lastName = uiState.lastName,
            onLastNameChange = {
                onEvent(ContactEvent.OnLastNameChange(it))
            },
            phoneNumber = uiState.phoneNumber,
            onPhoneNumberChange = {
                onEvent(ContactEvent.OnPhoneNumberChange(it))
            },
            onAddContact = {
                onEvent(ContactEvent.OnAddContact)
            },
            warningMessage = uiState.showWarningMessage
        )
    }
}

@Composable
fun SortOption(text: String, selected: Boolean, action: () -> Unit) {
    Button(
        onClick = {
            action()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.Blue else Color.DarkGray,
            contentColor = Color.White,
        )
    ) {
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    showContactDialog: Boolean,
    onShowContactDialog: (Boolean) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    onAddContact: () -> Unit,
    warningMessage: Boolean
) {
    if (showContactDialog) {
        AlertDialog(
            onDismissRequest = {
                onShowContactDialog(false)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Add contact"
                )
            },
            title = {
                Text(text = "Add contact")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "First name")
                        TextField(value = firstName, onValueChange = { onFirstNameChange(it) })
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Last name")
                        TextField(
                            value = lastName,
                            onValueChange = {
                                onLastNameChange(it)
                            })
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Phone number")
                        TextField(
                            value = phoneNumber,
                            onValueChange = {
                                onPhoneNumberChange(it)
                            })
                    }

                    if (warningMessage) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "All fields should be filled!",
                            color = Color.Red
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onAddContact()
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onShowContactDialog(false)
                }) {
                    Text("Dismiss")
                }
            })
    }
}