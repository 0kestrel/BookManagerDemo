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

public class BookActivity extends Activity implements OnClickListener{
	private EditText editBookId, editBookName, editBookAuthor;
	private Spinner spinnerBookPress, spinnerBookSort;
	private String[] presses = new String[]{"�廪��ѧ������", "�������������", "��ѧ������"};
	private String[] sorts = new String[]{"�����", "����", "����ѧ", "��Ȼ��ѧ"};
	private Button buttonAdd, buttonDelete, buttonModify,buttonView;
	private SQLiteDatabase bookDb;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		editBookId = (EditText)findViewById(R.id.editBookId);
		editBookName = (EditText)findViewById(R.id.editBookName);
		editBookAuthor = (EditText)findViewById(R.id.editBookAuthor);
		spinnerBookPress = (Spinner)findViewById(R.id.spinnerBookPress);
		spinnerBookSort = (Spinner)findViewById(R.id.spinnerBookSort);
		buttonAdd = (Button)findViewById(R.id.buttonAdd);
		buttonDelete = (Button)findViewById(R.id.buttonDelet);
		buttonModify = (Button)findViewById(R.id.buttonModify);
		buttonView = (Button)findViewById(R.id.buttonView);
		
		ArrayAdapter<String> pressAdapter = new ArrayAdapter<String>(BookActivity.this, 
				android.R.layout.simple_list_item_1, presses);
		ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(BookActivity.this, 
				android.R.layout.simple_list_item_1, sorts);
		pressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBookPress.setAdapter(pressAdapter);
		spinnerBookSort.setAdapter(sortAdapter);
		
		spinnerBookPress.setOnItemSelectedListener(new OnItemSelectedListener(){

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
		
		spinnerBookSort.setOnItemSelectedListener(new OnItemSelectedListener(){

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
		bookDb=openOrCreateDatabase("BookDB", Context.MODE_PRIVATE, null);
		bookDb.execSQL("CREATE TABLE IF NOT EXISTS book" +
				"(bookid VARCHAR, bookname VARCHAR, bookpress VARCHAR, bookauthor VARCHAR, booksort VARCAHR);");
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
		editBookId.setText("");
    	editBookName.setText("");
    	editBookAuthor.setText("");
    }

	@Override
	public void onClick(View v){
		
		if(v==buttonAdd){
			if(editBookId.getText().toString().trim().length()==0||
					editBookName.getText().toString().trim().length()==0||
					spinnerBookPress.getSelectedItem().toString().trim().length()==0||
					editBookAuthor.getText().toString().trim().length()==0||
					spinnerBookSort.getSelectedItem().toString().trim().length()==0){
				showMessage("����", "������ȫ������");
    			return;
			}
			bookDb.execSQL("INSERT INTO book VALUES('"+editBookId.getText().toString()+
					"','"+editBookName.getText().toString()+
					"','"+spinnerBookPress.getSelectedItem().toString()+
					"','"+editBookAuthor.getText().toString()+
 				  "','"+spinnerBookSort.getSelectedItem().toString()+"');");
			showMessage("�ɹ�", "���������");
			clearText();
		}
		
		if(v==buttonDelete){
			if(editBookId.getText().toString().trim().length()==0)
			{
				showMessage("����", "������ID");
				return;
			}
			Cursor c=bookDb.rawQuery("SELECT * FROM book WHERE bookid='"+editBookId.getText()+"'", null);
			if(c.moveToFirst())
    		{
    			bookDb.execSQL("DELETE FROM book WHERE bookid='"+editBookId.getText()+"'");
    			showMessage("�ɹ�", "������ɾ��");
    		}
			else
    		{
    			showMessage("����", "��ЧID");
    		}
    		clearText();
		}
		if(v==buttonModify){
			if(editBookId.getText().toString().trim().length()==0)
    		{
    			showMessage("����", "������ID");
    			return;
    		}
			Cursor c=bookDb.rawQuery("SELECT * FROM book WHERE bookid='"+editBookId.getText()+"'", null);
			if(c.moveToFirst())
    		{
				bookDb.execSQL("UPDATE book SET bookname='"+editBookName.getText()+
						"',bookpress='"+spinnerBookPress.getSelectedItem()+
						"',bookauthor='"+editBookAuthor.getText()+
						"',booksort='"+spinnerBookSort.getSelectedItem()+
    					"' WHERE bookid='"+editBookId.getText()+"'");
    			showMessage("�ɹ�", "�������޸�");
    		}
			else
    		{
    			showMessage("����", "��ЧID");
    		}
			clearText();
		}
		if(v==buttonView){
			Cursor c=bookDb.rawQuery("SELECT * FROM book", null);
			if(c.getCount()==0)
    		{
    			showMessage("����", "������");
    			return;
    		}
			StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("ID: "+c.getString(0)+"\n");
    			buffer.append("����: "+c.getString(1)+"\n");
    			buffer.append("������: "+c.getString(2)+"\n");
    			buffer.append("����: "+c.getString(3)+"\n");
    			buffer.append("���: "+c.getString(4)+"\n\n");
    		}
    		showMessage("ͼ����Ϣ", buffer.toString());
		}
	}

}
