package marc.com.lovly;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by 王成达
 * Date: 2017/9/13
 * Time: 10:19
 * Version: 1.0
 * Description:贝塞尔曲线的运动路径
 * Email:wangchengda1990@gmail.com
 **/
public class LovelyType implements TypeEvaluator<PointF> {

	private PointF point1,point2;

	public LovelyType(PointF point1, PointF point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	@Override
	public PointF evaluate(float t, PointF point0, PointF point3) {

		PointF pointF = new PointF();

		pointF.x = point0.x*(1-t)*(1-t)*(1-t)
					+ 3*point1.x*t*(1-t)*(1-t)
					+ 3*point2.x*t*t*(1-t)
					+ point3.x*t*t*t;

		pointF.y = point0.y*(1-t)*(1-t)*(1-t)
				+ 3*point1.y*t*(1-t)*(1-t)
				+ 3*point2.y*t*t*(1-t)
				+ point3.y*t*t*t;

		return pointF;
	}
}
