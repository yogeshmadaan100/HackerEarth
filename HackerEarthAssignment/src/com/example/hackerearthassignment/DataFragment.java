package com.example.hackerearthassignment;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DataFragment extends Fragment implements OnItemClickListener{
	public static final String TYPE = "TYPE";
    private ListView mListView;
	private RequestQueue mVolleyQueue;
	private DataEfficientAdapter mAdapter;
	private final String TAG_REQUEST = "MY_TAG";
	private List<DataModel> mDataList;
	StringRequest stringRequest;
	SwipeRefreshLayout mSwipeRefreshLayout;
	ViewGroup rootView;
	ProgressDialog pDialog;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=(ViewGroup)inflater.inflate(R.layout.layout_main, container,false);
		 	mListView = (ListView) rootView.findViewById(R.id.activity_main_listview);
		 	mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
	      	Log.e("view", "created");
		 	mListView.setOnItemClickListener(this);
	        mDataList=new ArrayList<DataModel>();
	       
		return rootView;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// Initialise Volley Request Queue. 
				mVolleyQueue = Volley.newRequestQueue(getActivity());
				pDialog=new ProgressDialog(getActivity());
				pDialog.setMessage("Parsing Response");
				pDialog.show();
				int max_cache_size = 1000000;
				//mAdapter = new DataEfficientAdapter(getActivity(),mDataList);
				//mListView.setAdapter(mAdapter);
				
				createSampleGsonRequest();
				 mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
			        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			            @Override
			            public void onRefresh() {
			            	createSampleGsonRequest();
			                new Handler().postDelayed(new Runnable() {
			                    @Override
			                    public void run() {
			                        mAdapter = new DataEfficientAdapter(getActivity(),mDataList);
			                        mListView.setAdapter(mAdapter);
			                        
			                        mSwipeRefreshLayout.setRefreshing(false);
			                    }
			                }, 10000);
			            }
			        });
	}
	

    
	public void createSampleGsonRequest() {
		// TODO Auto-generated method stub

		String url = "https://www.hackerearth.com/api/events/upcoming/?format=json";
		//Uri.Builder builder = Uri.parse(url).buildUpon();
		stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				parseFlickrImageResponse(response);
				mAdapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
				// For AuthFailure, you can re login with user credentials.
				// For ClientError, 400 & 401, Errors happening on client side when sending api request.
				// In this case you can check how client is forming the api and debug accordingly.
				// For ServerError 5xx, you can do retry or handle accordingly.
				if( error instanceof NetworkError) {
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
				} else if( error instanceof NoConnectionError) {
				} else if( error instanceof TimeoutError) {
				}

				Log.e("volley error is ", ""+error.getMessage());
				showToast("It Seems that internet is not working");
				pDialog.dismiss();
			}
		});
	
		stringRequest.setShouldCache(true);
		stringRequest.setTag(TAG_REQUEST);	
		mVolleyQueue.add(stringRequest);
	}
	private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	private void parseFlickrImageResponse(String response) {
		
		mDataList.clear();
		//Log.e("image Response", ""+response);
		DataController mController=new DataController(response);
		mController.init();
		for(DataModel str:mController.findAll())
		{
			DataModel model = new DataModel();
			model.setTitle(str.getTitle());
			model.setDescription(str.getDescription());
			model.setStatus(str.getStatus());
			model.setChallenge_type(str.getChallenge_type());
			model.setUrl(str.getUrl());
			mDataList.add(model);
		}
		 mAdapter = new DataEfficientAdapter(getActivity(),mDataList);
         mListView.setAdapter(mAdapter);
         
        
		mSwipeRefreshLayout.setRefreshing(false);
		pDialog.dismiss();
}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.e("url string is ", ""+mDataList.get(position).getUrl());
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataList.get(position).getUrl()));
		startActivity(browserIntent);
		
	}
	public View getRootView()
	{
		return rootView;
	}
	
}
