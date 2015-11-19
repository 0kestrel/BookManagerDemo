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
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private EditText editUser;
	private EditText editPassword;
	private Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editUser = (EditText)findViewById(R.id.editUser);
        editPassword = (EditText)findViewById(R.id.editPassword);
        buttonEnter = (Button)findViewById(R.id.buttonEnter);
        
        buttonEnter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String user = editUser.getText().toString();
	        	String password = editPassword.getText().toString();
	        	if(user.equals("admin")&&password.equals("admin")){
	        		Intent intent = new Intent(MainActivity.this, SecondActivity.class);
	        		startActivity(intent);
	        		MainActivity.this.finish();
	        	}
	        	else{
	        		Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
	        		clearText();
	        	}
			}
        });
    }
    
    public void clearText()
    {
		editUser.setText("");
    	editPassword.setText("");
    }
    
    protected void dialog(){
    	AlertDialog.Builder exit = new AlertDialog.Builder(MainActivity.this);
    	exit.setTitle("系统提示");
    	exit.setMessage("请问是否退出");
    	exit.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				MainActivity.this.finish();
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
