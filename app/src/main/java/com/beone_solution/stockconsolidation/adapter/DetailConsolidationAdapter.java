package com.beone_solution.stockconsolidation.adapter;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beone_solution.stockconsolidation.OnEditTextChanged;
import com.beone_solution.stockconsolidation.R;
import com.beone_solution.stockconsolidation.model.DetailConsolidationModel;
import com.beone_solution.stockconsolidation.widget.RobotoRegularTextView;

import java.util.List;

public class DetailConsolidationAdapter extends RecyclerView
        .Adapter<DetailConsolidationAdapter
        .DataObjectHolder>
{
    private List<DetailConsolidationModel> mDataset;
    private OnEditTextChanged onEditTextChanged;

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        RobotoRegularTextView dtlSize;
        RobotoRegularTextView dtlFromBeg;
        RobotoRegularTextView dtlToBeg;
        TextInputEditText dtlSuggestion;
        RobotoRegularTextView dtlFromEnd;
        RobotoRegularTextView dtlToEnd;

        public DataObjectHolder(View itemView) {
            super(itemView);
            dtlSize = itemView.findViewById(R.id.dtl_size);
            dtlFromBeg = itemView.findViewById(R.id.dtl_from_beg);
            dtlToBeg = itemView.findViewById(R.id.dtl_to_beg);
            dtlSuggestion = itemView.findViewById(R.id.dtl_suggestion);
            dtlFromEnd = itemView.findViewById(R.id.dtl_from_end);
            dtlToEnd = itemView.findViewById(R.id.dtl_to_end);
        }
    }

    public DetailConsolidationAdapter(List<DetailConsolidationModel> myDataset, OnEditTextChanged onEditTextChanged) {
        mDataset = myDataset;
        this.onEditTextChanged = onEditTextChanged;
    }

//    public DetailConsolidationAdapter(List<DetailConsolidationModel> myDataset) {
//        mDataset = myDataset;
//    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_consolidation, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.dtlSize.setText(mDataset.get(position).getSize());
        holder.dtlFromBeg.setText(String.format("%.0f", mDataset.get(position).getFromBQty()));
        holder.dtlToBeg.setText(String.format("%.0f", mDataset.get(position).getToBQty()));
        holder.dtlSuggestion.setText(String.format("%.0f", mDataset.get(position).getSuggestedQty()));
        holder.dtlSuggestion.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double editedSuggestedQty = 0;
                double editedFromEndQty;
                double editedToEndQty;
                if(holder.dtlSuggestion.getText().length() == 0){
                    holder.dtlSuggestion.setText("0");
                }
                else{
                    editedSuggestedQty = Double.parseDouble(holder.dtlSuggestion.getText().toString());
                }
                if (editedSuggestedQty < 0){
                    holder.dtlSuggestion.setText("0");
                    editedSuggestedQty = 0;
                }
                mDataset.get(position).setSuggestedQty(editedSuggestedQty);
                editedFromEndQty = mDataset.get(position).getFromBQty() - editedSuggestedQty;
                editedToEndQty = mDataset.get(position).getToBQty() + editedSuggestedQty;
                holder.dtlFromEnd.setText(String.format("%.0f", editedFromEndQty));
                holder.dtlToEnd.setText(String.format("%.0f", editedToEndQty));
                onEditTextChanged.afterTextChanged(position, s.toString());
            }
        });

        // Ending
        double fromEQty = mDataset.get(position).getFromBQty() - mDataset.get(position).getSuggestedQty();
        double toEQty = mDataset.get(position).getToBQty() + mDataset.get(position).getSuggestedQty();
        holder.dtlFromEnd.setText(String.format("%.0f", fromEQty));
        holder.dtlToEnd.setText(String.format("%.0f", toEQty));
    }

    public void addItem(DetailConsolidationModel dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Public Methods
    public String getConID(int position) { return  mDataset.get(position).getConID(); }
    public String getArtNo(int position) { return  mDataset.get(position).getArtNo(); }
    public int getOfWeeks(int position) { return  mDataset.get(position).getOfWeeks(); }
    public Double getSellThru(int position) { return  mDataset.get(position).getSellThru(); }
    public Double getRetailPrice(int position) { return  mDataset.get(position).getRetailPrice(); }
    public Double getDisc(int position) { return  mDataset.get(position).getDisc(); }
    public String getFromWhsCode(int position) { return  mDataset.get(position).getFromWhsCode(); }
    public String getFromWhsName(int position) { return  mDataset.get(position).getFromWhsName(); }
    public String getToWhsCode(int position) { return  mDataset.get(position).getToWhsCode(); }
    public String getToWhsName(int position) { return  mDataset.get(position).getToWhsName(); }
    public String getSize(int position) { return  mDataset.get(position).getSize(); }
    public Double getFromBQty(int position) { return  mDataset.get(position).getFromBQty(); }
    public Double getToBQty(int position) { return  mDataset.get(position).getToBQty(); }
    public Double getSuggestedQty(int position) { return  mDataset.get(position).getSuggestedQty(); }
}
