package com.bwellthy.app.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.bwellthy.app.models.Words;

import java.util.ArrayList;
import java.util.List;

public class AppDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bwellthy.db";
    private static final String TABLE_NAME = "vocabulary";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_VARIANT = "variant";
    private static final String COLUMN_MEANING = "meaning";
    private static final String COLUMN_RATIO = "ratio";

    private static AppDB instance;

    private AppDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + TABLE_NAME + " ( ");
        createTableQuery.append(COLUMN_ID + " INTEGER PRIMARY KEY, ");
        createTableQuery.append(COLUMN_WORD + " TEXT, ");
        createTableQuery.append(COLUMN_VARIANT + " INTEGER, ");
        createTableQuery.append(COLUMN_MEANING + " TEXT, ");
        createTableQuery.append(COLUMN_RATIO + " TEXT)");
        sqLiteDatabase.execSQL(createTableQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = new AppDB(context);
        }
        return instance;
    }

    public void bulkInsert(Context context, List<Words> words) {
        SQLiteDatabase database = getInstance(context).getWritableDatabase();
        try {
            StringBuilder insertQuery = new StringBuilder("INSERT OR REPLACE INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?);");
            SQLiteStatement insertStatement = database.compileStatement(insertQuery.toString());
            database.beginTransaction();
            for (Words word : words) {
                insertStatement.clearBindings();
                insertStatement.bindLong(1, word.getId());
                insertStatement.bindString(2, word.getWord());
                insertStatement.bindLong(3, word.getVariant());
                insertStatement.bindString(4, word.getMeaning());
                insertStatement.bindDouble(5, word.getRatio());
                insertStatement.execute();
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    public List<Words> getAllWords(Context context) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        List<Words> words = new ArrayList<>();
        try {
            Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_WORD, COLUMN_MEANING, COLUMN_VARIANT, COLUMN_RATIO}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Words word = new Words();
                    word.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    word.setWord(cursor.getString(cursor.getColumnIndex(COLUMN_WORD)));
                    word.setMeaning(cursor.getString(cursor.getColumnIndex(COLUMN_MEANING)));
                    word.setVariant(cursor.getInt(cursor.getColumnIndex(COLUMN_VARIANT)));
                    word.setRatio(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATIO)));
                    words.add(word);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return words;
    }

    public List<Words> getAllWordsIgnoreRatio(Context context) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        List<Words> words = new ArrayList<>();
        try {
            Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_WORD, COLUMN_MEANING, COLUMN_VARIANT, COLUMN_RATIO}, COLUMN_RATIO + " > ?", new String[]{"0"}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Words word = new Words();
                    word.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    word.setWord(cursor.getString(cursor.getColumnIndex(COLUMN_WORD)));
                    word.setMeaning(cursor.getString(cursor.getColumnIndex(COLUMN_MEANING)));
                    word.setVariant(cursor.getInt(cursor.getColumnIndex(COLUMN_VARIANT)));
                    word.setRatio(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATIO)));
                    words.add(word);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return words;
    }

}
