package com.example.bookmanager;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ReaderActivity extends Activity implements OnClickListener{
	private EditText editReaderId, editReaderName, editReaderAge;
	private Spinner spinnerReaderSex, spinnerReaderFaculty;
	private String[] sexs = new String[]{"男", "女"};
	private String[] faculties = new String[]{"计信学院", "土建学院", "机电学院"};
	private Button buttonAdd, buttonDelete, buttonModify,buttonView;
	private SQLiteDatabase readerDb;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader);
		editReaderId = (EditText)findViewById(R.id.editReaderId);
		editReaderName = (EditText)findViewById(R.id.editReaderName);
		editReaderAge = (EditText)findViewById(R.id.editReaderAge);
		spinnerReaderSex = (Spinner)findViewById(R.id.spinnerReaderSex);
		spinnerReaderFaculty = (Spinner)findViewById(R.id.spinnerReaderFaculty);
		buttonAdd = (Button)findViewById(R.id.buttonAdd);
		buttonDelete = (Button)findViewById(R.id.buttonDelet);
		buttonModify = (Button)findViewById(R.id.buttonModify);
		buttonView = (Button)findViewById(R.id.buttonView);
		
		ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(ReaderActivity.this, 
				android.R.layout.simple_list_item_1, sexs);
		ArrayAdapter<String> facultyAdapter = new ArrayAdapter<String>(ReaderActivity.this, 
				android.R.layout.simple_list_item_1, faculties);
		sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerReaderSex.setAdapter(sexAdapter);
		spinnerReaderFaculty.setAdapter(facultyAdapter);
		
		spinnerReaderSex.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spinnerReaderFaculty.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		buttonAdd.setOnClickListener(this);
		buttonDelete.setOnClickListener(this);
		buttonModify.setOnClickListener(this);
		buttonView.setOnClickListener(this);
		readerDb=openOrCreateDatabase("ReaderDB", Context.MODE_PRIVATE, null);
		readerDb.execSQL("CREATE TABLE IF NOT EXISTS reader" +
				"(readerid VARCHAR, readername VARCHAR, readersex VARCHAR, readerage VARCHAR, readerfaculty VARCAHR);");
	}
	
	public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
	
	public void clearText()
    {
		editReaderId.setText("");
    	editReaderName.setText("");
    	editReaderAge.setText("");
    }

	@Override
	public void onClick(View v){
		
		if(v==buttonAdd){
			if(editReaderId.getText().toString().trim().length()==0||
					editReaderName.getText().toString().trim().length()==0||
					spinnerReaderSex.getSelectedItem().toString().trim().length()==0||
					editReaderAge.getText().toString().trim().length()==0||
					spinnerReaderFaculty.getSelectedItem().toString().trim().length()==0){
				showMessage("错误", "请输入全部数据");
    			return;
			}
			readerDb.execSQL("INSERT INTO reader VALUES('"+editReaderId.getText().toString()+
					"','"+editReaderName.getText().toString()+
					"','"+spinnerReaderSex.getSelectedItem().toString()+
					"','"+editReaderAge.getText().toString()+
 				  "','"+spinnerReaderFaculty.getSelectedItem().toString()+"');");
			showMessage("成功", "数据已添加");
			clearText();
		}
		
		if(v==buttonDelete){
			if(editReaderId.getText().toString().trim().length()==0)
			{
				showMessage("错误", "请输入ID");
				return;
			}
			Cursor c=readerDb.rawQuery("SELECT * FROM reader WHERE readerid='"+editReaderId.getText()+"'", null);
			if(c.moveToFirst())
    		{
    			readerDb.execSQL("DELETE FROM reader WHERE readerid='"+editReaderId.getText()+"'");
    			showMessage("成功", "数据已删除");
    		}
			else
    		{
    			showMessage("错误", "无效ID");
    		}
    		clearText();
		}
		if(v==buttonModify){
			if(editReaderId.getText().toString().trim().length()==0)
    		{
    			showMessage("错误", "请输入ID");
    			return;
    		}
			Cursor c=readerDb.rawQuery("SELECT * FROM reader WHERE readerid='"+editReaderId.getText()+"'", null);
			if(c.moveToFirst())
    		{
				readerDb.execSQL("UPDATE reader SET readername='"+editReaderName.getText()+
						"',readersex='"+spinnerReaderSex.getSelectedItem()+
						"',readerage='"+editReaderAge.getText()+
						"',readerfaculty='"+spinnerReaderFaculty.getSelectedItem()+
    					"' WHERE readerid='"+editReaderId.getText()+"'");
    			showMessage("成功", "数据已修改");
    		}
			else
    		{
    			showMessage("错误", "无效ID");
    		}
			clearText();
		}
		if(v==buttonView){
			Cursor c=readerDb.rawQuery("SELECT * FROM reader", null);
			if(c.getCount()==0)
    		{
    			showMessage("错误", "无数据");
    			return;
    		}
			StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("ID: "+c.getString(0)+"\n");
    			buffer.append("姓名: "+c.getString(1)+"\n");
    			buffer.append("性别: "+c.getString(2)+"\n");
    			buffer.append("年龄: "+c.getString(3)+"\n");
    			buffer.append("院系: "+c.getString(4)+"\n\n");
    		}
    		showMessage("读者信息", buffer.toString());
		}
	}

}
