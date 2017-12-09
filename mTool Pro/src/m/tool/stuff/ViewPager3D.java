package m.tool.stuff;

import m.tool.pro.R;
import m.tool.stuff.animation.Animator;
import m.tool.stuff.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

@SuppressLint("FloatMath")
public class ViewPager3D extends ViewPager {
	final static float DEFAULT_OVERSCROLL_ROTATION = 2;
	final static int DEFAULT_OVERSCROLL_TRANSLATION = 100;
	final static int DEFAULT_SWIPE_TRANSLATION = 100;
	final static float DEFAULT_SWIPE_ROTATION = 3;
	final private static int DEFAULT_OVERSCROLL_ANIMATION_DURATION = 350;
	final private static boolean DEFAULT_ANIMATE_ALPHA = true;
	private final static int INVALID_POINTER_ID = -1;
	private final static double RADIANS = 180f / Math.PI;

	private class OverscrollEffect {
		private float mOverscroll;
		private Animator mAnimator;

		private void setPull(final float deltaDistance) {
			mOverscroll = deltaDistance;
			invalidateVisibleChilds(mLastPosition);
		}

		private void onRelease() {
			startAnimation(0);
		}

		private void startAnimation(final float target) {
			mAnimator = ObjectAnimator.ofFloat(this, "pull", mOverscroll,
					target);
			mAnimator.setInterpolator(new DecelerateInterpolator());
			mAnimator.setDuration((long) (mOverscrollAnimationDuration));
			mAnimator.start();
		}

		private boolean isOverscrolling() {
			if (mScrollPosition == 0 && mOverscroll < 0) {
				return true;
			}
			if (getAdapter() != null) {
				final boolean isLast = (getAdapter().getCount() - 1) == mScrollPosition;
				if (isLast && mOverscroll > 0) {
					return true;
				}
			}
			return false;
		}

	}

	final private OverscrollEffect mOverscrollEffect = new OverscrollEffect();
	final private Camera mCamera = new Camera();
	private OnPageChangeListener mScrollListener;
	private float mLastMotionX;
	private int mActivePointerId;
	private int mScrollPosition;
	private float mScrollPositionOffset;
	final private int mTouchSlop;
	private float mOverscrollRotation;
	private float mSwipeRotation;
	private int mOverscrollTranslation;
	private int mSwipeTranslation;
	private int mOverscrollAnimationDuration;
	private boolean mAnimateAlpha;

	public ViewPager3D(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStaticTransformationsEnabled(true);
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = ViewConfigurationCompat
				.getScaledPagingTouchSlop(configuration);
		super.setOnPageChangeListener(new MyOnPageChangeListener());
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.ViewPager3D);
		mOverscrollRotation = a.getFloat(
				R.styleable.ViewPager3D_overscroll_rotation,
				DEFAULT_OVERSCROLL_ROTATION);
		mSwipeRotation = a.getFloat(R.styleable.ViewPager3D_swipe_rotation,
				DEFAULT_SWIPE_ROTATION);
		mSwipeTranslation = a.getInt(R.styleable.ViewPager3D_swipe_translation,
				DEFAULT_SWIPE_TRANSLATION);
		mOverscrollTranslation = a.getInt(
				R.styleable.ViewPager3D_overscroll_translation,
				DEFAULT_OVERSCROLL_TRANSLATION);
		mOverscrollAnimationDuration = a.getInt(
				R.styleable.ViewPager3D_overscroll_animation_duration,
				DEFAULT_OVERSCROLL_ANIMATION_DURATION);
		mAnimateAlpha = a.getBoolean(R.styleable.ViewPager3D_animate_alpha,
				DEFAULT_ANIMATE_ALPHA);
		a.recycle();
	}

	public boolean isAnimateAlpha() {
		return mAnimateAlpha;
	}

	public void setAnimateAlpha(boolean mAnimateAlpha) {
		this.mAnimateAlpha = mAnimateAlpha;
	}

	public int getOverscrollAnimationDuration() {
		return mOverscrollAnimationDuration;
	}

	public void setOverscrollAnimationDuration(int mOverscrollAnimationDuration) {
		this.mOverscrollAnimationDuration = mOverscrollAnimationDuration;
	}

	public int getSwipeTranslation() {
		return mSwipeTranslation;
	}

	public void setSwipeTranslation(int mSwipeTranslation) {
		this.mSwipeTranslation = mSwipeTranslation;
	}

	public int getOverscrollTranslation() {
		return mOverscrollTranslation;
	}

	public void setOverscrollTranslation(int mOverscrollTranslation) {
		this.mOverscrollTranslation = mOverscrollTranslation;
	}

	public float getSwipeRotation() {
		return mSwipeRotation;
	}

	public void setSwipeRotation(float mSwipeRotation) {
		this.mSwipeRotation = mSwipeRotation;
	}

	public float getOverscrollRotation() {
		return mOverscrollRotation;
	}

	public void setOverscrollRotation(float mOverscrollRotation) {
		this.mOverscrollRotation = mOverscrollRotation;
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mScrollListener = listener;
	};

	private void invalidateVisibleChilds(final int position) {
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).invalidate();
		}
	}

	private int mLastPosition = 0;

	private class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (mScrollListener != null) {
				mScrollListener.onPageScrolled(position, positionOffset,
						positionOffsetPixels);
			}
			mScrollPosition = position;
			mScrollPositionOffset = positionOffset;
			mLastPosition = position;
			invalidateVisibleChilds(position);
		}

		public void onPageSelected(int position) {
			if (mScrollListener != null) {
				mScrollListener.onPageSelected(position);
			}
		}

		public void onPageScrollStateChanged(final int state) {
			if (mScrollListener != null) {
				mScrollListener.onPageScrollStateChanged(state);
			}
			if (state == SCROLL_STATE_IDLE) {
				mScrollPositionOffset = 0;
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			mLastMotionX = ev.getX();
			mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
			break;
		}
		case MotionEventCompat.ACTION_POINTER_DOWN: {
			final int index = MotionEventCompat.getActionIndex(ev);
			final float x = MotionEventCompat.getX(ev, index);
			mLastMotionX = x;
			mActivePointerId = MotionEventCompat.getPointerId(ev, index);
			break;
		}
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean callSuper = false;

		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			callSuper = true;
			mLastMotionX = ev.getX();
			mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
			break;
		}
		case MotionEventCompat.ACTION_POINTER_DOWN: {
			callSuper = true;
			final int index = MotionEventCompat.getActionIndex(ev);
			final float x = MotionEventCompat.getX(ev, index);
			mLastMotionX = x;
			mActivePointerId = MotionEventCompat.getPointerId(ev, index);
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (mActivePointerId != INVALID_POINTER_ID) {
				final int activePointerIndex = MotionEventCompat
						.findPointerIndex(ev, mActivePointerId);
				if (activePointerIndex != INVALID_POINTER_ID) {
					final float x = MotionEventCompat.getX(ev,
							activePointerIndex);
					final float deltaX = mLastMotionX - x;
					final float oldScrollX = getScrollX();
					final int width = getWidth();
					final int widthWithMargin = width + getPageMargin();
					final int lastItemIndex = getAdapter().getCount() - 1;
					final int currentItemIndex = getCurrentItem();
					final float leftBound = Math.max(0, (currentItemIndex - 1)
							* widthWithMargin);
					final float rightBound = Math.min(currentItemIndex + 1,
							lastItemIndex) * widthWithMargin;
					final float scrollX = oldScrollX + deltaX;
					if (mScrollPositionOffset == 0) {
						if (scrollX < leftBound) {
							if (leftBound == 0) {
								final float over = deltaX + mTouchSlop;
								mOverscrollEffect.setPull(over / width);
							}
						} else if (scrollX > rightBound) {
							if (rightBound == lastItemIndex * widthWithMargin) {
								final float over = scrollX - rightBound
										- mTouchSlop;
								mOverscrollEffect.setPull(over / width);
							}
						}
					} else {
						mLastMotionX = x;
					}
				}
			} else {
				mOverscrollEffect.onRelease();
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			callSuper = true;
			mActivePointerId = INVALID_POINTER_ID;
			mOverscrollEffect.onRelease();
			break;
		}
		case MotionEvent.ACTION_POINTER_UP: {
			final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = MotionEventCompat.getPointerId(ev,
					pointerIndex);
			if (pointerId == mActivePointerId) {
				final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				mLastMotionX = ev.getX(newPointerIndex);
				mActivePointerId = MotionEventCompat.getPointerId(ev,
						newPointerIndex);
				callSuper = true;
			}
			break;
		}
		}

		if (mOverscrollEffect.isOverscrolling() && !callSuper) {
			return true;
		} else {
			try {
				return super.onTouchEvent(ev);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		if (child.getWidth() == 0) {
			return false;
		}
		final int position = child.getLeft() / child.getWidth();
		final boolean isFirstOrLast = position == 0
				|| (position == getAdapter().getCount() - 1);
		if (mOverscrollEffect.isOverscrolling() && isFirstOrLast) {
			final float dx = getWidth() / 2;
			final int dy = getHeight() / 2;
			t.getMatrix().reset();
			final float translateZ = (float) (mOverscrollTranslation * Math
					.sin(Math.PI * Math.abs(mOverscrollEffect.mOverscroll)));
			final float degrees = 90
					/ mOverscrollRotation
					- (float) ((RADIANS * Math
							.acos(mOverscrollEffect.mOverscroll)) / mOverscrollRotation);

			mCamera.save();
			mCamera.rotateY(degrees);
			mCamera.translate(0, 0, translateZ);
			mCamera.getMatrix(t.getMatrix());
			mCamera.restore();
			t.getMatrix().preTranslate(-dx, -dy);
			t.getMatrix().postTranslate(dx, dy);

			if (mAnimateAlpha) {
				t.setTransformationType(Transformation.TYPE_BOTH);
				t.setAlpha((FloatMath.sin((float) ((1 - Math
						.abs(mOverscrollEffect.mOverscroll)) * Math.PI / 2))));
			}
			child.invalidate();
			return true;
		} else if (mScrollPositionOffset > 0) {

			final float dx = getWidth() / 2;
			final float dy = getHeight() / 2;

			final double degrees;
			if (position > mScrollPosition) {
				if (mAnimateAlpha) {
					t.setTransformationType(Transformation.TYPE_BOTH);
					t.setAlpha((FloatMath.sin((float) (mScrollPositionOffset
							* Math.PI / 2))));
				}
				degrees = -(90 / mSwipeRotation)
						+ (RADIANS * Math.acos(1 - mScrollPositionOffset))
						/ mSwipeRotation;
			} else {
				if (mAnimateAlpha) {
					t.setTransformationType(Transformation.TYPE_BOTH);
					t.setAlpha((FloatMath.sin((float) (mScrollPositionOffset
							* Math.PI / 2 + Math.PI / 2))));
				}
				degrees = (90 / mSwipeRotation)
						- (RADIANS * Math.acos(mScrollPositionOffset))
						/ mSwipeRotation;
			}
			final float translateZ = (mSwipeTranslation * FloatMath
					.sin((float) ((Math.PI) * mScrollPositionOffset)));

			t.getMatrix().reset();
			mCamera.save();
			mCamera.rotateY((float) degrees);
			mCamera.translate(0, 0, translateZ);
			mCamera.getMatrix(t.getMatrix());
			mCamera.restore();
			t.getMatrix().preTranslate(-dx, -dy);
			t.getMatrix().postTranslate(dx, dy);
			child.invalidate();
			return true;
		}
		return false;
	}
}