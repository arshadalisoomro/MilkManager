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

package pk.edu.usindh.im.milkmanager.database.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pk.edu.usindh.im.milkmanager.database.MilkDatabase
import pk.edu.usindh.im.milkmanager.model.Milk

class MilkRepository(application: Application) {

    private val milkDao = MilkDatabase.getDatabase(application).getMilkDao()

    private var allMilk = MutableLiveData<List<Milk>>()
    private var sumOfAllWeight = MutableLiveData<Int>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val milkData = milkDao.findAllMilk()
            milkData.collect{
                allMilk.postValue(it)
            }
        }

        // Get Sum of All weights
        CoroutineScope(Dispatchers.IO).launch {
            milkDao.calculateSumOfAllWeight().collect{
                sumOfAllWeight.postValue(it.sum)
            }
        }
    }

    fun save(milk: Milk) {
        CoroutineScope(Dispatchers.IO).launch {
            milkDao.saveMilk(milk)
        }
    }

    fun update(milk: Milk) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("UPDATE", "" + milk.description)
            val it = milkDao.updateMilk(milk)
            Log.e("UPDATE", "returned $it as value")

        }
    }

    fun delete(milk: Milk) {
        CoroutineScope(Dispatchers.IO).launch {
            milkDao.deleteMilk(milk)
        }
    }

    fun getSumOfAllWeight(): MutableLiveData<Int> {
        return sumOfAllWeight
    }

    fun getAllMilk(): MutableLiveData<List<Milk>> {
        return allMilk
    }



}