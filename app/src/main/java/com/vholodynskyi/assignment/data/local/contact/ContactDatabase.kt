package com.vholodynskyi.assignment.data.local.contact

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun userDao(): ContactDao
}
