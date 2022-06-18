package com.example.sokoban.game.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDatabase extends SQLiteOpenHelper {
    public HelperDatabase(@Nullable Context context) {
        super(context, "sokoban", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE boards(_id TEXT PRIMARY KEY, name TEXT, rows INTEGER, columns INTEGER, description TEXT)");
        //db.execSQL("CREATE TABLE rows(_boardId TEXT FOREIGN KEY, row_number INTEGER, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS boards");
        onCreate(db);
    }

    public void insertBoards(Boards board) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", board.get_id());
        contentValues.put("name", board.getName());
        contentValues.put("rows", board.getRows());
        contentValues.put("columns", board.getColumns());
        contentValues.put("description", board.getDescription());

        db.insert("boards", null, contentValues);
        db.close();
    }

    public void updateBoard(Boards board) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", board.get_id());
        contentValues.put("name", board.getName());
        contentValues.put("rows", board.getRows());
        contentValues.put("columns", board.getColumns());
        contentValues.put("description", board.getDescription());

        db.update("boards", contentValues, "_id=?", new String[]{String.valueOf(board.get_id())});
        db.close();
    }

    public void deleteBoard(String board_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("boards", "_id=?", new String[]{String.valueOf(board_id)});
        db.close();
    }

    public Cursor getAllBoards() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM boards", null);
    }

    public Boards getOneBoard(String board_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("boards", new String[]{"_id", "name", "rows", "columns", "description"}, "_id=?",
                new String[]{String.valueOf(board_id)}, null, null, null);
        c.moveToFirst();
        return new Boards(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4));
    }
}
