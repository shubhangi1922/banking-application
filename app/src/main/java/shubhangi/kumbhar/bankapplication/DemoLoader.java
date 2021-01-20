package shubhangi.kumbhar.bankapplication;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import shubhangi.kumbhar.bankapplication.database.CustomersDb;
import shubhangi.kumbhar.bankapplication.database.TransactionDb;

public class DemoLoader {
    Context context;
    public DemoLoader(Context context){
        this.context = context;
    }

    public  void loadDemo(){
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        CustomersDb customersDb = new CustomersDb(this.context);
        try {
            customersDb.open();

            customersDb.insertData("1","Shubhangi","9972757009","john@gmail.com","35002464606","4700");
            customersDb.insertData("2","Samson","5744043723","samson@gmail.com","34002464606","400");
            customersDb.insertData("3","Uday","1744043723","Uday@gmail.com","3400242335464606","900");
            customersDb.insertData("4","Samira","51744043723","Samira@gmail.com","34345002464606","234");
            customersDb.insertData("5","Sunena","35744043723","Sunena@gmail.com","340074362464606","2300");
            customersDb.insertData("6","Shivam","54744043723","Shivam@gmail.com","34002434564606","34010");
            customersDb.insertData("7","Vishal","52744043723","Vishal@gmail.com","340022464606","4060");
            customersDb.insertData("8","Vivek","574124043723","Vivek@gmail.com","3400256464606","4200");
            customersDb.insertData("9","Mukesh A.","55744043723","Mukesh@reliancegroup.com","34034502464606","50000");
            customersDb.insertData("10","Anil A.","57424043723","Anil@reliancegroup.com","3432002464606","0");

            customersDb.close();

        }catch (Exception e){

        }

        TransactionDb transactionDb = new TransactionDb(this.context);
        try{
            transactionDb.open();
            transactionDb.insertData("1","10000","Money Deposited","Credit","08/01/2021","1");
            transactionDb.insertData("2","5000","Money Withdraw at ATM","Debit","10/01/2021","1");
            transactionDb.insertData("3","4000","Money Transfered to 34002464606","Debit","10/01/2021","1");
            transactionDb.insertData("4","4000","Money Credited by 35002464606","Credit","10/01/2021","2");
            transactionDb.insertData("5","1000","Money Deposited","Credit","08/01/2021","2");
            transactionDb.insertData("6","5000","Money Withdraw at ATM","Debit","12/01/2021","2");
            transactionDb.insertData("7","5000","Money Deposited","Credit",date,"1");
            transactionDb.insertData("8","800","Bill Payment Hotel Dine-out","Debit",date,"1");
            transactionDb.insertData("9","100","Money Withdraw at ATM","Debit",date,"1");
            transactionDb.insertData("10","400","Money Transfered to 34002464606","Debit",date,"1");
            transactionDb.insertData("11","400","Money Credited by 35002464606","Credit",date,"2");

            transactionDb.close();

        }catch (Exception e){

        }



    }
}
