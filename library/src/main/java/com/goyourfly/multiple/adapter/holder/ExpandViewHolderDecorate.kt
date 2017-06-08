package com.goyourfly.multiple.adapter.holder

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.goyourfly.multiple.adapter.MultipleAdapter

/**
 * Created by gaoyufei on 2017/6/8.
 */
class ExpandViewHolderDecorate(val activity: Activity,
                               var defaultViewId: Int,
                               var selectViewId: Int,
                               val gravity:Int,
                               val duration:Long) : ViewHolderDecorate {


    override fun decorate(viewHolder: RecyclerView.ViewHolder, adapter: MultipleAdapter): MultipleViewHolder {
        val context = viewHolder.itemView.context
        val root = LinearLayout(context)
        val linearParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)

        var selectView = findView(context,selectViewId)
        var defaultView = findView(context,defaultViewId)

        if(selectView == null) selectView = generateDefaultSelectView(context)
        if(defaultView == null) defaultView = generateDefaultUnSelectView(context)

        val selectRoot = FrameLayout(context)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.CENTER
        selectRoot.addView(defaultView,layoutParams)
        selectRoot.addView(selectView,layoutParams)
        selectRoot.visibility = View.GONE
        if(gravity == com.goyourfly.multiple.adapter.Gravity.LEFT){
            root.addView(selectRoot, linearParams)
            root.addView(viewHolder.itemView)
        }else if(gravity == com.goyourfly.multiple.adapter.Gravity.RIGHT){
            root.addView(viewHolder.itemView)
            root.addView(selectRoot, linearParams)
        }

        val rootParams = ViewGroup.LayoutParams(viewHolder.itemView.layoutParams)
        root.layoutParams = rootParams
        selectRoot.measure(rootParams.width, rootParams.height)
        selectRoot.visibility = View.GONE
        return ExpandViewHolder(root, viewHolder, adapter, selectRoot, selectView!!, defaultView!!,gravity,duration)
    }


    fun findView(context:Context,layout:Int):View?{
        if(layout == 0)
            return null
        return LayoutInflater.from(context).inflate(layout,null,false)
    }


    /**
     * 生成默认的SelectView
     */
    fun generateDefaultSelectView(context: Context): View {
        val root = FrameLayout(context)
        root.setBackgroundColor(0xFFFF4081.toInt())
        root.addView(View(context),48F.toPx(context),FrameLayout.LayoutParams.MATCH_PARENT)
        return root
    }

    /**
     * 生成默认的UnSelectView
     */
    fun generateDefaultUnSelectView(context: Context): View {
        val root = FrameLayout(context)
        root.setBackgroundColor(0xFFE0E0E0.toInt())
        root.addView(View(context),48F.toPx(context),FrameLayout.LayoutParams.MATCH_PARENT)
        return root
    }


    fun Float.toPx(context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.getDisplayMetrics()).toInt();
    }
}