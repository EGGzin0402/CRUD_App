package com.example.crudapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.Item
import com.example.crudapp.data.ItemsRepository
import com.himanshoe.charty.candle.model.CandleData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val candleDataList: StateFlow<List<CandleData>> =
        itemsRepository.getAllItemsStream().map { itemList ->
            var total = 0.0F
            var atual: Float
            itemList.sortedBy { item ->
                dateFormatter.parse(item.date)
            }.foldIndexed(mutableListOf<CandleData>()) { index, acc, item ->
                if (item.profit) {
                    atual = total+item.price.toFloat()
                } else {
                    atual = total-item.price.toFloat()
                }
                if (index > 0) {
                    acc.add(
                        CandleData(
                            open = total,
                            close = atual,
                            high = 0f,
                            low = 0f
                        )
                    )
                }else{
                    acc.add(
                        CandleData(
                            open = total,
                            close = if (item.profit){item.price.toFloat()} else {-item.price.toFloat()},
                            high = 0f,
                            low = 0f
                        )
                    )
                }
                total += if (item.profit) {
                    item.price.toInt()
                } else {
                    -item.price.toInt()
                }
                acc
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    val homeUiState :StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map {
            HomeUiState(
                it.sortedBy { item ->
                    dateFormatter.parse(item.date)
                }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<Item> = listOf())