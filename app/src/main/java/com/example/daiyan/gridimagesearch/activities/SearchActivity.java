package com.example.daiyan.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.daiyan.gridimagesearch.R;
import com.example.daiyan.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.daiyan.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private EditText etQuery;
    private GridView gvResult;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String size="";
    private String color="";
    private String type="";
    private String site="";
    private int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this,imageResults);
        gvResult.setAdapter(aImageResults);

    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResult = (GridView) findViewById(R.id.gvResult);
        gvResult.setOnItemClickListener(this);
        gvResult.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                generatorImageJSON(page);
            }
        });
    }

    public void onImageSearch(View v){
        imageResults.clear(); // clear the existing images from the array (in cases where its a new search);
        generatorImageJSON(1);

    }

    private void generatorImageJSON(int page) {
        start = (page - 1 ) * 8;
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for " + query, Toast.LENGTH_SHORT).show();
        String searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+ query + "&rsz=8";
        searchURL += "&imgcolor=" + color;
        searchURL += "&imgsz=" + size;
        searchURL += "&imgtype=" + type;
        searchURL += "&as_sitesearch=" + site;
        searchURL += "&start=" + Integer.toString(start);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON = null;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");

                    // When you make to the adapter, it does modify the underlying data
                    /*
                    imageResults.addAll(ImageResult.fromJSONArray(imageResultsJSON));
                    aImageResults.notifyDataSetChanged();
                     */
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, ImageDisplayActivity.class);
        ImageResult result = imageResults.get(position);
        i.putExtra("result", result);
        startActivity(i);
    }

    public void goSettings(){
        Intent i = new Intent(this, AdvancedSearchSettingActivity.class);
        i.putExtra("size",size);
        i.putExtra("type",type);
        i.putExtra("color",color);
        i.putExtra("site",site);
        startActivityForResult(i, 3458);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent it){
        if(resultCode == RESULT_OK){
            size = it.getStringExtra("size");
            type = it.getStringExtra("type");
            color = it.getStringExtra("color");
            site = it.getStringExtra("site");
        }
    }
}
