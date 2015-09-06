package com.nac.bai18_ailatrieuphu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private DataBaseManager dataBase;
	private ArrayList<Question> listQ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataBase = new DataBaseManager(this);
		// insertDataUser("Android", 12000000, 11);
		//updateDataUser("Android", 20000000, 15);
		get15Questions();
	}
	public void get15Questions(){
		for (int i = 1; i < 16; i++) {
			dataBase.getQuestions("SELECT * FROM QUESTION"+i+" ORDER BY RANDOM() LIMIT 1");
		}
		listQ=dataBase.getListQuestion();
		for (Question i : listQ) {
			Log.i(TAG, "Question: " + i.getQuestion());
			Log.i(TAG, "CaseA: " + i.getCaseA());
			Log.i(TAG, "CaseB: " + i.getCaseB());
			Log.i(TAG, "CaseC: " + i.getCaseC());
			Log.i(TAG, "CaseD: " + i.getCaseD());
			Log.i(TAG, "True Case: " + i.getTrueCase());
		}
	}
	public void updateDataUser(String name, int highScore, int level) {
		ContentValues values = new ContentValues();
		values.put("HighScore", highScore);
		values.put("Level", level);

		dataBase.updateData("User", values, "Name=?", new String[] { name });
	}

	public void insertDataUser(String name, int highScore, int level) {
		ContentValues values = new ContentValues();
		values.put("Name", name);
		values.put("HighScore", highScore);
		values.put("Level", level);
		dataBase.insertData("User", values);
	}

	public void deleteDataUser(String name) {
		dataBase.deleteData("User", "Name=?", new String[] { name });
	}

}
