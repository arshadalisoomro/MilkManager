package pk.inlab.app.expenselogger.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CustomGridRecyclerView : RecyclerView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is GridLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw ClassCastException("This RecyclerView should use GridLayoutManager as the layout manager")
        }
    }

    override fun attachLayoutAnimationParameters(
        child: View?,
        params: ViewGroup.LayoutParams,
        index: Int,
        count: Int
    ) {
        if (adapter != null && layoutManager is GridLayoutManager) {
            //val
            var animationParams =
                params.layoutAnimationParameters as? GridLayoutAnimationController.AnimationParameters // CustomGridRecyclerView.kt:38 is the line where kotlin.TypeCastException occurs I have tried with GridLayoutAnimationController.AnimationParameters? as well but never succeeded

            if (animationParams == null) {
                animationParams = GridLayoutAnimationController.AnimationParameters()
                params.layoutAnimationParameters = animationParams
            }
            val columns = (layoutManager as GridLayoutManager?)!!.spanCount
            animationParams.count = count
            animationParams.index = index
            animationParams.columnsCount = columns
            animationParams.rowsCount = count / columns
            val invertedIndex = count - 1 - index
            animationParams.column = columns - 1 - invertedIndex % columns
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }

//    override fun attachLayoutAnimationParameters(
//        child: View?,
//        params: ViewGroup.LayoutParams,
//        index: Int,
//        count: Int
//    ) {
//        if (adapter != null && layoutManager is GridLayoutManager) {
//            //val
//            val animationParams =
//                params.layoutAnimationParameters as? GridLayoutAnimationController.AnimationParameters
//            val columns = (layoutManager as GridLayoutManager?)!!.spanCount
//            animationParams!!.count = count
//            animationParams.index = index
//            animationParams.columnsCount = columns
//            animationParams.rowsCount = count / columns
//            val invertedIndex = count - 1 - index
//            animationParams.column = columns - 1 - invertedIndex % columns
//            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns
//        } else {
//            super.attachLayoutAnimationParameters(child, params, index, count)
//        }
//    }
}