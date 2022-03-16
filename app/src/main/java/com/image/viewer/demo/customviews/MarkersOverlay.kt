package com.image.viewer.demo.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import com.alexvasilkov.gestures.GestureController.OnStateChangeListener
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.GestureFrameLayout
import com.alexvasilkov.gestures.views.GestureImageView
import com.image.viewer.demo.R
import com.image.viewer.demo.data.EdittextModel
import com.image.viewer.demo.listeners.OnDialogTextChangedListener
import com.image.viewer.demo.utilities.showRenameDialog
import kotlin.math.abs

class MarkersOverlay : FrameLayout {

    private var imageView: GestureImageView? = null
    private var textView: TextView? = null
    private var params: LayoutParams? = null
    private var editableTextModel = EdittextModel()
    private val pointIn = FloatArray(2)
    private val pointOut = FloatArray(2)
    private var layoutInflater: LayoutInflater? = null
    private var mEdittextView: GestureFrameLayout? = null
    private var isMoving = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun attachToImage(imageView: GestureImageView) {
        layoutInflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        params =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params!!.gravity = Gravity.TOP or Gravity.START
        this.imageView = imageView

        // We should compute and draw overlay whenever image state is changed
        imageView.controller.addOnStateChangeListener(object : OnStateChangeListener {
            override fun onStateChanged(state: State) {
                invalidate()
                populateMarker()
            }

            override fun onStateReset(oldState: State, newState: State) {
                invalidate()
                populateMarker()
            }
        })
    }

    /**
     * Shows text at specified position with specified gravity.
     */
    fun addText(editableText: EdittextModel?) {
        editableTextModel = editableText!!
        invalidate()
        populateMarker()
    }

    fun editText(str: String?) {
        updateText(str)
    }

    fun populateMarker() {
        val image = if (imageView == null) null else imageView!!.drawable
        if (image != null) {
            drawMarker(editableTextModel)
        }
    }

    @SuppressLint("InflateParams")
    private fun drawMarker(model: EdittextModel) {

        //checking if text is not null
        if (model.text == null) {
            return
        }
        if (mEdittextView == null) {
            mEdittextView =
                layoutInflater!!.inflate(
                    R.layout.layout_page_editor_text,
                    null
                ) as GestureFrameLayout
            textView = mEdittextView!!.findViewById(R.id.tv_editor_text)
        }
        textView!!.text = model.text

        // Computing text location on the underlying image
        pointIn[0] = model.locationX.toFloat()
        pointIn[1] = model.locationY.toFloat()
        imageView!!.imageMatrix.mapPoints(pointOut, pointIn)
        params!!.setMargins(pointOut[0].toInt(), pointOut[1].toInt(), 0, 0)
        removeView(mEdittextView)
        mEdittextView!!.layoutParams = params
        addView(mEdittextView)
        addDragListener(mEdittextView, model)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addDragListener(editableText: FrameLayout?, model: EdittextModel) {
        editableText!!.setOnTouchListener(object : OnTouchListener {
            private val paramsF = params
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isMoving = false
                        initialX = paramsF!!.leftMargin
                        initialY = paramsF.topMargin
                        //get the touch location
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_UP -> if (!isMoving) {
                        //we will show edit text dialog on its click
                        showRenameDialog(
                            context!!,
                            R.string.label_edit_text,
                            R.string.label_edit,
                            object : OnDialogTextChangedListener {
                                override fun onTextChanged(str: String?) {
                                    updateText(str)
                                }
                            })
                    }
                    MotionEvent.ACTION_MOVE -> {
                        //Calculate the X and Y coordinates of the view.
                        val xDiff = (event.rawX - initialTouchX).toInt()
                        val yDiff = (event.rawY - initialTouchY).toInt()
                        paramsF!!.leftMargin = initialX + xDiff
                        paramsF.topMargin = initialY + yDiff
                        /* Set an offset of 10 pixels to determine controls moving. Else, normal touches
                         * could react as moving the control window around */
                        if (abs(xDiff) > 10 || abs(yDiff) > 10) {
                            isMoving = true
                        }
                        updateViewLayout(editableText, paramsF)
                        model.setLocation(initialX + xDiff, initialY + yDiff)
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun updateText(str: String?) {
        editableTextModel.setText(str)
        textView!!.text = str
        removeView(mEdittextView)
        addView(mEdittextView, params)

    }


}