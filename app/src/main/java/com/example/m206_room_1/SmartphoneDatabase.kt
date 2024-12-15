package com.example.m206_room_1

import android.content.Context
import androidx.room.*

// 1. Entité Smartphone
@Entity(tableName = "smartphone_table")
data class Smartphone(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val prix: Double,
    val image: String
)

// 2. DAO SmartphoneDao
@Dao
interface SmartphoneDao {
    @Insert
    fun insertSmartphone(smartphone: Smartphone)

    @Query("SELECT * FROM smartphone_table")
    fun getAllSmartphones(): List<Smartphone>
}

// 3. Base de données AppDatabase
@Database(entities = [Smartphone::class], version = 1, exportSchema = false)
abstract class SmartphoneDatabase : RoomDatabase() {
    abstract fun smartphoneDao(): SmartphoneDao

    companion object {
        @Volatile
        private var INSTANCE: SmartphoneDatabase? = null

        fun getDatabase(context: Context): SmartphoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartphoneDatabase::class.java,
                    "smartphone_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
