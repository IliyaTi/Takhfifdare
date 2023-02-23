package com.example.takhfifdar.screens.viewmodels
//
//import android.app.Application
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
//import com.example.takhfifdar.data.repositories.local.database.Transaction
//import kotlinx.coroutines.launch
//
//class HistoryScreenViewModel(application: Application): AndroidViewModel(application) {
//
//    val loading = mutableStateOf(true)
//    var items: List<Transaction> = listOf()
//
//    fun loadData() {
//        viewModelScope.launch {
//            items = TakhfifdarDatabase.getDatabase(getApplication()).TransactionDao().getTransactions()
//            loading.value = false
//        }
//    }
//
//}