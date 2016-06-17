package com.britesky.ipos.handwriting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

//import com.britesky.ipos.R;
import com.britesky.ipos.utils.VariablesComm;
import com.britesky.payanywhere.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class FingerDrawView extends View {

	public Paint paint;
	public Bitmap bitmap;
	private Canvas canvas;
	private Path path;
	private Paint bitmapPaint;
	//private Paint borderPaint;
	private List<Point> points;
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private Context mContext;
	private static final String LOGTAG = "FingerDrawView";
	protected static final Point STROKE_END = new Point(255, 0);
	protected static final Point CHAR_END = new Point(255, 255);

	public FingerDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(getResources().getColor(R.color.pay_color));
		paint.setStyle(Paint.Style.STROKE);

		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(5);

	}

	public void setBitmapSize(int width, int height) {
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		canvas = new Canvas(bitmap);
		path = new Path();
		bitmapPaint = new Paint(Paint.DITHER_FLAG);

		points = new Vector<Point>();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public void saveMyBitmap(String bitName, Bitmap mBitmap) {

		File f = new File(this.mContext.getFilesDir() + "/" + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("在保存图片时出错", e.toString());

		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		System.out.println("--------------onDraw \n");
		canvas.drawColor(R.color.white);
		//canvas.drawRect(0, 0, 255, 255, borderPaint);
		canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
		canvas.drawPath(path, paint);
	}

	private void touch_start(float x, float y) {

		path.reset();
		path.moveTo(x, y);
		mX = x;
		mY = y;
		points.add(new Point((int) x, (int) y));
	}

	private void touch_move(float x, float y) {
		VariablesComm.TOUCH_MOVE_TAG = 2;
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		points.add(new Point((int) x, (int) y));
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		path.lineTo(mX, mY);
		// commit the path to our offscreen
		canvas.drawPath(path, paint);
		// kill this so we don't double draw
		path.reset();
		points.add(STROKE_END);
		//saveMyBitmap("d",bitmap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	public void resetView() {
		points.clear();
		bitmap.eraseColor(Color.TRANSPARENT);
		invalidate();
		System.out.println("--------------reset \n");
	}

	protected void dump() {
		for (Point pt : points) {
			Log.v(LOGTAG, String.format("(%d, %d)", pt.x, pt.y));
		}
	}

	protected List<Point> getPoints() {
		return points;
	}
}
