package com.example.currencyconverter.screen.contact

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
import com.example.currencyconverter.database.MyDatabase
import com.example.currencyconverter.database.contact.ContactDao
import com.example.currencyconverter.database.contact.ContactEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val database: MyDatabase,
    private val dao: ContactDao
) : ComposeViewModel<ContactState, ContactEvent>() {

    private val showContactDialog = mutableStateOf(false)
    private val firstName = mutableStateOf("")
    private val lastName = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")
    private val sortedBy = mutableStateOf(SortedBy.FIRST_NAME)
    private val addWithBlankFields = mutableStateOf(false)

    @Composable
    override fun uiState(): ContactState {
        return ContactState(
            contacts = getContacts(),
            showContactDialog = getContactDialogState(),
            sortedBy = getContactsSortedBy(),
            firstName = getFirstName(),
            lastName = getLastName(),
            phoneNumber = getPhoneNumber(),
            addWithBlankFields = getBlankFieldsState()
        )
    }

    @Composable
    private fun getContacts(): List<ContactEntity> {
        val res = when (getContactsSortedBy()) {
            SortedBy.FIRST_NAME -> remember {
                database.contactDao.getContactsOrderedByFirstName()
            }.collectAsState(initial = emptyList()).value

            SortedBy.LAST_NAME -> remember {
                database.contactDao.getContactsOrderedByLastName()
            }.collectAsState(initial = emptyList()).value

            SortedBy.PHONE_NUMBER -> remember {
                database.contactDao.getContactsOrderedByPhoneNumber()
            }.collectAsState(initial = emptyList()).value
        }

        return res
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
    private fun getBlankFieldsState(): Boolean {
        return addWithBlankFields.value
    }

    override fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.OnFirstNameChange -> onFirstNameChange(event.firstName)
            is ContactEvent.OnLastNameChange -> onLastNameChange(event.lastName)
            is ContactEvent.OnPhoneNumberChange -> onPhoneNumberChange(event.phoneNumber)
            is ContactEvent.OnShowContactDialog -> onShowContactDialog(event.show)
            ContactEvent.OnAddContact -> viewModelScope.launch {
                onAddContact()
            }

            is ContactEvent.OnDeleteContact -> viewModelScope.launch {
                onDeleteContact(event.contact)
            }

            ContactEvent.OnAddWithBlankFields -> onAddWithBlankFields()
            ContactEvent.OnFirstNameSort -> viewModelScope.launch { onFirstNameSort() }
            ContactEvent.OnLastNameSort -> viewModelScope.launch { onLastNameSort() }
            ContactEvent.OnPhoneNumberSort -> viewModelScope.launch { onPhoneNumberSort() }
        }
    }

    private fun onFirstNameSort() {
        sortedBy.value = SortedBy.FIRST_NAME
    }

    private fun onLastNameSort() {
        sortedBy.value = SortedBy.LAST_NAME
    }

    private fun onPhoneNumberSort() {
        sortedBy.value = SortedBy.PHONE_NUMBER
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

    private suspend fun onAddContact() {
        dao.upsertContact(
            ContactEntity(
                id = UUID.randomUUID().toString(),
                firstName = firstName.value,
                lastName = lastName.value,
                phoneNumber = phoneNumber.value
            )
        )

        firstName.value = ""
        lastName.value = ""
        phoneNumber.value = ""
        showContactDialog.value = false
    }

    private suspend fun onDeleteContact(contact: ContactEntity) {
        dao.deleteContact(
            contact
        )
    }

    private fun onAddWithBlankFields() {
        addWithBlankFields.value = true
    }
}