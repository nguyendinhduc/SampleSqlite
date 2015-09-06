package com.nac.bai18_ailatrieuphu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DataBaseManager {
	private Context mContext;
	private static final String DATA_PATH = Environment.getDataDirectory()
			+ "/data/com.nac.bai18_ailatrieuphu/databases/";

	private static final String DATA_NAME = "Question";
	private static final String TAG = "DataBaseManager";
	private SQLiteDatabase sqlDB;

	private ArrayList<Question> listQ = new ArrayList<Question>();
	public ArrayList<Question> getListQuestion(){
		return listQ;
	}
	public DataBaseManager(Context context) {
		this.mContext = context;
		coppyDataBase();
	}

	private void coppyDataBase() {
		
		new File(DATA_PATH).mkdir();
		File fileOn = new File(DATA_PATH + DATA_NAME);
		if (fileOn.exists())
			return;

		try {
			fileOn.createNewFile();
			AssetManager asset = this.mContext.getAssets();
			InputStream fileIn = asset.open(DATA_NAME);
			FileOutputStream fileOut = new FileOutputStream(fileOn);

			int len = -1;
			byte[] b = new byte[1024];
			while ((len = fileIn.read(b)) > 0) {
				fileOut.write(b, 0, len);
			}

			fileIn.close();
			fileOut.close();

		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.toString());
		}
	}

	public void insertData(String tableName, ContentValues values) {
		openDataBase();
		long index = sqlDB.insert(tableName, null, values);
		if (index == -1)
			Toast.makeText(mContext, "Insert data failure!", Toast.LENGTH_SHORT)
					.show();
		else
			Toast.makeText(mContext, "Insert data successfully!",
					Toast.LENGTH_SHORT).show();
	}

	public void updateData(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		openDataBase();

		long index = sqlDB.update(tableName, values, whereClause, whereArgs);
		if (index == 0)
			Toast.makeText(mContext, "Nothing is updated!", Toast.LENGTH_SHORT)
					.show();
		else
			Toast.makeText(mContext, index + " rows are updated!",
					Toast.LENGTH_SHORT).show();
	}

	public void deleteData(String tableName, String whereClause,
			String[] whereArgs) {
		long index = sqlDB.delete(tableName, whereClause, whereArgs);
		if (index == 0)
			Toast.makeText(mContext, "Nothing is deleted!", Toast.LENGTH_SHORT)
					.show();
		else
			Toast.makeText(mContext, index + " rows are deleted!",
					Toast.LENGTH_SHORT).show();
	}

	public void getQuestions(String sql) {
		openDataBase();
		Cursor c = sqlDB.rawQuery(sql, null);
		if (c == null)
			return;

		c.moveToFirst();
		int indexQuestion = c.getColumnIndex("Question");
		int indexCaseA = c.getColumnIndex("CaseA");
		int indexCaseB = c.getColumnIndex("CaseB");
		int indexCaseC = c.getColumnIndex("CaseC");
		int indexCaseD = c.getColumnIndex("CaseD");
		int indexTrueCase = c.getColumnIndex("TrueCase");
		//
		String question;
		String caseA;
		String caseB;
		String caseC;
		String caseD;
		int trueCase;
		Log.i(TAG, "--------------------------------");
		while (!c.isAfterLast()) {
			question = c.getString(indexQuestion);
			caseA = c.getString(indexCaseA);
			caseB = c.getString(indexCaseB);
			caseC = c.getString(indexCaseC);
			caseD = c.getString(indexCaseD);
			trueCase = c.getInt(indexTrueCase);
			Question q = new Question(question, caseA, caseB, caseC, caseD, trueCase);
			listQ.add(q);
			Log.i(TAG, "11");
			c.moveToNext();
		}
		closeDataBase();
		Log.i(TAG, "--------------------------------");
	}

	private void openDataBase() {
		if (sqlDB == null || sqlDB.isOpen() == false)
			sqlDB = SQLiteDatabase.openDatabase(DATA_PATH + DATA_NAME, null,
					SQLiteDatabase.OPEN_READWRITE);
	}

	public void closeDataBase() {
		if (sqlDB != null && sqlDB.isOpen())
			sqlDB.close();
	}

}
