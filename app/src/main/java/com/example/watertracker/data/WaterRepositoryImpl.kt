package com.example.watertracker.data

import android.annotation.SuppressLint
import android.content.Context
import com.example.watertracker.data.source.database.AppDatabase
import com.example.watertracker.data.source.database.WaterEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

class WaterRepositoryImpl(
    applicationContext: Context
) : WaterRepository {

    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val db = AppDatabase.getInstance(applicationContext)

    override suspend fun getTotalAmount(): Int {
        val currentDate = dateFormat.format(Date())
        return db.waterPortionDao().getByDate(currentDate)
            .sumOf { it.amount }
    }

    override suspend fun getAllPortion(): List<WaterEntity> {
        return withContext(IO) {
            db.waterPortionDao().getAll()
        }
    }

    override suspend fun savePortion(amount: Int) {
        withContext(IO) {
            db.waterPortionDao().add(
                WaterEntity(
                    uuid = UUID.randomUUID().toString(),
                    createdDate = dateFormat.format(Date()),
                    amount = amount
                )
            )
        }
    }
}