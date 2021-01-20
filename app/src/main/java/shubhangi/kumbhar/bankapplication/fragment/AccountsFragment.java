package shubhangi.kumbhar.bankapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.adapter.TransactionAdapter;
import shubhangi.kumbhar.bankapplication.database.CustomersDb;
import shubhangi.kumbhar.bankapplication.database.TransactionDb;
import shubhangi.kumbhar.bankapplication.pojo.TransactionModel;


public class AccountsFragment extends Fragment {


TextView tv_credited,tv_debited;
    RecyclerView recyclerView;
    TransactionAdapter adapter;
    List<TransactionModel> list;

    public static Fragment newInstance() {
        return new AccountsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        initElements(view);
        getCreditedData();
        getDebitedData();
        return view;
    }


    private void getDebitedData() {
        TransactionDb transactionDb = new TransactionDb(getContext());
        try{
            transactionDb.open();
            tv_debited.setText("₹ " + transactionDb.getDebitAmount("1"));
            transactionDb.close();
        }catch (Exception e){

        }
    }

    private void initElements(View view) {
        tv_credited = view.findViewById(R.id.tv_credited);
        tv_debited = view.findViewById(R.id.tv_debited);
        String date = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault()).format(new Date());

        list = new ArrayList<>();
        try {
            TransactionDb db = new TransactionDb(getContext());

            db.open();
            list.addAll(db.getTodayTransactionList("1"));
            Collections.reverse(list);

            db.close();
        }catch(Exception e){

        }
        recyclerView=view.findViewById(R.id.recyclerView);

        adapter = new TransactionAdapter(getContext(),list,getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapter);


        TextView tv_today_date = view.findViewById(R.id.tv_today_date);
        tv_today_date.setText("Today is "+date);
        TextView tv_greetings = view.findViewById(R.id.tv_greetings);
        TextView tv_acc_balance = view.findViewById(R.id.tv_acc_balance);

        try {
            CustomersDb db = new CustomersDb(getContext());

            db.open();

            tv_greetings.setText(getTimeWiseText()+db.getAccountDetailsByCustomerId("1").getCust_name());
            tv_acc_balance.setText("₹ "+db.getAccountDetailsByCustomerId("1").getCust_account_balance());
            db.close();
        }catch(Exception e){

        }

    }

    private void getCreditedData() {
        TransactionDb db = new TransactionDb(getContext());
        try{
            db.open();
           tv_credited.setText("₹ " +  db.getCreditAmount("1"));
            db.close();
        }catch (Exception e){

        }

    }

    private String getTimeWiseText() {
        String TIME = "";
        String CURRENT_TIME = "";



        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        CURRENT_TIME = dateFormat.format(cal.getTime());
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            TIME = "11:59";
            Date CURR = sdf.parse(CURRENT_TIME);
            Date GAME = sdf.parse(TIME);
            if(CURR.compareTo(GAME) == -1)
                return  "Good Morning ";
            TIME = "15:59";
            GAME = sdf.parse(TIME);

            if(CURR.compareTo(GAME) == -1)
                return  "Good Afternoon ";
            TIME = "23:59";
            GAME = sdf.parse(TIME);

            if(CURR.compareTo(GAME) == -1)
                return  "Good Evening ";
        }catch (Exception e){
        }

        return "Hello ";
    }


}
