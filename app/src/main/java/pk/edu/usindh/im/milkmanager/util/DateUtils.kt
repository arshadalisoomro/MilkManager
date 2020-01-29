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

package pk.edu.usindh.im.milkmanager.util

import java.text.SimpleDateFormat
import java.util.*


class DateUtils {

    private val month = 0
    private val year = 0

    private fun init() { // TODO Auto-generated method stub
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
    }

    init { // TODO Auto-generated constructor stub
        init()
    }
    companion object {
        private var locale: Locale = Locale("en", "US")

        private fun min(): String {
            return java.lang.String.valueOf(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH))
        }

        private fun max(): String {
            return java.lang.String.valueOf(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH))
        }

        fun startOfCurrentMonth(): Int {
            return Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)
        }

        fun endOfCurrentMonth(): Int {
            return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        val today: String
            get() = SimpleDateFormat("MMM", locale)
                .format(Calendar.getInstance().time).toUpperCase(Locale.ROOT) +
                    " " + min() + "-" + max() +
                    ", " + Calendar.getInstance().get(Calendar.YEAR)

        fun getDate(date: Date): String {
            return SimpleDateFormat("dd", locale)
                .format(date).toString() +
                    " " +
                    SimpleDateFormat("hh:mm a", locale)
                        .format(date)
        }


        val monthDay: String
            get() {
                val result: String
                val date: Int = Calendar.getInstance().get(Calendar.DATE)
                result = if (date > 9) {
                    date.toString()
                } else {
                    "0$date"
                }
                return SimpleDateFormat("MMM", Locale.ROOT)
                    .format(Calendar.getInstance().time).toUpperCase(Locale.ROOT) +
                        "," + result
            }

        fun getMonthDay(dateCal: Calendar): String {
            val result: String
            val date: Int = dateCal.get(Calendar.DATE)
            result = if (date > 9) {
                date.toString()
            } else {
                "0$date"
            }
            return SimpleDateFormat("MMM", Locale.ROOT)
                .format(dateCal.getTime()).toUpperCase(Locale.ROOT) +
                    "," + result
        }

    }
}