package shubhangi.kumbhar.bankapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.adapter.TransactionAdapter;
import shubhangi.kumbhar.bankapplication.adapter.ViewAllCustomerAdapter;
import shubhangi.kumbhar.bankapplication.database.CustomersDb;
import shubhangi.kumbhar.bankapplication.database.TransactionDb;
import shubhangi.kumbhar.bankapplication.pojo.TransactionModel;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class TransactionFragment extends Fragment {
    RecyclerView recyclerView;
    TransactionAdapter adapter;
    List<TransactionModel> list;
    public static Fragment newInstance() {
        return  new TransactionFragment();
    }

    Button btn_all,btn_credit,btn_debit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        initElements(view);
        return view;
    }

    private void initElements(View view) {
        btn_all = view.findViewById(R.id.btn_all);
        btn_credit = view.findViewById(R.id.btn_credit);

        btn_debit = view.findViewById(R.id.btn_debit);

        list = new ArrayList<>();
        TransactionDb db = new TransactionDb(getContext());
        try {
            db.open();
            list.addAll(db.getTransactionList("1"));
            Collections.reverse(list);
            db.close();
        }catch(Exception e){

        }
        recyclerView=view.findViewById(R.id.recyclerView);

        adapter = new TransactionAdapter(getContext(),list,getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapter);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getFilter().filter("");
            }
        });
        btn_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getFilter().filter("Credit");
            }
        });
        btn_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getFilter().filter("debit");
            }
        });
    }
}
