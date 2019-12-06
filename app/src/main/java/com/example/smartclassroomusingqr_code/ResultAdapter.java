package com.example.smartclassroomusingqr_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    ArrayList<ResultModel> resultModels;
    public ResultAdapter(ArrayList<com.example.smartclassroomusingqr_code.ResultModel> resultModels){
        this.resultModels = resultModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list,parent,false);
        return new ResultAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stName.setText(resultModels.get(position).getName());
        holder.totalMarks.setText(resultModels.get(position).getTotalQuestion());
        holder.obtained.setText(resultModels.get(position).getMarks());

    }

    @Override
    public int getItemCount() {
        return resultModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stName;
        TextView totalMarks;
        TextView obtained;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stName = itemView.findViewById( R.id.txtname );
            totalMarks = itemView.findViewById( R.id.txttotalmarks );
            obtained = itemView.findViewById( R.id.txtobtainedmarks );
        }
    }
}
