package com.tao.opensight.ui.view.front

import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.tao.opensight.R
import com.tao.opensight.App
import java.util.EnumMap

/**
 * 字体管理类，目前支持TextView设置字体
 */
object TypefaceManager {
    private const val TAG = "TypefaceManager"

    private val typefaceCache: MutableMap<FontType, Typeface> = EnumMap(FontType::class.java)
    private const val defaultStyle = 1

    fun applyCustomFont(attrs: AttributeSet?, textView: TextView) {
        val styleAttr = App.context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomFontTextView,
            defaultStyle,
            defaultStyle
        )
        val styleIndex = styleAttr.getInteger(R.styleable.CustomFontTextView_fontName, defaultStyle)
        styleAttr.recycle()

        FontType.entries.getOrNull(styleIndex)?.let { fontType ->
            getOrCreateTypeface(fontType)?.let { typeface ->
                applyFontSettings(textView, fontType, typeface)
            } ?: Log.e(TAG, "Failed to load typeface for: ${fontType.name}")
        } ?: Log.e(TAG, "Invalid style index: $styleIndex")
    }

    fun getOrCreateTypeface(fontType: FontType): Typeface? =
        typefaceCache[fontType] ?: try {
            Typeface.createFromAsset(App.context.assets, fontType.path)
                .also {
                    typefaceCache[fontType] = it
                    Log.d(TAG, "Typeface loaded and cached: ${fontType.name}")
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading typeface: ${fontType.path}", e)
            null
        }

    fun applyFontSettings(textView: TextView, fontType: FontType, typeface: Typeface) {
        textView.apply {
            if (fontType in arrayOf(FontType.HS_MEDIUM, FontType.HS_NORMAL, FontType.HS_REGULAR)) {
                includeFontPadding = false
                Log.d(TAG, "Font padding disabled for: ${fontType.name}")
            }
            this.typeface = typeface
            Log.d(TAG, "Typeface set for TextView: ${fontType.name}")
        }
    }

    fun getFontTypeByName(name: String): FontType =
        FontType.entries.firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: FontType.HS_NORMAL.also {
                Log.d(TAG, "Default font type returned for name: $name") // 添加日志
            }

    enum class FontType(val path: String) {
        HS_MEDIUM("fonts/SourceHanSansCN-Medium.otf"),
        HS_NORMAL("fonts/SourceHanSansCN-Normal.otf"),
        HS_REGULAR("fonts/SourceHanSansCN-Regular.otf"),
        AG_Light("fonts/AkzidenzGrotesk-Light.otf"),
        AG_MC("fonts/AkzidenzGrotesk-MediumCond.otf");
    }

}