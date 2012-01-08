package com.nikola.despotoski.doubletaplistview;

import java.util.Arrays;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nikola.despotoski.doubletaplistview.CustomListView.OnItemDoubleTapLister;
/* Author: Nikola Despotoski
 * Email: nikola[dot]despotoski(at)gmail[dot]com
 * 
 *   Copyright (c) 2012 Nikola Despotoski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class DoubleTapListViewActivity extends Activity implements OnItemDoubleTapLister {
	    private CustomListView mListView = null;
	    private List<String> mList = null;
	    private ArrayAdapter<String> mAdapter = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new CustomListView(this);
        mListView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setContentView(mListView);
        mList= Arrays.asList(getResources().getStringArray(R.array.planets));
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemDoubleClickListener(this);
    }
	@Override
	public void OnDoubleTap(AdapterView<?> parent, View view, int position,
			long id) {
		 Toast.makeText(getApplicationContext(), "Double tap occured on "+mList.get(position), Toast.LENGTH_LONG).show();
		
	}
	@Override
	public void OnSingleTap(AdapterView<?> parent, View view, int position,
			long id) {
		 Toast.makeText(getApplicationContext(), "Single tap occured on "+mList.get(position), Toast.LENGTH_LONG).show();
		
	}
}