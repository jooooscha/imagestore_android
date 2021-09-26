package com.joooooscha.imagestore.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("SELECT * FROM imageDB ORDER BY date")
    suspend fun getAll(): List<ImageDB>

    @Query("SELECT name FROM imageDB")
    suspend fun getAllNames(): Array<String>

    @Query("SELECT id FROM imageDB")
    suspend fun getAllIds(): Array<Int>

    @Query("SELECT * FROM imageDB WHERE id in (:id)")
    suspend fun getImageById(id: Int): Array<ImageDB>

//    @Query("INSERT INTO imageDB VALUES (:id, :name, :height, :width, :date, :type, :coordx, :coordy)")
//    suspend fun putImage(id: Int, name: String, height: Int, width: Int, date: String, type: String, coordx: Double, coordy: Double)
    @Query("INSERT INTO imageDB VALUES (:id, :name, :height, :width, :date, :type, :coordx, :coordy)")
    suspend fun putImage(id: Int, name: String, height: Int, width: Int, date: String, type: String, coordx: Double, coordy: Double)

}