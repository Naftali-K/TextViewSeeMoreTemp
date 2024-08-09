package com.nk.textviewseemoretemp

import android.animation.LayoutTransition
import android.content.Context
import android.os.Handler
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

class ShowMoreHelper private constructor(builder: Builder) {

    private var onTextClickListener: (() -> Unit)? = null
    private var onViewClickListener: (View.OnClickListener)? = null

    private var isShowMore = true

    // optional
    private val textLength: Int
    private val textLengthType: Int
    private val moreLessUnderlined: Boolean
    private val moreLabel: String
    private val lessLabel: String
    private val moreLabelColor: Int
    private val lessLabelColor: Int
    private val labelUnderLine: Boolean
    private val labelBold: Boolean
    private val expandAnimation: Boolean
    private val textClickableInExpand: Boolean
    private val textClickableInCollapse: Boolean

    init {
        this.textLength = builder.textLength
        this.textLengthType = builder.textLengthType
        this.moreLessUnderlined = builder.moreLessUnderlined
        this.moreLabel = builder.moreLabel
        this.lessLabel = builder.lessLabel
        this.moreLabelColor = builder.moreLabelColor
        this.lessLabelColor = builder.lessLabelColor
        this.labelUnderLine = builder.labelUnderLine
        this.labelBold = builder.labelBold
        this.expandAnimation = builder.expandAnimation
        this.textClickableInExpand = builder.textClickableInExpand
        this.textClickableInCollapse = builder.textClickableInCollapse
        this.onTextClickListener = builder.onClickListener
        this.onViewClickListener = builder.onViewClickListener
    }

    fun addShowMoreLess(
        textView: TextView,
        text: CharSequence,
        isContentExpanded: Boolean
    ) {
        if (textLengthType == TYPE_CHARACTER) {
            if (text.length <= textLength) {
                textView.text = text
                return
            }
        } else { // TYPE_LINE
            textView.maxLines = textLength
            textView.text = text
        }

        textView.post(Runnable {
            try {

                textView.setOnClickListener {
                    if (!isShowMore) {
                        onTextClickListener?.invoke()
                        onViewClickListener?.onClick(it)
                    }
                }

                val trimText = trimText(text)
                textView.text = trimText
                if (trimText.isEmpty())
                    return@Runnable
                if (textLengthType == TYPE_LINE) {
                    when {
                        textView.layout != null && textView.layout.lineCount <= textLength -> {
                            textView.text = trimText
                            return@Runnable
                        }
                        isContentExpanded -> {//Initial condition is expand
                            addShowLess(textView, trimText)
                            return@Runnable
                        }
                        else -> {//Initial condition is collapse
                            addShowMore(textView, trimText)
                            return@Runnable
                        }
                    }
                } else {
                    when {
                        isContentExpanded -> {//Initial condition is expand
                            addShowLess(textView, trimText)
                            return@Runnable
                        }
                        else -> {///Initial condition is collapse
                            addShowMore(textView, trimText)
                            return@Runnable
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    private fun addShowMore(
        textView: TextView,
        trimText: CharSequence
    ) {
        try {

            isShowMore = true

            val newSubString: CharSequence
            if (textLengthType == TYPE_LINE) {
//                Log.i("TAG", "textView in class: " + textView.toString())
                val lp = textView.layoutParams as ViewGroup.MarginLayoutParams
                val subString = trimText.substring(
                    startIndex = textView.layout.getLineStart(0),
                    endIndex = textView.layout.getLineEnd(textLength - 1)
                )
                newSubString = if (!subString.endsWith("\n", false)) {
                    val startRange = subString.length - (moreLabel.length + 4 + lp.rightMargin / 6)
                    val endRange = subString.length
                    if (startRange > 0) {
                        subString.removeRange(startRange, endRange)
                    } else {
                        subString
                    }
                } else {
                    subString.removeSuffix("\n")
                }
            } else {
                newSubString = trimText.subSequence(0, textLength)
            }

            var startIndex = 0
            var endIndex = 0

            val spannableStringBuilder = SpannableStringBuilder(
                newSubString
            ).apply {
                this.append("...")

                Log.d(TAG, "MORE moreLessUnderlined = $moreLessUnderlined")

                if (moreLessUnderlined) {
                    startIndex = this.length

                    Log.d(TAG, "MORE started Index = $startIndex")
                }

                this.append(moreLabel)

                if (moreLessUnderlined) {
                    endIndex = this.length
                    //endIndex = startIndex + moreLabel.length

                    Log.d(TAG, "MORE ended Index = $endIndex")
                }
            }

            val ss = SpannableString.valueOf(spannableStringBuilder)
            val moreLabelClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    addShowLess(textView, trimText)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = labelUnderLine
                    ds.isFakeBoldText = labelBold
                    ds.color = moreLabelColor
                }
            }
            ss.setSpan(moreLabelClickableSpan, ss.length - moreLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            if (moreLessUnderlined) {
                ss.setSpan(UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            if (textClickableInExpand) {
                if (moreLabel.isEmpty()) {
                    Handler().post { //textView
                        textView.setOnClickListener {
                            onTextClickListener?.invoke()
                            onViewClickListener?.onClick(it)
                        }
                    }
                } else {
                    val exceptMoreLabelClickableSpan = object : ClickableSpan() {
                        override fun onClick(view: View) {
                            onTextClickListener?.invoke()
                            onViewClickListener?.onClick(view)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                            ds.color = textView.currentTextColor
                        }
                    }
                    ss.setSpan(exceptMoreLabelClickableSpan, 0, ss.length - moreLabel.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            if (expandAnimation) {
                val layoutTransition = LayoutTransition()
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                (textView.parent as ViewGroup).layoutTransition = layoutTransition
            }
            textView.text = ss
            textView.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addShowLess(
        textView: TextView,
        trimText: CharSequence
    ) {
        try {

            isShowMore = false

            textView.maxLines = Integer.MAX_VALUE

            var startIndex = 0
            var endIndex = 0

            val spannableStringBuilder = SpannableStringBuilder(trimText).apply {
                if (lessLabel.isNotEmpty()) {
                    this.append(" ")

                    Log.d(TAG, "LESS moreLessUnderlined = $moreLessUnderlined")

                    if (moreLessUnderlined) {
                        startIndex = this.length

                        Log.d(TAG, "LESS started Index = $startIndex")
                    }

                    this.append(lessLabel)

                    if (moreLessUnderlined) {
                        endIndex = this.length
                        //endIndex = startIndex + moreLabel.length

                        Log.d(TAG, "LESS ended Index = $endIndex")
                    }
                }
            }

            val ss = SpannableString.valueOf(spannableStringBuilder)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    if (lessLabel.isNotEmpty()) {
                        addShowMore(textView, trimText)
                    }
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = labelUnderLine
                    ds.isFakeBoldText = labelBold
                    ds.color = lessLabelColor
                }
            }

            if (lessLabel.isNotEmpty()) {
                ss.setSpan(clickableSpan, ss.length - lessLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                if (moreLessUnderlined) {
                    ss.setSpan(UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            if (textClickableInCollapse) {
                if (lessLabel.isEmpty()) {
                    Handler().post { //textView
                        textView.setOnClickListener {
                            onTextClickListener?.invoke()
                            onViewClickListener?.onClick(it)
                        }
                    }
                } else {
                    val exceptLessLabelClickableSpan = object : ClickableSpan() {
                        override fun onClick(view: View) {
                            onTextClickListener?.invoke()
                            onViewClickListener?.onClick(view)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                            ds.color = textView.currentTextColor
                        }
                    }
                    ss.setSpan(exceptLessLabelClickableSpan, 0, ss.length - lessLabel.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            if (expandAnimation) {
                val layoutTransition = LayoutTransition()
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                (textView.parent as ViewGroup).layoutTransition = layoutTransition
            }
            textView.text = ss
            textView.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun trimText(text: CharSequence): CharSequence {
        var length = text.length
        val trimmedLength: Int = TextUtils.getTrimmedLength(text)
        if (length > trimmedLength) {
            val builder = SpannableStringBuilder(text)
            var start = 0
            while (start < length && builder[start] <= ' ') {
                start++
            }
            builder.delete(0, start)
            length -= start

            var end = length
            while (end >= 0 && builder[end - 1] <= ' ') {
                end--
            }
            builder.delete(end, length)
            return builder
        }
        return text
    }

    class Builder(
        private val context: Context
    ) {
        var textLength = 3
        var textLengthType = TYPE_LINE
        var moreLessUnderlined = false
        var moreLabel = context.getString(R.string.see_more)
        var lessLabel = context.getString(R.string.see_less)
        var moreLabelColor = ContextCompat.getColor(context, R.color.magenta) //Color.parseColor("#1FB487")
        var lessLabelColor = ContextCompat.getColor(context, R.color.purple_700) //Color.parseColor("#1FB487")
        var labelUnderLine = false
        var labelBold = false
        var expandAnimation = false
        var textClickableInExpand = true
        var textClickableInCollapse = true
        var onClickListener: (() -> Unit)? = null
        var onViewClickListener: (View.OnClickListener)? = null

        fun showMoreLessUnderlined(underlined: Boolean): Builder {
            moreLessUnderlined = underlined
            return this
        }

        fun textLengthAndLengthType(length: Int, textLengthType: Int): Builder {
            this.textLength = length
            this.textLengthType = textLengthType
            return this
        }

        fun showMoreLabel(moreLabel: String): Builder {
            this.moreLabel = moreLabel
            return this
        }

        fun showLessLabel(lessLabel: String): Builder {
            this.lessLabel = lessLabel
            return this
        }

        fun showMoreLabelColor(moreLabelColor: Int): Builder {
            this.moreLabelColor = ContextCompat.getColor(context, moreLabelColor)
            return this
        }

        fun showLessLabelColor(lessLabelColor: Int): Builder {
            this.lessLabelColor = ContextCompat.getColor(context, lessLabelColor)
            return this
        }

        fun labelUnderLine(labelUnderLine: Boolean): Builder {
            this.labelUnderLine = labelUnderLine
            return this
        }

        fun labelBold(labelBold: Boolean): Builder {
            this.labelBold = labelBold
            return this
        }

        fun textClickable(textClickableInExpand: Boolean, textClickableInCollapse: Boolean): Builder {
            this.textClickableInExpand = textClickableInExpand
            this.textClickableInCollapse = textClickableInCollapse
            return this
        }

        fun expandAnimation(expandAnimation: Boolean): Builder {
            this.expandAnimation = expandAnimation
            return this
        }

        fun setOnClickListener(listener: () -> Unit): Builder {
            this.onClickListener = listener
            return this
        }

        fun setViewClickListener(listener: View.OnClickListener): Builder {
            this.onViewClickListener = listener
            return this
        }

        fun build(): ShowMoreHelper {
            return ShowMoreHelper(this)
        }
    }

    companion object {
        private val TAG = ShowMoreHelper::class.java.simpleName
        const val TYPE_LINE = 1
        const val TYPE_CHARACTER = 2
    }
}