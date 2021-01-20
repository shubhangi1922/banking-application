package shubhangi.kumbhar.bankapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class ViewAllCustomerAdapter extends RecyclerView.Adapter<ViewAllCustomerAdapter.MyViewHolder> {
    Context context;
    View view;
    List<ViewAllCustomerModel> list;
    Activity activity;

    public ViewAllCustomerAdapter(Context context, List<ViewAllCustomerModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.tab_view_all_customers,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getCust_name());
        holder.contact.setText(list.get(position).getCust_contact());
        holder.account_no.setText(list.get(position).getCust_account_no());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        TextView name;
        TextView contact;
        TextView account_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.tv_cust_name);
            contact = itemView.findViewById(R.id.tv_mobile);
            account_no = itemView.findViewById(R.id.tv_account_no);
        }
    }
}
