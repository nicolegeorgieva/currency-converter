package com.example.currencyconverter.screen.contact

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
import com.example.currencyconverter.database.contact.ContactDao
import com.example.currencyconverter.database.contact.ContactEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactDao: ContactDao
) : ComposeViewModel<ContactState, ContactEvent>() {

    private val showContactDialog = mutableStateOf(false)
    private val firstName = mutableStateOf("")
    private val lastName = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")
    private val sortedBy = mutableStateOf(SortedBy.FIRST_NAME)
    private val showWarningMessage = mutableStateOf(false)

    @Composable
    override fun uiState(): ContactState {
        return ContactState(
            contacts = getContacts(),
            showContactDialog = getContactDialogState(),
            sortedBy = getContactsSortedBy(),
            firstName = getFirstName(),
            lastName = getLastName(),
            phoneNumber = getPhoneNumber(),
            showWarningMessage = showWarningMessage()
        )
    }

    @Composable
    private fun getContacts(): List<ContactEntity> {
        val sortedBy = getContactsSortedBy()
        return remember(sortedBy) {
            when (sortedBy) {
                SortedBy.FIRST_NAME -> contactDao.getContactsOrderedByFirstName()
                SortedBy.LAST_NAME -> contactDao.getContactsOrderedByLastName()
                SortedBy.PHONE_NUMBER -> contactDao.getContactsOrderedByPhoneNumber()
            }
        }.collectAsState(initial = emptyList()).value
    }

    @Composable
    private fun getContactDialogState(): Boolean {
        return showContactDialog.value
    }

    @Composable
    private fun getFirstName(): String {
        return firstName.value
    }

    @Composable
    private fun getLastName(): String {
        return lastName.value
    }

    @Composable
    private fun getPhoneNumber(): String {
        return phoneNumber.value
    }

    @Composable
    private fun getContactsSortedBy(): SortedBy {
        return sortedBy.value
    }

    @Composable
    private fun showWarningMessage(): Boolean {
        return showWarningMessage.value
    }

    override fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.OnFirstNameChange -> onFirstNameChange(event.firstName)
            is ContactEvent.OnLastNameChange -> onLastNameChange(event.lastName)
            is ContactEvent.OnPhoneNumberChange -> onPhoneNumberChange(event.phoneNumber)
            is ContactEvent.OnShowContactDialog -> onShowContactDialog(event.show)
            ContactEvent.OnAddContact -> onAddContact()
            is ContactEvent.OnDeleteContact -> onDeleteContact(event.contact)
            ContactEvent.ShowWarning -> showWarning()
            is ContactEvent.SortBy -> sortBy(event.sortedBy)
        }
    }

    private fun sortBy(sortBy: SortedBy) {
        when (sortBy) {
            SortedBy.FIRST_NAME -> sortedBy.value = SortedBy.FIRST_NAME
            SortedBy.LAST_NAME -> sortedBy.value = SortedBy.LAST_NAME
            SortedBy.PHONE_NUMBER -> sortedBy.value = SortedBy.PHONE_NUMBER
        }
    }

    private fun onShowContactDialog(show: Boolean) {
        showContactDialog.value = show
    }

    private fun onFirstNameChange(firstNameSet: String) {
        firstName.value = firstNameSet
    }

    private fun onLastNameChange(lastNameSet: String) {
        lastName.value = lastNameSet
    }

    private fun onPhoneNumberChange(phoneNumberSet: String) {
        phoneNumber.value = phoneNumberSet
    }

    private fun onAddContact() {
        if (firstName.value.isNotBlank() &&
            lastName.value.isNotBlank() &&
            phoneNumber.value.isNotBlank()
        ) {
            showWarningMessage.value = false

            viewModelScope.launch {
                contactDao.upsertContact(
                    ContactEntity(
                        id = UUID.randomUUID().toString(),
                        firstName = firstName.value,
                        lastName = lastName.value,
                        phoneNumber = phoneNumber.value
                    )
                )
            }

            firstName.value = ""
            lastName.value = ""
            phoneNumber.value = ""
            showContactDialog.value = false
        } else {
            showWarningMessage.value = true
        }
    }

    private fun onDeleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            contactDao.deleteContact(
                contact
            )
        }
    }

    private fun showWarning() {
        showWarningMessage.value = true
    }
}