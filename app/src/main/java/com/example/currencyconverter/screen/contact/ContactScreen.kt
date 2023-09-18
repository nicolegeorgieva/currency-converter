package com.example.currencyconverter.screen.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.MyPreview
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.database.contact.ContactEntity

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
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "Back button") {
                BackButton()
            }

            item(key = "Sorting options") {
                SortOptions(
                    sortBy = uiState.sortedBy,
                    onSortBy = {
                        onEvent(ContactEvent.SortBy(it))
                    }
                )
            }

            item(key = "Contacts") {
                for (contact in uiState.contacts) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = {
                onEvent(ContactEvent.OnShowContactDialog(true))
            }) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Add contact"
            )
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
fun SortOptions(
    sortBy: SortedBy,
    onSortBy: (SortedBy) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SortOption(
            text = "first name",
            selected = sortBy == SortedBy.FIRST_NAME
        ) {
            onSortBy(SortedBy.FIRST_NAME)
        }

        SortOption(
            text = "last name",
            selected = sortBy == SortedBy.LAST_NAME
        ) {
            onSortBy(SortedBy.LAST_NAME)
        }

        SortOption(
            text = "phone number",
            selected = sortBy == SortedBy.PHONE_NUMBER
        ) {
            onSortBy(SortedBy.PHONE_NUMBER)
        }
    }
}

@Composable
fun SortOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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

@Preview
@Composable
fun ContactScreenPreview() {
    val contacts = listOf(
        ContactEntity(
            id = "",
            firstName = "Iliyan",
            lastName = "Germanov",
            phoneNumber = "+243827489723"
        ),
        ContactEntity(
            id = "",
            firstName = "Nicole",
            lastName = "Georgieva",
            phoneNumber = "+102187128937201"
        )
    )
    MyPreview {
        ContactUi(
            uiState = ContactState(
                contacts = contacts,
                showContactDialog = false,
                sortedBy = SortedBy.FIRST_NAME,
                firstName = "",
                lastName = "",
                phoneNumber = "",
                showWarningMessage = false
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
fun ContactScreenPreview2() {
    val contacts = listOf(
        ContactEntity(
            id = "",
            firstName = "Iliyan",
            lastName = "Germanov",
            phoneNumber = "+243827489723"
        ),
        ContactEntity(
            id = "",
            firstName = "Nicole",
            lastName = "Georgieva",
            phoneNumber = "+102187128937201"
        )
    )
    MyPreview {
        ContactUi(
            uiState = ContactState(
                contacts = contacts,
                showContactDialog = true,
                sortedBy = SortedBy.FIRST_NAME,
                firstName = "",
                lastName = "",
                phoneNumber = "",
                showWarningMessage = false
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
fun ContactScreenPreview3() {
    MyPreview {
        ContactUi(
            uiState = ContactState(
                contacts = emptyList(),
                showContactDialog = false,
                sortedBy = SortedBy.LAST_NAME,
                firstName = "",
                lastName = "",
                phoneNumber = "",
                showWarningMessage = false
            ),
            onEvent = {}
        )
    }
}