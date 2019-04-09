package com.mta.studyenglish.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mta.studyenglish.model.ExerciseItem;
import com.mta.studyenglish.model.GrammarDetailItem;
import com.mta.studyenglish.model.GrammarItem;
import com.mta.studyenglish.model.UserItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataenglish.db";
    public static final String DBLOCATION = "/data/data/com.mta.studyenglish/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    // ---------------------------------------------------------------------------------------------

    public UserItem findUserLogin(String username, String password) {
        openDatabase();

        UserItem userItem = null;
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM user WHERE username = ? AND passwork = ? ", new String[]{username, password});
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            userItem = new UserItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        closeDatabase();

        return userItem;
    }

    public List<GrammarItem> findAllGrammar() {
        openDatabase();

        GrammarItem grammarItem;
        List<GrammarItem> grammarItemList = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM list_grammar ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            grammarItem = new GrammarItem(cursor.getString(1), cursor.getInt(0), cursor.getInt(2));
            grammarItemList.add(grammarItem);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return grammarItemList;
    }

    public List<GrammarItem> findAllMarkedGrammar() {
        openDatabase();

        GrammarItem grammarItem;
        List<GrammarItem> grammarItemList = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM list_grammar WHERE is_mark = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            grammarItem = new GrammarItem(cursor.getString(1), cursor.getInt(0), cursor.getInt(2));
            grammarItemList.add(grammarItem);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return grammarItemList;
    }

    public GrammarDetailItem findGrammarDetailByListId(int grammarListId) {
        openDatabase();

        GrammarDetailItem grammarDetailItem = null;
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM detail_grammar WHERE id_list = ? ", new String[]{String.valueOf(grammarListId)});
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            grammarDetailItem = new GrammarDetailItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        }

        cursor.close();
        closeDatabase();

        return grammarDetailItem;
    }

    public long updateMarkInGrammarList(int grammarListId, int mark) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_mark", mark);
        String[] whereArgs = {String.valueOf(grammarListId)};

        openDatabase();

        long returnValue = mDatabase.update("list_grammar", contentValues, "id_list = ?", whereArgs);

        closeDatabase();

        return returnValue;
    }

    public List<ExerciseItem> findAllExerciseByGrammarId(int grammarId) {
        openDatabase();

        ExerciseItem exerciseItem;
        List<ExerciseItem> exerciseItemList = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM excersice WHERE id_list = ? ", new String[]{String.valueOf(grammarId)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exerciseItem = new ExerciseItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getInt(7));
            exerciseItemList.add(exerciseItem);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();

        return exerciseItemList;
    }
}
