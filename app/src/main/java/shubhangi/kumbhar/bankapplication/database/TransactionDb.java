package shubhangi.kumbhar.bankapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import shubhangi.kumbhar.bankapplication.pojo.TransactionModel;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class TransactionDb {
    public static final String KEY_TRANSACTION_ID = "transaction_id";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_TRANSACTION_COMMENT = "transaction_comment";
    public static final String KEY_TRANSACTION_TYPE = "transaction_type";
    public static final String KEY_TRANSACTION_DATE = "transaction_date";
    public static final String KEY_INITIATOR_ID = "initiator_id";


    public static final String DB_NAME = "transactionDb";
    public static final String TABLE = "transactiontbl";
    public static final int DB_VERSION = 1;

    Context context;
    SQLiteDatabase database;
    DbHelper helper;

    public TransactionDb(Context context) {
        this.context = context;
    }
     public static class DbHelper extends SQLiteOpenHelper {
         public DbHelper(@Nullable Context context) {
             super(context, DB_NAME, null, DB_VERSION);
         }

         @Override
         public void onCreate(SQLiteDatabase sqLiteDatabase) {
             sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + " (" +
                     KEY_TRANSACTION_ID + " TEXT NOT NULL, " +
                     KEY_AMOUNT + " TEXT NOT NULL, " +
                     KEY_TRANSACTION_COMMENT + " TEXT NOT NULL, " +
                     KEY_TRANSACTION_TYPE + " TEXT NOT NULL, " +
                     KEY_TRANSACTION_DATE + " TEXT NOT NULL, " +
                     KEY_INITIATOR_ID + " TEXT NOT NULL);"
             );

         }

         @Override
         public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
             onCreate(sqLiteDatabase);
         }
     }

     public TransactionDb open() throws SQLException {
        helper = new DbHelper(context);
        database = helper.getWritableDatabase();
        return this;
     }

     public void close(){
        helper.close();
     }

     public void insertData(String ID, String AMOUNT, String COMMENT, String TYPE, String DATE ,String INITIATOR_ID){
         ContentValues val = new ContentValues();
         val.put(KEY_TRANSACTION_ID,ID);
         val.put(KEY_AMOUNT,AMOUNT);
         val.put(KEY_TRANSACTION_COMMENT,COMMENT);
         val.put(KEY_TRANSACTION_TYPE,TYPE);
         val.put(KEY_TRANSACTION_DATE,DATE);
         val.put(KEY_INITIATOR_ID,INITIATOR_ID);
         database.insert(TABLE,null,val);

     }
     public List<TransactionModel> getTransactionList(String custId){
        List<TransactionModel> model = new ArrayList<>();
         String selectQuery = "SELECT  * FROM " + TABLE+" WHERE "+KEY_INITIATOR_ID+" = ?";
         helper=new TransactionDb.DbHelper(context);
         database=helper.getReadableDatabase();
         Cursor cursor = database.rawQuery(selectQuery, new String[]{custId});
         if (cursor.moveToFirst()) {
             do{
                 model.add(new TransactionModel(
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

    public List<TransactionModel> getTodayTransactionList(String custId){
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        List<TransactionModel> model = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE+" WHERE "+KEY_INITIATOR_ID+" = ? and "+KEY_TRANSACTION_DATE+" = ?";
        helper=new TransactionDb.DbHelper(context);
        database=helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{custId,date});
        if (cursor.moveToFirst()) {
            do{
                model.add(new TransactionModel(
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

     public String getCreditAmount(String USER_ID){
        double sum = 0.0;
        String selectQuery =  "SELECT * FROM  "+TABLE+" where "+KEY_INITIATOR_ID+" = ? and "+KEY_TRANSACTION_TYPE+" = ?";

         helper = new TransactionDb.DbHelper(context);
        database=helper.getReadableDatabase();
         Cursor cursor = database.rawQuery(selectQuery, new String[]{USER_ID,"Credit"});
         if (cursor.moveToFirst()) {
             do{

//                 Log.e("TRANSTBL", "getCreditAmount: CURSOR IS "+cursor.toString() );
                 if(isTodaysTransaction(cursor.getString(4)))
                sum = sum+Double.parseDouble(cursor.getString(1));

             } while (cursor.moveToNext());
         }
         cursor.close();
         helper.close();

         Log.e("TRNSTBL", "getCreditAmount: "+String.valueOf(sum) );
         return String.valueOf(sum);

     }

     public String getDebitAmount(String USER_ID){
         double sum = 0.0;
         String selectQuery =  "SELECT * FROM  "+TABLE+" where "+KEY_INITIATOR_ID+" = ? and "+KEY_TRANSACTION_TYPE+" = ?";

         helper = new TransactionDb.DbHelper(context);
         database=helper.getReadableDatabase();
         Cursor cursor = database.rawQuery(selectQuery, new String[]{USER_ID,"Debit"});
         if (cursor.moveToFirst()) {
             do{

                 if(isTodaysTransaction(cursor.getString(4)))
                     sum = sum+Double.parseDouble(cursor.getString(1));

             } while (cursor.moveToNext());
         }
         cursor.close();
         helper.close();

         Log.e("TRNSTBL", "getDebitAmount: "+String.valueOf(sum) );
         return String.valueOf(sum);
     }

    private boolean isTodaysTransaction(String string) {
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Log.e("TRANSTBL", "isTodaysTransaction: today's date :  "+date );
        Log.e("TRANSTBL", "isTodaysTransaction: trans's date :  "+string );

        if(string.equalsIgnoreCase(date)){
            return true;
        }else{
            return false;
        }


    }
}
