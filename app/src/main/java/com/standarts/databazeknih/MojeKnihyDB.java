package com.standarts.databazeknih;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MojeKnihyDB {
    static final String TAG = "MojeKnihyDB2";
    static final String DATABASE_NAME = "MojeKnihy";
    static final int DATABASE_VERSION = 2;
    static final String DATABASE_TABLE = "knihy";

    static final String COL_ID = "id";
    static final String COL_NAZEV = "nazev";
    static final String COL_POCET_STRANEK = "ps";
    static final String COL_AUTOR = "autor";

    final Context context;
    MujDatabaseHelper DBHelper;
    SQLiteDatabase db;

    public MojeKnihyDB(Context ctx) {
        this.context = ctx;
        DBHelper = new MujDatabaseHelper(this.context);
    }

    private static class MujDatabaseHelper extends SQLiteOpenHelper {

        MujDatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqlDB) {
            /*String sql = String.format("CREATE TABLE \"knihy\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"nazev\"\tTEXT NOT NULL,\n" +
                    "\t\"ps\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");");*/

            sqlDB.execSQL("create table " + DATABASE_TABLE + " (id integer primary key autoincrement,"
                    + COL_NAZEV + " text not null,"
                    + COL_POCET_STRANEK + " integer,"
                    + COL_AUTOR + " varchar(255))"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int iOld, int iNew) {
            /// provedení potřebných zmìn///
            //alter table
            if(iNew > iOld){
                sqlDB.execSQL("alter table " + DATABASE_TABLE + " add column " + COL_AUTOR + " varchar(255)");
            }
        }
    }

    //--- otevření DB ---
    public MojeKnihyDB open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //--- zavření DB ---
    public void close()
    {
        DBHelper.close();
    }

    //--- vložit záznam ---
    public long vlozKnihu(String nazev, Integer ps, String autor) {
        open();
        ContentValues vstupniHodnoty = new ContentValues();
        vstupniHodnoty.put(COL_NAZEV, nazev);
        vstupniHodnoty.put(COL_POCET_STRANEK, ps);
        vstupniHodnoty.put(COL_AUTOR, autor);

        long id = db.insert(DATABASE_TABLE, null, vstupniHodnoty);
        close();
        return id;
    }
    //--- smazání konkrétního záznamu ---
    public boolean smazKnihu(int id) {
        // TODO //
        if(id > 0) {
            open();
            db.delete(DATABASE_TABLE, COL_ID + "=?", new String[]{id + ""});
            return true;
        }else {
            return false;
        }
    }

    //--- smazání všech záznamù ---
    public int smazKnihy() {
        // TODO //
        db.delete(DATABASE_TABLE, null, null);
        return 0;
    }

    //--- získat vsechny knihy ---
    public Cursor vsechnyKnihy(String order) {
        // TODO //
        return db.query(DATABASE_TABLE, new String[] {COL_ID, COL_NAZEV, COL_POCET_STRANEK, COL_AUTOR}, null, null, null, null, order);
    }

    //--- získat jeden záznam ---
    public Cursor jednuKnihu(long rowId) throws SQLException {
        // TODO //
        //return mCursor;
        return null;
    }
}
