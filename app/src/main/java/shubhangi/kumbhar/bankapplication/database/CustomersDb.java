package shubhangi.kumbhar.bankapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import shubhangi.kumbhar.bankapplication.adapter.ViewAllCustomerAdapter;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class CustomersDb {

    public static final String KEY_CUSTOMER_ID = "cust_id";
    public static final String KEY_CUSTOMER_NAME = "cust_name";
    public static final String KEY_CUSTOMER_CONTACT = "cust_contact";
    public static final String KEY_CUSTOMER_EMAIL = "cust_email";
    public static final String KEY_CUSTOMER_ACCOUNT_NO = "cust_account_no";
    public static final String KEY_CUSTOMER_ACCOUNT_BALANCE = "cust_account_balance";
    
    public static final String DB_NAME = "customerDb";
    public static final String TABLE = "customertbl";
    public static final int DB_VERSION = 1;
    
    Context context;
    SQLiteDatabase database;
    DbHelper helper;

    
    public CustomersDb(Context context){
        this.context = context;
    }

    public void makeCustomerBalance(String amount, String cust_id) {
        helper=new CustomersDb.DbHelper(context);

        database = helper.getReadableDatabase();
        database.execSQL("UPDATE "+TABLE+" SET "+KEY_CUSTOMER_ACCOUNT_BALANCE+" = "+"'"+amount+"' "+ "WHERE "+KEY_CUSTOMER_ID+" = "+"'"+cust_id+"'");

    }

    public static class DbHelper extends SQLiteOpenHelper {


        public DbHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE + " (" +
                    KEY_CUSTOMER_ID + " TEXT NOT NULL, " +
                    KEY_CUSTOMER_NAME + " TEXT NOT NULL, " +
                    KEY_CUSTOMER_CONTACT + " TEXT NOT NULL, " +
                    KEY_CUSTOMER_EMAIL + " TEXT NOT NULL, " +
                    KEY_CUSTOMER_ACCOUNT_NO + " TEXT NOT NULL, " +
                    KEY_CUSTOMER_ACCOUNT_BALANCE + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

    public CustomersDb open() throws SQLException {
        helper = new DbHelper(context);
        database = helper.getWritableDatabase();
        return this;

    }

    public ViewAllCustomerModel getAccountDetailsByCustomerId(String Cust_id){
        ViewAllCustomerModel model = null;

        String selectQuery = "SELECT  * FROM " + TABLE+" WHERE "+KEY_CUSTOMER_ID+" = ?";
        helper=new CustomersDb.DbHelper(context);
        database=helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{Cust_id});
        if (cursor.moveToFirst()) {
            do{
                model=new ViewAllCustomerModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)

                );

            } while (cursor.moveToNext());
        }
        cursor.close();
        helper.close();
        return model;

    }

    public void close(){
        helper.close();
    }

    public void insertData(String ID, String CUSTOMER_NAME, String CUSTOMER_MOBILE, String CUSTOMER_EMAIL, String CUSTOMER_ACCOUNT, String CUSTOMER_bALANCE){
        ContentValues val = new ContentValues();
        val.put(KEY_CUSTOMER_ID,ID);
        val.put(KEY_CUSTOMER_NAME,CUSTOMER_NAME);
        val.put(KEY_CUSTOMER_CONTACT,CUSTOMER_MOBILE);
        val.put(KEY_CUSTOMER_EMAIL,CUSTOMER_EMAIL);
        val.put(KEY_CUSTOMER_ACCOUNT_NO,CUSTOMER_ACCOUNT);
        val.put(KEY_CUSTOMER_ACCOUNT_BALANCE,CUSTOMER_bALANCE);
        database.insert(TABLE,null,val);
        Log.e("CUSTOMERDb", "insertData: for id:: "+KEY_CUSTOMER_ID );
    }

    public List<ViewAllCustomerModel> getAllCustomers(){
        List<ViewAllCustomerModel> model = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE;
        helper=new CustomersDb.DbHelper(context);
        database=helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do{
                model.add(new ViewAllCustomerModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)

                        ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        helper.close();
        return model;
    }

    public ViewAllCustomerModel getAccInfoFromAccNumber(String ACC_NO){
        ViewAllCustomerModel model = null;

        String selectQuery = "SELECT  * FROM " + TABLE+" WHERE "+KEY_CUSTOMER_ACCOUNT_NO+" = ?";
        helper=new CustomersDb.DbHelper(context);
        database=helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{ACC_NO});
        if (cursor.moveToFirst()) {
            do{
                model=new ViewAllCustomerModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)

                );

            } while (cursor.moveToNext());
        }
        cursor.close();
        helper.close();
        return model;


    }
}
