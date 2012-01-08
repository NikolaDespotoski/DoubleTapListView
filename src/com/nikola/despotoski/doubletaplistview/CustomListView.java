package com.nikola.despotoski.doubletaplistview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class CustomListView extends ListView{

	
	private boolean mTookFirstEvent=false;
    private CountDownTimer mCountdownTillNextEvent;
    private boolean mTimerRunning = false;
    private int mPositionHolder=-1;
	private boolean mTookSecondEvent;
	private int mPosition=-1;
	private OnItemDoubleTapLister mOnDoubleTapListener = null;
	private AdapterView<?> mParent = null;
    private View mView = null;
    private long mId= 12315;
    
	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		removeSelector();
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		removeSelector();
	}

	public CustomListView(Context context) {
		super(context);
		removeSelector();//optional
	}

	
	
	
	
	public void setOnItemDoubleClickListener(OnItemDoubleTapLister listener )
	{
		mOnDoubleTapListener = listener;
		if(mOnDoubleTapListener==null)
			throw new IllegalArgumentException("OnItemDoubleTapListener cannot be null");
		else
		{
			/*If the OnItemDoubleTapListener is not null, register the default onItemClickListener to get parameters */
		setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            mParent = parent;
	            mView = view;
	            mPosition = position;
	            mId=id;
	            if(!mTookFirstEvent) /* Testing if first tap occured */
	            {
	                mPositionHolder=position;
	                /*this will hold the position variable from first event. In case user presses any other item (position)*/
	                mTookFirstEvent=true;
	                /* first tap acquired*/
	            }
	            if(mCountdownTillNextEvent==null && !mTimerRunning)
	            {
	                mTimerRunning=true;  //signaling that timer is running, (not needed)
	                mCountdownTillNextEvent = new CountDownTimer(ViewConfiguration.getDoubleTapTimeout(), 1) //default time in milliseconds between single and double tap (see: repo of ViewConfiguration)
	                {
	                     @Override
	                public void onTick(long millisUntilFinished) {
	                   
	                }
	                 @Override
	                public void onFinish() {   /*when time expires reverting to initial state */
	                     mTookFirstEvent=false;  
	                     mCountdownTillNextEvent=null;
	                     mTimerRunning=false;
	                    
	                     if(!mTookSecondEvent) /* no second tap */                    	
	                    	 mOnDoubleTapListener.OnSingleTap(mParent, mView, mPosition, mId);  /* send call to all who *implements* this*/
	                   /* Instead onSingleTap we can call default onItemClickListener*/
	                     else
	                    	 mTookSecondEvent=false; 
	                }
	            };
	            mCountdownTillNextEvent.start();  //firing the countdown
	    }
	    else
	    { 
	            if(mCountdownTillNextEvent!=null && mTimerRunning && mPositionHolder == position)
	            {
	                mTookFirstEvent=false;          //reverting when to non clicked state when double tap on the listview item occurred
	                mCountdownTillNextEvent=null;
	                mTimerRunning=false;
	                mTookSecondEvent=true; //clutter, mCountdownTillNextEvent!=null indicates that timer is running
	                mPosition = position;
	                /* send call to all who *implements* this*/
	                mOnDoubleTapListener.OnDoubleTap(mParent, mView, mPosition, mId);
	            }
	    }

				
			}});
		}
	
	}
	public void removeSelector()
	{
		setSelector(android.R.color.transparent); // optional
		//TODO solution for double tap selector needed.
		
	}

	public interface OnItemDoubleTapLister
	{
		public void OnDoubleTap(AdapterView<?> parent, View view, int position,
				long id);
		public void OnSingleTap(AdapterView<?> parent, View view, int position,
				long id);
	}

	
	
}
