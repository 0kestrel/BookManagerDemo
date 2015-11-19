package com.example.bookmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity{
	private Button buttonReader;
	private Button buttonBook;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		buttonReader = (Button)findViewById(R.id.buttonReader);
		buttonBook = (Button)findViewById(R.id.buttonBook);
		
		buttonReader.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SecondActivity.this, ReaderActivity.class);
				startActivity(intent);
			}
			
		});
		
		buttonBook.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SecondActivity.this, BookActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	
	protected void dialog(){
    	AlertDialog.Builder exit = new AlertDialog.Builder(SecondActivity.this);
    	exit.setTitle("系统提示");
    	exit.setMessage("请问是否退出");
    	exit.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SecondActivity.this.finish();
			}
		});
    	exit.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
    	exit.create().show();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
    		dialog();
    		return false;
    	}
    	return false;
    }

}
