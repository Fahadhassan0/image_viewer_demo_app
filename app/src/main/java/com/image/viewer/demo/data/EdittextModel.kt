package com.image.viewer.demo.data

import android.view.Gravity

class EdittextModel {
    var gravity = Gravity.TOP or Gravity.START
        private set
    var locationX = 0
        private set
    var locationY = 0
        private set
    var offsetX = 0
        private set
    var offsetY = 0
        private set
    var scale = 1f
        private set
    var rotation = 0f
        private set
    var mode = Mode.PIN
        private set
    var text: String? = null
        private set

    /**
     * @param gravity Gravity of the drawable. E.g. Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
     * means that drawable coordinates are applied to the center of the bottom edge of the
     * drawable.
     */
    fun setGravity(gravity: Int): EdittextModel {
        this.gravity = gravity
        return this
    }

    fun setText(text: String?): EdittextModel {
        this.text = text
        return this
    }

    /**
     * @param x X coordinate in pixels relative to original image.
     * @param y Y coordinate in pixels relative to original image.
     */
    fun setLocation(x: Int?, y: Int?): EdittextModel {
        locationX = x!!
        locationY = y!!
        return this
    }

    /**
     * @param x X offset in pixels relative to marker's icon.
     * @param y Y offset in pixels relative to marker's icon.
     */
    fun setOffset(x: Int, y: Int): EdittextModel {
        offsetX = x
        offsetY = y
        return this
    }

    /**
     * Sets default scale for marker's icon.
     */
    fun setScale(scale: Float): EdittextModel {
        this.scale = scale
        return this
    }

    /**
     * Sets default rotation for marker's icon.
     */
    fun setRotation(rotation: Float): EdittextModel {
        this.rotation = rotation
        return this
    }

    fun setMode(mode: Mode): EdittextModel {
        this.mode = mode
        return this
    }

    enum class Mode {
        /**
         * Pin is attached to an image according to specified gravity
         * but does not follow zoom and rotation.
         */
        PIN,

        /**
         * Pin is attached to an image according to specified gravity and follows zoom and rotation.
         */
        STICK
    }
}