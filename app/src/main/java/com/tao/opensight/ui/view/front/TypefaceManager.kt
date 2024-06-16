package com.tao.opensight.ui.view.front

import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.tao.opensight.R
import com.tao.opensight.App

object TypefaceManager {

    private val typefaceCache: MutableMap<FontType, Typeface> = mutableMapOf()
    const val defaultStyle = 1

    fun applyCustomFont(attrs: AttributeSet?, textView: TextView) {
        val styleAttr = App.context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView, 0, 0)
        val styleIndex = styleAttr.getInteger(0, defaultStyle)
        styleAttr.recycle()
        FontType.entries.getOrNull(styleIndex)?.let { fontType ->
            getOrCreateTypeface(fontType)?.also { typeface ->
                applyFontSettings(textView, fontType, typeface)
            }
        }
    }

    fun getOrCreateTypeface(fontType: FontType): Typeface? =
        typefaceCache[fontType] ?: try {
            Typeface.createFromAsset(App.context.assets, fontType.path)
                .also { typefaceCache[fontType] = it }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    fun applyFontSettings(textView: TextView, fontType: FontType, typeface: Typeface) {
        textView.apply {
            if (fontType in arrayOf(FontType.HS_MEDIUM, FontType.HS_NORMAL, FontType.HS_REGULAR)) {
                includeFontPadding = false
            }
            this.typeface = typeface
        }
    }

    enum class FontType(val path: String) {
        HS_MEDIUM("fonts/SourceHanSansCN-Medium.otf"),
        HS_NORMAL("fonts/SourceHanSansCN-Normal.otf"),
        HS_REGULAR("fonts/SourceHanSansCN-Regular.otf"),
        AG_Light("fonts/AkzidenzGrotesk-Light.otf"),
        AG_MC("fonts/AkzidenzGrotesk-MediumCond.otf");
    }


    fun getFontTypeByName(name: String): FontType =
        FontType.entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: FontType.HS_NORMAL

}