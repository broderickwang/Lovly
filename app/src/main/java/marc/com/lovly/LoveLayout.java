package marc.com.lovly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by 王成达
 * Date: 2017/9/13
 * Time: 09:18
 * Version: 1.0
 * Description:点赞的效果
 * Email:wangchengda1990@gmail.com
 **/
public class LoveLayout extends RelativeLayout {

	private Random mRandom;

	private int[] mImagesRes;

	private int mWidth,mHeight;

	private int mImgWidth,mImgHeight;

	private Interpolator[] interpolators;

	public LoveLayout(Context context) {
		this(context, null);
	}

	public LoveLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mRandom = new Random();

		mImagesRes = new int[]{R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow};

		interpolators = new Interpolator[]{new AccelerateDecelerateInterpolator()
				,new AccelerateInterpolator(),new DecelerateInterpolator(),new LinearInterpolator()};

		Drawable drawable = ContextCompat.getDrawable(context,R.drawable.pl_blue);
		mImgHeight = drawable.getIntrinsicHeight();
		mImgWidth = drawable.getIntrinsicWidth();
	}

	public void addLove() {
		ImageView lovely = new ImageView(getContext());
		//随机设置图片资源
		lovely.setImageResource(mImagesRes[mRandom.nextInt(mImagesRes.length)]);

		//设置图片在底部中心位置
		RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
				, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_PARENT_BOTTOM);
		params.addRule(CENTER_HORIZONTAL);
		lovely.setLayoutParams(params);
		addView(lovely);

		setAnimaior(lovely);
//		lovely.setAnimation(new ScaleAnimation(0.3f,1,0.3f,1));
//		ObjectAnimator.ofFloat(lovely,"alpha",0.3f,1.0f);
//		lovely
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
	}

	private void setAnimaior(final ImageView imageView) {
		//加载动画
		AnimatorSet allSet = new AnimatorSet();

		AnimatorSet loadSet = new AnimatorSet();

		ObjectAnimator alphaA = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1.0f);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1.0f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1.0f);
		loadSet.playTogether(alphaA, scaleX, scaleY);
		loadSet.setDuration(350);

		allSet.playSequentially(loadSet, getBizerAnimator(imageView));
		allSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				removeView(imageView);
			}
		});
		allSet.start();

	}

	//运动动画
	private Animator getBizerAnimator(final ImageView imageView) {

		PointF point0 = new PointF(mWidth/2-mImgWidth/2,mHeight-mImgHeight);
		PointF point1 = getPointY(0);
		PointF point2 = getPointY(1);
		PointF point3 = new PointF(mRandom.nextInt(mWidth)-mImgWidth,0);

		//运动点以左上角为标准，不是以中心点
		LovelyType type = new LovelyType(point1,point2);
		ValueAnimator animator = ObjectAnimator.ofObject(type,point0,point3);
		animator.setDuration(4000);
		//差值器
		animator.setInterpolator(interpolators[mRandom.nextInt(interpolators.length)]);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				PointF p = (PointF) animation.getAnimatedValue();
				imageView.setX(p.x);
				imageView.setY(p.y);
				//透明度 getAnimatedFraction获取的就是t
				float t = animation.getAnimatedFraction();
				imageView.setAlpha(1-t + 0.2f);
			}
		});

		return animator;
	}

	private PointF getPointY(int index) {
		return  new PointF(mRandom.nextInt(mWidth)-mImgWidth,mRandom.nextInt(mHeight/2)+ index*mHeight/2);
	}
}