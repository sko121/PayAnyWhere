package com.britesky.payanywhere.ui.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.britesky.ipos.utils.VariablesComm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {
	private Context mContext;
	private static final float i = 4.0F;
	public boolean a = false;
	private Bitmap bitmap;
	private Canvas canvas;
	private Path path = new Path();

	public Paint paint = new Paint();
	private Paint bitmapPaint = new Paint(Paint.DITHER_FLAG);;

	private float mX, mY;
	private List<Point> points;
	private static final float TOUCH_TOLERANCE = 4;
	protected static final Point STROKE_END = new Point(255, 0);
	protected static final Point CHAR_END = new Point(255, 255);

	public PaintView(Context context) {
		this(context, null);
		mContext = context;
	}

	public PaintView(Context context, AttributeSet paramAttributeSet) {
		this(context, paramAttributeSet, true);
		mContext = context;
	}

	public PaintView(Context paramContext, AttributeSet paramAttributeSet,
			boolean paramBoolean) {
		super(paramContext, paramAttributeSet);
		this.paint.setAntiAlias(true);
		this.paint.setDither(true);
		this.paint.setColor(-16777216);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(5.0F);
	}

	public void setBitmapSize(int width, int height) {
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		path = new Path();
		bitmapPaint = new Paint(Paint.DITHER_FLAG);
		points = new Vector<Point>();
	}

	public void clear() {
		this.a = false;
		this.bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_4444);
		this.canvas = new Canvas(bitmap);
		invalidate();
	}

	public byte[] getSignatureByteArray() {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		this.bitmap.compress(Bitmap.CompressFormat.PNG, 0,
				localByteArrayOutputStream);
		byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
		try {
			localByteArrayOutputStream.flush();
			localByteArrayOutputStream.close();
			return arrayOfByte;
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}

	public void saveMyBitmap(String bitName) {

		File f = new File(mContext.getFilesDir() + "/" + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("err save pic", e.toString());

		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
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
	
	protected void onDraw(Canvas paramCanvas) {
		paramCanvas.drawColor(0);
		paramCanvas.drawBitmap(bitmap, 0.0F, 0.0F, bitmapPaint);
		paramCanvas.drawPath(path, paint);
	}

	protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		bitmap = Bitmap.createBitmap(paramInt1, paramInt2,
				Bitmap.Config.ARGB_4444);
		canvas = new Canvas(bitmap);
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
		canvas.drawPoint(mX, mY, paint);
		// kill this so we don't double draw
		path.reset();
		points.add(STROKE_END);
		// saveMyBitmap("d",bitmap);
	}

}