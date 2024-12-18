package com.example.carmvvmrepositorydb.model

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mark") val mark: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "color") val color: String
)

@Dao
interface CarDao {
    @Insert
    suspend fun insertCar(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()
}

@Database(entities = [Car::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class CarRepository(private val carDao: CarDao) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()

    suspend fun insertCar(car: Car) = carDao.insertCar(car)

    suspend fun deleteAllCars() = carDao.deleteAllCars()
}