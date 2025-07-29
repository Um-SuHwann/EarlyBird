package com.earlybird.earlybirdcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.earlybird.earlybirdcompose.data.dao.TodoDao
import com.earlybird.earlybirdcompose.data.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}