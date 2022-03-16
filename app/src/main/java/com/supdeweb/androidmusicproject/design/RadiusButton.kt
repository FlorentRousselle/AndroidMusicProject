package com.supdeweb.androidmusicproject.design

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.databinding.ComponentRadiusButtonBinding


class RadiusButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    /**
     * binding
     */
    private val binding: ComponentRadiusButtonBinding =
        ComponentRadiusButtonBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    /**
     * interface Listener
     */
    interface RadiusButtonListener {
        fun onUserClickOnItem()
    }

    /**
     * implementation listener
     */
    private var listener: RadiusButtonListener? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RadiusButton,
            0, 0
        ).apply {

            try {
                setLabel()
                setStartIcon()
                setFirstSubtitle()
            } finally {
                recycle()
            }
        }

        initActionButton()
    }

    /**
     * set the component listener
     */
    fun setListener(listener: RadiusButtonListener) {
        this.listener = listener
    }


    /**
     * display a start icon
     */
    private fun TypedArray.setStartIcon() {
        val url = getString(R.styleable.RadiusButton_start_icon)
        setImage(url)
    }


    /**
     * set the label
     */
    private fun TypedArray.setLabel() {
        val label = getString(R.styleable.RadiusButton_textLabel)
        binding.componentRadiusButtonTvLabel.text = label
    }

    /**
     * set the first subtitle
     */
    private fun TypedArray.setFirstSubtitle() {
        val label = getString(R.styleable.RadiusButton_first_subtitle)
        binding.componentRadiusButtonTvFirstSubtitle.text = label
    }

    fun customizeButton(
        textLabel: String? = null,
        firstLabel: String? = null,
        imageUrl: String? = null,
        isArtist: Boolean = false,
    ) {
        updateButtonFrom(view = binding.componentRadiusButtonTvLabel, customLabel = textLabel)
        updateButtonFrom(
            view = binding.componentRadiusButtonTvFirstSubtitle,
            customLabel = firstLabel
        )
        updateButtonFrom(view = binding.componentRadiusButtonIv,
            imageUrl = imageUrl,
            isArtist = isArtist)
    }

    private fun updateButtonFrom(
        view: View,
        customLabel: String? = null,
        imageUrl: String? = null,
        isArtist: Boolean = false,
    ) {
        when (view) {
            is TextView -> {
                view.text = customLabel
                if (customLabel != null) {
                    view.visibility = View.VISIBLE
                }
            }
            is ImageView -> {
                imageUrl?.let {
                    setImage(it, isArtist)
                }
            }
        }
    }

    private fun initActionButton() {
        binding.componentRadiusButtonClRoot.setOnClickListener {
            listener?.onUserClickOnItem()
        }
    }

    private fun setImage(imageUrl: String?, isArtist: Boolean = false) {
        if (isArtist) {
            val radius =
                binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_xl)
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
            Glide.with(binding.componentRadiusButtonIv)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .transform(RoundedCorners(radius), CenterCrop())
                .into(binding.componentRadiusButtonIv)
        } else {
            val radius =
                binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_m)
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
            Glide.with(binding.componentRadiusButtonIv)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .transform(RoundedCorners(radius), CenterCrop())
                .into(binding.componentRadiusButtonIv)
        }
    }
}
