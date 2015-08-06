package com.example.daiyan.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.daiyan.gridimagesearch.R;

import java.util.Arrays;


public class AdvancedSearchSettingActivity extends ActionBarActivity {

    private Spinner spImageSize, spColorFilter, spImageType;
    private String[] imageSize = {"small", "medium", "large", "xlarge"};
    private String[] imageType = {"faces","photo","clipart","lineart"};
    private String[] colorFilter = {"black","blue","brown","gray","green","red"};
    private ArrayAdapter<String> aImageSize, aImageType, aColorFilter;
    private EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search_setting);
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSite = (EditText) findViewById(R.id.etSiteFilter);

        aImageSize = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,imageSize);
        aImageType = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,imageType);
        aColorFilter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,colorFilter);
        spImageType.setAdapter(aImageType);
        spImageSize.setAdapter(aImageSize);
        spColorFilter.setAdapter(aColorFilter);

        Intent i = this.getIntent();
        String size = i.getStringExtra("size");
        String type = i.getStringExtra("type");
        String site = i.getStringExtra("site");
        String color = i.getStringExtra("color");

        spImageSize.setSelection(Arrays.asList(imageSize).indexOf(size));
        spColorFilter.setSelection(Arrays.asList(colorFilter).indexOf(color));
        spImageType.setSelection(Arrays.asList(imageType).indexOf(type));
        etSite.setText(site);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advanced_search_setting, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveSetting(View v){
        Intent i = new Intent();
        String size,color,type,site;
        size = spImageSize.getSelectedItem().toString();
        color=spColorFilter.getSelectedItem().toString();
        type=spImageType.getSelectedItem().toString();
        site=etSite.getText().toString();

        i.putExtra("size", size);
        i.putExtra("color",color);
        i.putExtra("type",type);
        i.putExtra("site",site);

        setResult(RESULT_OK,i);
        finish();
    }
}
