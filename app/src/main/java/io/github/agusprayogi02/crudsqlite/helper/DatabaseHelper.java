package io.github.agusprayogi02.crudsqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import io.github.agusprayogi02.crudsqlite.model.StudentModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DBName = "Student_db";
    private static final int DBVersion = 1;
    private static final String tableName = "Student";
    private static final String idKey = "id";
    private static final String NameKey = "name";
    private static final String EmailKey = "email";

    private static final String makeTableStudent = "CREATE TABLE "
            + tableName + "(" + idKey + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NameKey + " TEXT, " + EmailKey + " TEXT );";
    private static final String dropTableStudent = "DROP TABLE IF EXISTS '" + tableName + "'";

    public DatabaseHelper(Context context) {
        super(context, DBName, null, DBVersion);
        Log.d("Table ", makeTableStudent);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(makeTableStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(dropTableStudent);
        onCreate(sqLiteDatabase);
    }

    public long addStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
//        membuat conntent values;
        ContentValues values = new ContentValues();
        values.put(NameKey, student.getNameField());
        values.put(EmailKey, student.getEmailField());
        return db.insert(tableName, null, values);
    }

    public ArrayList<StudentModel> getAllStudent() {
        ArrayList<StudentModel> list = new ArrayList<StudentModel>();
        String query = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int indexName = c.getColumnIndex(NameKey);
        if (c.moveToFirst()) {
            do {
                StudentModel student = new StudentModel(c.getInt(0), c.getString(1), c.getString(2));
                list.add(student);
            } while (c.moveToNext());
            Log.d("array Student", list.toString());
        }
        return list;
    }

    public long updateStudent(StudentModel student) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        String[] id = new String[]{String.valueOf(student.getIdField())};
        values.put(NameKey, student.getNameField());
        values.put(EmailKey, student.getEmailField());
        return db.update(tableName, values, "id = ?", id);
    }

    public long deleteStudent(int idField) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, idKey + "= ?", new String[]{String.valueOf(idField)});
    }
}
