package edu.uconn.pha;

import edu.uconn.model.DemographicInfo;
import edu.uconn.model.Height;
import edu.uconn.model.Person;
import edu.uconn.model.Weight;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	private static final String TAG = HomeActivity.class.getName();

	private DemographicInfo basicDemographicInfo;
	private Height height;
	private ImageView imageView;
	private Person person;
	private String fullName;
	private TextView textView;
	private Weight weight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    Intent launchingIntent = getActivity().getIntent();
	    // String content = launchingIntent.getData().toString();
	 
	    View view = inflater.inflate(R.layout.home_fragment, container, false);
//	    viewer.loadUrl(content);
//	 
	    return view;
	}

}
