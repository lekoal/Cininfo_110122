package com.example.cininfo.data

interface LocalRepository {
    fun getAllSearchHistory(): List<String>
    fun saveEntity(searchItem: String)
}