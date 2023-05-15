package it.eros.moneymoney.models

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import it.eros.moneymoney.R


/**
 * Custom Navigation Image Button for dispaly the images on the navigation bar.
 * I decided to make a custom component for be able to hide/show text and do custom stuff
 */
class NavigationImageButton (
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.navigation_image_button, this)

        val customAttributesStyle = context.obtainStyledAttributes(attrs, R.styleable.NavigationImageButton, 0, 0)

        // Image component
        val imageComponent = findViewById<ImageView>(R.id.image)
        // Text component
        val textComponent = findViewById<TextView>(R.id.text)

        try {
            // Set the image by reference
            imageComponent.setImageResource(customAttributesStyle.getResourceId(R.styleable.NavigationImageButton_image, 0))
            // Set the text
            textComponent.text = customAttributesStyle.getString(R.styleable.NavigationImageButton_text)
        } finally {
            customAttributesStyle.recycle()
        }

        this.setOnClickListener {
            // Hide the text of the other buttons
            for (index in 0 until (this.getParent() as ViewGroup).childCount) {
                val nextChild = (this.getParent() as ViewGroup).getChildAt(index)
                nextChild.findViewById<TextView>(R.id.text)?.visibility = View.INVISIBLE
            }

            // Make the text visible on click
            textComponent.visibility = View.VISIBLE;
        }
    }
}