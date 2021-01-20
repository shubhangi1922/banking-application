package shubhangi.kumbhar.bankapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.adapter.ViewAllCustomerAdapter;
import shubhangi.kumbhar.bankapplication.database.CustomersDb;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class ViewAllCustomerFragment extends Fragment {
    RecyclerView recyclerView;
    ViewAllCustomerAdapter adapter;
    List<ViewAllCustomerModel> list;
    public static Fragment newInstance() {
        return new ViewAllCustomerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_customer, container, false);
        initElements(view);
        return view;
    }

    private void initElements(View view) {
        list = new ArrayList<>();
        CustomersDb db = new CustomersDb(getContext());
        try {
            db.open();
            list.addAll(db.getAllCustomers());
            db.close();
        }catch(Exception e){

        }
        recyclerView=view.findViewById(R.id.recyclerView);

        adapter = new ViewAllCustomerAdapter(getContext(),list,getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapter);
    }
}
