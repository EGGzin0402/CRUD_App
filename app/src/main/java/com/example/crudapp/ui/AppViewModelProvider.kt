package com.example.crudapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.crudapp.CRUDApplication
import com.example.crudapp.ui.home.HomeViewModel
import com.example.crudapp.ui.item.ItemDetailsViewModel
import com.example.crudapp.ui.item.ItemEditViewModel
import com.example.crudapp.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.itemsRepository
            )
        }

        initializer {
            ItemEntryViewModel(crudApplication().container.itemsRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.itemsRepository
            )
        }

        initializer {
            HomeViewModel(crudApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.crudApplication(): CRUDApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CRUDApplication)