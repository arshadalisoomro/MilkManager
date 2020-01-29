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

package pk.inlab.app.expenselogger.view.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pk.edu.usindh.im.milkmanager.database.repository.MilkRepository
import pk.edu.usindh.im.milkmanager.model.Milk

class MilkViewModel(
    application: Application
): AndroidViewModel(application) {

    private var repository = MilkRepository(application)

    private var _allMilk = repository.getAllMilk()

    val allMilk: LiveData<List<Milk>>
        get() = _allMilk

    fun save(milk: Milk) {
        repository.save(milk)
    }

    fun update(milk: Milk) {
        repository.update(milk)
    }

    fun delete(milk: Milk) {
        repository.delete(milk)
    }

    fun getSumOfAllWeight():MutableLiveData<Int> {
        return repository.getSumOfAllWeight()
    }
}