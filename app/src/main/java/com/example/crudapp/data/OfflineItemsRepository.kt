package com.example.crudapp.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDAO: ItemDAO) : ItemsRepository{
    override fun getAllItemsStream(): Flow<List<Item>> = itemDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDAO.getItem(id)

    override suspend fun insertItem(item: Item) = itemDAO.insert(item)

    override suspend fun deleteItem(item: Item) = itemDAO.delete(item)

    override suspend fun updateItem(item: Item) = itemDAO.update(item)
}