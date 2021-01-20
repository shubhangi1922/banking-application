package shubhangi.kumbhar.bankapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.database.CustomersDb;
import shubhangi.kumbhar.bankapplication.database.TransactionDb;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class TransferFragment extends Fragment {
    EditText et_acc_no,et_amount;
    Button btn_transfer;
    TextView tv_status,tv_validAmount;
    boolean isAccountExist=false;
    ViewAllCustomerModel customerModel = null;
    ViewAllCustomerModel myAccDetails = null;
    private long TIME_UNIT = 500;

    public static Fragment newInstance() {
        return new TransferFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        initElements(view);
        getMyAccountDetails();
        return view;
    }

    private void getMyAccountDetails() {
        CustomersDb customerDb = new CustomersDb(getContext());
        try {
            customerDb.open();
            myAccDetails = customerDb.getAccountDetailsByCustomerId("1");
            customerDb.close();
        }catch (Exception e){

        }
    }

    private void initElements(View view) {
        et_acc_no = view.findViewById(R.id.et_acc_no);
        et_amount = view.findViewById(R.id.et_amount);
        btn_transfer = view.findViewById(R.id.btn_transfer);
        tv_status = view.findViewById(R.id.tv_status);
        tv_validAmount = view.findViewById(R.id.tv_validAmount);

        et_acc_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    checkIfAccountExists();
            }
        });

        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!et_amount.getText().toString().equalsIgnoreCase(""))
                isValidToMyAccountBalance();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    performMoneyTransfer();
                }catch(Exception e){

                }
            }
        });

    }

    private void performMoneyTransfer() throws InterruptedException {
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Transfering Money. Do not press back. You will be notified once done");
        pDialog.setCancelable(false);
        pDialog.show();

        Thread.sleep(TIME_UNIT);
        pDialog.setTitleText("Fetching Your account balance");
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        int MY_BALANCE = Integer.parseInt(myAccDetails.getCust_account_balance());
        int MONEY_TO_TRANSFER =  Integer.parseInt(et_amount.getText().toString());
        int USER_BALANCE = Integer.parseInt(customerModel.getCust_account_balance());

        try{
            TransactionDb transDb = new TransactionDb(getContext());
            transDb.open();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            transDb.insertData(String.valueOf(timestamp.getTime()),et_amount.getText().toString(),"MONEY TRANSFERED TO "+customerModel.getCust_account_no(),"Debit",date,myAccDetails.getCust_id());
            CustomersDb customersDb = new CustomersDb(getContext());
            customersDb.makeCustomerBalance(String.valueOf(MY_BALANCE-MONEY_TO_TRANSFER),myAccDetails.getCust_id());

            transDb.close();
            customersDb.close();
        }catch (Exception e){

        }
        Thread.sleep(TIME_UNIT);
        pDialog.setTitleText("Transfering money to recipent bank account");


        try{
            TransactionDb transDb = new TransactionDb(getContext());
            transDb.open();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            transDb.insertData(String.valueOf(timestamp.getTime()),et_amount.getText().toString(),"MONEY RECEIVED FROM "+myAccDetails.getCust_account_no(),"Credit",date,customerModel.getCust_id());
            CustomersDb customersDb = new CustomersDb(getContext());
            customersDb.makeCustomerBalance(String.valueOf(USER_BALANCE+MONEY_TO_TRANSFER),customerModel.getCust_id());


            transDb.close();
            customersDb.close();
        }catch (Exception e){

        }

        Thread.sleep(TIME_UNIT);
        pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("Money Transfered Successfully");
        pDialog.setConfirmButton("Great!", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        Toast.makeText(getContext(), "MONEY TRANSFER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        et_amount.setText("");
        et_acc_no.setText("");
        et_amount.setEnabled(false);
        isAccountExist = false;
        btn_transfer.setEnabled(false);
        tv_status.setText("");


    }

    private void isValidToMyAccountBalance() {
        int MY_BALANCE = Integer.parseInt(myAccDetails.getCust_account_balance());
        int MONEY_TO_TRANSFER =  Integer.parseInt(et_amount.getText().toString());
        if(MY_BALANCE<MONEY_TO_TRANSFER){

            btn_transfer.setEnabled(false);
            tv_validAmount.setText("Sorry, Insufficent Balance");
            tv_validAmount.setTextColor(Color.parseColor("#FF0000"));
        }else{
            btn_transfer.setEnabled(true);
            tv_validAmount.setText("");
            tv_validAmount.setTextColor(Color.parseColor("#228B22"));

        }
    }

    private void checkIfAccountExists() {
        CustomersDb customerDb = new CustomersDb(getContext());
        try {
            customerDb.open();
           customerModel =  customerDb.getAccInfoFromAccNumber(et_acc_no.getText().toString());
           customerDb.close();
           if(customerModel == null){
               isAccountExist=false;

               btn_transfer.setEnabled(false);
               et_amount.setEnabled(false);
               tv_status.setText("Sorry, Account Not Found");
               tv_status.setTextColor(Color.parseColor("#FF0000"));
           }else{
               if(customerModel.getCust_id().equalsIgnoreCase("1")){
                   isAccountExist=false;

                   btn_transfer.setEnabled(false);
                   et_amount.setEnabled(false);
                   tv_status.setText("Sorry, Cannot transfer to your own account");
                   tv_status.setTextColor(Color.parseColor("#FF0000"));
               }else {
                   isAccountExist = true;
                   btn_transfer.setEnabled(true);
                   et_amount.setEnabled(true);
                   tv_status.setText(customerModel.getCust_name());
                   tv_status.setTextColor(Color.parseColor("#228B22"));
               }
           }

        }catch (Exception e){

        }
    }
}
