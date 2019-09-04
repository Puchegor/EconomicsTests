package com.elmareos.economicstests;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "ectheory.db";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    final String LOG_TAG = "myLOGS";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        if (Build.VERSION.SDK_INT>=17)
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        copyDatabase();
        Log.d(LOG_TAG, "DBHelper created");
    }
    private void copyDatabase(){
        if(!checkDatabase()){
            this.getReadableDatabase();
            this.close();
            try{
                copyDBFile();
            }catch (IOException e){
                Log.d(LOG_TAG, "Database file not found");
            }
        }
    }
    private void copyDBFile() throws IOException{
        InputStream input = mContext.getAssets().open(DB_NAME);
        OutputStream output = new FileOutputStream(DB_PATH+DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0)
            output.write(buffer,0,length);
        output.flush();
        output.close();
        input.close();
    }
    private boolean checkDatabase(){
        File dbFile = new File(DB_PATH+DB_NAME);
        return dbFile.exists();
    }
    public boolean openDatabase() throws SQLException{
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        return myDataBase != null;
    }
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
