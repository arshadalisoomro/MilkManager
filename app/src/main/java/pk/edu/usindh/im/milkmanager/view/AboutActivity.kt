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

package pk.edu.usindh.im.milkmanager.view

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import pk.edu.usindh.im.milkmanager.R
import java.util.*


class AboutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val pInfo =
                this@AboutActivity.packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            val aboutPage: View = AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher_round)
                .setDescription(
                    getString(R.string.idea)
                )
                .addItem(Element().setTitle("Version : $version"))
                .addItem(getCopyRightsElement())
                .create()
            setContentView(aboutPage)

            // Back arrow
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun getCopyRightsElement(): Element {
        val copyRightsElement = Element()

        val copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.title = copyrights
        copyRightsElement.iconDrawable = R.drawable.ic_copyright_black_24dp
        copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
        copyRightsElement.iconNightTint = android.R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        copyRightsElement.setOnClickListener {
            val builder = AlertDialog.Builder(this@AboutActivity)
            builder.setTitle(getString(R.string.copyright_title))
            builder.setMessage(
                "" + copyrights +
                        "\nArshad Ali Soomro" +
                        "\nInstructor in Computer Science" +
                        "\nAt IBA IT Training Center Gambat" +
                        "\nSindh, Pakistan"
            )
            builder.setCancelable(false)
            builder.setPositiveButton(
                R.string.ok
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }

            builder.show()
        }


        return copyRightsElement
    }
}