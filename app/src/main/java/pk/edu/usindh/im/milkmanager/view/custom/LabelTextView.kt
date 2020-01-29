package pk.inlab.app.expenselogger.view.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

/**
 * A custom TexView
 * @author Arshad Ali Soomro
 */

class LabelTextView(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatTextView(context, attrs) {

    init {
        textSize = 18f
        gravity = Gravity.CENTER_HORIZONTAL
        setTypeface(this.typeface, Typeface.BOLD)
    }

}