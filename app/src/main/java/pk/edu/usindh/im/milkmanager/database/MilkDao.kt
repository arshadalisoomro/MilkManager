/*
 *    Copyright 2020 In-Lab Inc. Pakistan
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package pk.edu.usindh.im.milkmanager.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pk.edu.usindh.im.milkmanager.model.Milk
import pk.edu.usindh.im.milkmanager.model.Sum

@Dao
interface MilkDao {

    @Insert
    suspend fun saveMilk(milk: Milk)

    @Update(entity = Milk::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMilk(milk: Milk): Int

    @Delete
    suspend fun deleteMilk(milk: Milk)

    @Query("SELECT * FROM MilkTable ORDER BY purchaseDate DESC")
    fun findAllMilk(): Flow<List<Milk>>

    @Query("SELECT SUM(purchaseWeight) AS sum FROM MilkTable")
    fun calculateSumOfAllWeight(): Flow<Sum>

}