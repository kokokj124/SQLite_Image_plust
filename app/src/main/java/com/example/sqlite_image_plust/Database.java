package com.example.sqlite_image_plust;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import androidx.annotation.Nullable;
public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    public void insert_DoVat(String ten, String mota, byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "insert into DoVat values(?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();statement.bindString(1,ten);
        statement.bindString(2,mota);
        statement.bindBlob(3,hinh);
        statement.executeInsert();
    }
    public void delete_DoVat(String ten){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "delete from DoVat where Ten = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.executeInsert();
    }
    public void edit_DoVat(String ten, String mota, byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "update DoVat set MoTe = ?, HinhAnh = ? where Ten = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(0,mota);
        statement.bindBlob(1,hinh);
        statement.bindString(2,ten);
        statement.executeInsert();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
