package it.eros.moneymoney.custom_components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import it.eros.moneymoney.R


class SwitchOnOffText (
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.switch_on_off_text, this)

        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.SwitchOnOffText, 0, 0)

        // Text before component
        val textBeforeComponent = findViewById<TextView>(R.id.text_before)
        // Switch component
        val switchComponent = findViewById<SwitchCompat>(R.id.switcher_custom)
        // Text after component
        val textAfterComponent = findViewById<TextView>(R.id.text_after)

        switchComponent.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                textAfterComponent.visibility = View.VISIBLE
                textBeforeComponent.visibility = View.INVISIBLE
                switchComponent.setBackgroundColor(customAttributesStyle.getColor(3, 0));
            }else{
                textAfterComponent.visibility = View.INVISIBLE
                textBeforeComponent.visibility = View.VISIBLE
            }
        })
    }
}