package ir.drax.autoscrolltext

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.LinearInterpolator
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView


class AutoScrollTextView(context: Context?, attrs: AttributeSet?): HorizontalScrollView(context, attrs) {
    private var text = ""
    private var textView:TextView
    private var scrollSpeed = 5;
    private var scrollDirection = 0     //ltr=0  rtl=1
    private  var activeAnimator= AnimatorSet()

    init {
        isHorizontalScrollBarEnabled=false;

        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        val root = LinearLayout(context)

        textView = TextView(context);
        textView.isSingleLine=true
        textView.maxLines=1

        root.addView(textView,layoutParams)
        addView(root,layoutParams)

        context!!.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AutoScrollTextView,
            0, 0).apply {

            try {
                text = this!!.getString(R.styleable.AutoScrollTextView_text)!!

                val textSize = getDimension(R.styleable.AutoScrollTextView_textSize,0f)
                textView.textSize=if (textSize==0f)12f else textSize

                scrollSpeed = getInt(R.styleable.AutoScrollTextView_animationSpeed, scrollSpeed)
                textView.textSize=if (textSize==0f)12f else textSize

                val textColor = getColor(R.styleable.AutoScrollTextView_textColor,0)
                textView.setTextColor(if (textColor==0) Color.BLACK else textColor)

                scrollDirection = getInteger(R.styleable.AutoScrollTextView_scrollDirection,0)

                textView.text=text

            } finally {
                recycle()
            }
            reDraw()
        }
    }

    private fun reDraw() {
        textView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width: Int = textView.width
                if (width > 0) {
                    // removing OnGlobalLayoutListener
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        textView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }

                    myScrollTo(if(scrollDirection==0)width else -width,0, text.length,true)

                }
            }
        })

    }

    private fun myScrollTo(x:Int, y:Int, contentLength:Int,reset:Boolean) {

        Log.e("witdth", "$x -  $scrollX")
        val endX = if (reset)-x else x
        val endY = 0
        val xTranslate = ObjectAnimator.ofInt(this, "scrollX", endX)
        val yTranslate = ObjectAnimator.ofInt(this, "scrollY", endY)
        activeAnimator = AnimatorSet()
        activeAnimator.duration = if (reset) 1000L else ( 600 * contentLength / scrollSpeed).toLong()
        activeAnimator.play(xTranslate)
        activeAnimator.startDelay=1000
        activeAnimator.interpolator=LinearInterpolator()
        activeAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(arg0: Animator) {
                // TODO Auto-generated method stub
            }

            override fun onAnimationRepeat(arg0: Animator) {
                // TODO Auto-generated method stub
            }

            override fun onAnimationEnd(arg0: Animator) {
                if (reset)
                {

                    myScrollTo(x,y,contentLength,false)
                }
                else
                    myScrollTo(x,y,contentLength,true)
            }

            override fun onAnimationCancel(arg0: Animator) {
                // TODO Auto-generated method stub
            }
        })
        activeAnimator.start()
    }

    fun setText(newText:String){
        activeAnimator.cancel()
        text=""
        repeat(10) {
            text+=newText}
        textView.text=text
        reDraw()
    }

}