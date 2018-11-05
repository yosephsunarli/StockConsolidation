package com.beone_solution.stockconsolidation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beone_solution.stockconsolidation.R;
import com.beone_solution.stockconsolidation.model.DetailConsolidationModel;
import com.beone_solution.stockconsolidation.model.ListConsolidationModel;
import com.beone_solution.stockconsolidation.widget.RobotoRegularTextView;

import java.util.ArrayList;
import java.util.List;

public class ListConsolidationAdapter extends RecyclerView
        .Adapter<ListConsolidationAdapter
        .DataObjectHolder>
        implements Filterable
{
    private List<ListConsolidationModel> mDataset;
    private List<ListConsolidationModel> mFiltered;
    private ListConsolidationClickListener listConsolidationClickListener;

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout listConsolidation;
        LinearLayout listConsPanel;
        RobotoRegularTextView artno;
        RobotoRegularTextView artName;
        RobotoRegularTextView fromwhs;
        RobotoRegularTextView frombeg;
        RobotoRegularTextView fromend;
        RobotoRegularTextView towhs;
        RobotoRegularTextView tobeg;
        RobotoRegularTextView toend;
        ImageView vertSeparator;
        CheckBox checkBox;

        public DataObjectHolder(View itemView) {
            super(itemView);
            listConsolidation = itemView.findViewById(R.id.listConsolidation);
            listConsPanel = itemView.findViewById(R.id.listConsPanel);
            artno = itemView.findViewById(R.id.artno);
            artName = itemView.findViewById(R.id.artname);
            fromwhs = itemView.findViewById(R.id.fromwhs);
            frombeg = itemView.findViewById(R.id.frombeg);
            fromend = itemView.findViewById(R.id.fromend);
            towhs = itemView.findViewById(R.id.towhs);
            tobeg = itemView.findViewById(R.id.tobeg);
            toend = itemView.findViewById(R.id.toend);
            vertSeparator = itemView.findViewById(R.id.vertical_separator);
            checkBox = itemView.findViewById(R.id.checkbox);
            checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int adapterPosition = getAdapterPosition();
                    if (mFiltered.get(adapterPosition).getChecked()) {
                        checkBox.setChecked(false);
                        mFiltered.get(adapterPosition).setChecked(false);
                    }
                    else {
                        checkBox.setChecked(true);
                        mFiltered.get(adapterPosition).setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listConsolidationClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(ListConsolidationClickListener mClickListener) {
        listConsolidationClickListener = mClickListener;
    }

    public interface ListConsolidationClickListener {
        void onItemClick(int position, View v);
    }

    public ListConsolidationAdapter(List<ListConsolidationModel> myDataset) {
        mDataset = myDataset;
        mFiltered = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consolidation, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.artno.setText(mFiltered.get(position).getArtNo());
        holder.artName.setText(mFiltered.get(position).getArtName());
        holder.fromwhs.setText(mFiltered.get(position).getFromWhsName());
        holder.frombeg.setText(String.format("%.0f", mFiltered.get(position).getFromBQty()));
        holder.fromend.setText(String.format("%.0f", mFiltered.get(position).getFromEQty()));
        holder.towhs.setText(mFiltered.get(position).getToWhsName());
        holder.tobeg.setText(String.format("%.0f", mFiltered.get(position).getToBQty()));
        holder.toend.setText(String.format("%.0f", mFiltered.get(position).getToEQty()));
        if (position == (mFiltered.size() - 1)) {
            holder.vertSeparator.setVisibility(View.GONE);
        }
        else{
            holder.vertSeparator.setVisibility(View.VISIBLE);
        }
        if (mFiltered.get(position).getChecked()) {
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }
    }

    public void addItem(ListConsolidationModel dataObj, int index) {
        mFiltered.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mFiltered.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFiltered = mDataset;
                } else {
                    List<ListConsolidationModel> filteredList = new ArrayList<>();
                    for (ListConsolidationModel row : mDataset) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getArtNo().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getFromWhsName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getToWhsName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getArtName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFiltered = (ArrayList<ListConsolidationModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Public Methods
    public List<ListConsolidationModel> getFilteredData() { return mFiltered; }
    public String getConID(int position) { return  mFiltered.get(position).getConID(); }
    public String getArtNo(int position) { return  mFiltered.get(position).getArtNo(); }
    public String getArtName(int position) { return  mFiltered.get(position).getArtName(); }
    public String getFromWhsName(int position) { return  mFiltered.get(position).getFromWhsName(); }
    public Double getFromBQty(int position) { return  mFiltered.get(position).getFromBQty(); }
    public Double getFromEQty(int position) { return  mFiltered.get(position).getFromEQty(); }
    public String getToWhsName(int position) { return  mFiltered.get(position).getToWhsName(); }
    public Double getToBQty(int position) { return  mFiltered.get(position).getToBQty(); }
    public Double getToEQty(int position) { return  mFiltered.get(position).getToEQty(); }
}
