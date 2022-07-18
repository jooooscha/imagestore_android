package com.joooooscha.imagestore.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("SELECT * FROM ImageMeta ORDER BY date")
    suspend fun getAllMeta(): List<ImageMeta>

    @Query("SELECT * FROM ImageMeta ORDER BY date DESC LIMIT :to - :from OFFSET :to")
    suspend fun getRange(from: Int, to: Int): List<ImageMeta>

    @Query("SELECT id FROM ImageMeta ORDER BY date DESC LIMIT :to - :from OFFSET :from")
    suspend fun getIdRange(from: Int, to: Int): List<Int>

    @Query("SELECT name FROM ImageMeta")
    suspend fun getAllNames(): Array<String>

    @Query("SELECT id FROM ImageMeta")
    suspend fun getAllIds(): Array<Int>

    @Query("SELECT * FROM ImageMeta WHERE id = :id")
    suspend fun getImageById(id: Int): Array<ImageMeta>

//    @Query("INSERT INTO imageDB VALUES (:id, :name, :height, :width, :date, :type, :coordx, :coordy)")
//    suspend fun putImage(id: Int, name: String, height: Int, width: Int, date: String, type: String, coordx: Double, coordy: Double)

    @Query("INSERT INTO ImageMeta VALUES (:id, :name, :height, :width, :date, :type, :coordx, :coordy)")
    suspend fun putMeta(id: Int, name: String, height: Int, width: Int, date: String, type: String, coordx: Double, coordy: Double)

}