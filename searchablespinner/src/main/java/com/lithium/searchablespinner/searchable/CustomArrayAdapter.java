package com.lithium.searchablespinner.searchable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amol on 30/05/23.
 */
public class CustomArrayAdapter extends RecyclerView.Adapter<CustomArrayAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<?> contactList;
    private List<?> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(android.R.id.text1);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()).toString(),getAdapterPosition());
                }
            });
        }
    }


    public CustomArrayAdapter(Context context, List<String> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            final String contact = contactListFiltered.get(position).toString();
            System.out.println(contact);
            holder.name.setText(contact);
        }catch (Exception e){

        }


    }
    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                try {


                    String charString = charSequence.toString();
                    if (charString.isEmpty()||charString.equalsIgnoreCase("")) {
                        contactListFiltered = contactList;
                    } else {
                        List<Object> filteredList = new ArrayList<>();
                        for (Object row : contactList) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.toString().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }else {
                                contactListFiltered=contactList;
                            }
                        }
                        contactListFiltered = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = contactListFiltered;
                    return filterResults;
                }catch (Exception e){

                }
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                try {
                    contactListFiltered = (ArrayList<?>) filterResults.values;
                    notifyDataSetChanged();
                }catch (Exception e){
                }
            }
        };
    }
    public Object getItem(int position) {
       return contactListFiltered.get(position);
    }
    public interface ContactsAdapterListener {
        void onContactSelected(String contact,int position);
    }
}
