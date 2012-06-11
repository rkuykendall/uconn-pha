package edu.uconn.pha;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageViewerActivity extends Activity {

	private static final String TAG = ImageViewerActivity.class.getName();

	private Bundle bundle;
	private ImageView imageView;
	private URL url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// retrieve the parameters passed to this view
		bundle = getIntent().getExtras();

		try {
			// initialize image from the provided URL
			url = new URL(bundle.getString("url"));

			// create the image view
			imageView = new ImageView(this);

			// set the image view resource from the URL
			imageView.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));

			// set the activity content to be the image view
			setContentView(imageView);
		} catch (MalformedURLException e) {
			Log.e(TAG, "Error :: Malformed URL Provided", e);
		} catch (IOException e) {
			Log.e(TAG, "Error :: Cannot Create Bitmap", e);
		}
	}
}
