package shubhangi.kumbhar.bankapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import shubhangi.kumbhar.bankapplication.R;
import shubhangi.kumbhar.bankapplication.pojo.TransactionModel;
import shubhangi.kumbhar.bankapplication.pojo.ViewAllCustomerModel;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> implements Filterable {
    Context context;
    View view;
    List<TransactionModel> list,data;
    Activity activity;

    public TransactionAdapter(Context context, List<TransactionModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.data = list;

        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.tab_transaction,parent,false);
        return new TransactionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      if(list.get(position).getTransaction_type().equalsIgnoreCase("credit")) {
          holder.amount.setText("+ ₹"+list.get(position).getAmount());
          holder.amount.setTextColor(Color.parseColor("#009688"));

      }else{
          holder.amount.setText("- ₹"+list.get(position).getAmount());
          holder.amount.setTextColor(Color.parseColor("#F44336"));

      }

        holder.comment.setText(list.get(position).getTransaction_comment());
        holder.type.setText(list.get(position).getTransaction_type());
        holder.date.setText(list.get(position).getTransaction_date());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        TextView amount;
        TextView comment;
        TextView type;
        TextView date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            amount = itemView.findViewById(R.id.tv_amount);
            comment = itemView.findViewById(R.id.tv_comment);
            type = itemView.findViewById(R.id.tv_type);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    list =data;
                }else{
                    List<TransactionModel> filteredList = new ArrayList<>();
                    for(TransactionModel row :data){
                        if (row.getTransaction_type().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    list = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =  list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<TransactionModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
