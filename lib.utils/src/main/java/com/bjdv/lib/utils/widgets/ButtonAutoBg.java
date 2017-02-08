package com.bjdv.lib.utils.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Applies a pressed state color filter or disabled state alpha for the button's background
 * drawable.
 *
 * @author shiki
 */
public class ButtonAutoBg extends Button {

	public ButtonAutoBg(Context context) {
		super(context);
		init();
	}

	public ButtonAutoBg(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ButtonAutoBg(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void setBackground(Drawable background) {
		super.setBackground(background);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackgroundDrawable(Drawable d) {
		// Replace the original background drawable (e.g. image) with a LayerDrawable that
		// contains the original drawable.
		SAutoBgButtonBackgroundDrawable layer = new SAutoBgButtonBackgroundDrawable(d);
		super.setBackgroundDrawable(layer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackgroundResource(int resid) {
		// Replace the original background drawable (e.g. image) with a LayerDrawable that
		// contains the original drawable.
		SAutoBgButtonBackgroundDrawable layer = new SAutoBgButtonBackgroundDrawable(getContext().getResources().getDrawable(resid));
		super.setBackgroundDrawable(layer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackgroundColor(int color) {
		// Replace the original background drawable (e.g. image) with a LayerDrawable that
		// contains the original drawable.
		SAutoBgButtonBackgroundDrawable layer = new SAutoBgButtonBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(color)));
		super.setBackgroundDrawable(layer);
	}

	/**
	 * The stateful LayerDrawable used by this button.
	 */
	protected class SAutoBgButtonBackgroundDrawable extends LayerDrawable {

		// The color filter to apply when the button is pressed
		protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
		// Alpha value when the button is disabled
		protected int _disabledAlpha = 100;

		public SAutoBgButtonBackgroundDrawable(Drawable d) {
			super(new Drawable[] { d });
		}

		@Override
		protected boolean onStateChange(int[] states) {
			boolean enabled = false;
			boolean pressed = false;

			for (int state : states) {
				if (state == android.R.attr.state_enabled)
					enabled = true;
				else if (state == android.R.attr.state_pressed)
					pressed = true;
			}

			mutate();
			if (enabled && pressed) {
				setColorFilter(_pressedFilter);
			} else if (!enabled) {
				setColorFilter(null);
				setAlpha(_disabledAlpha);
			} else {
				setColorFilter(null);
				setAlpha(255);
			}

			invalidateSelf();

			return super.onStateChange(states);
		}

		@Override
		public boolean isStateful() {
			return true;
		}
	}

}