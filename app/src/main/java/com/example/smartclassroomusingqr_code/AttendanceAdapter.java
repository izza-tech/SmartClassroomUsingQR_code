package com.example.smartclassroomusingqr_code;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    private ArrayList<String> data ;
    public AttendanceAdapter(ArrayList<String> data){
        this.data=data;
    }
    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item,parent,false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.AttendanceViewHolder holder, int position) {
    String Date= data.get(position);
    holder.txtDate.setText(Date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate =(TextView) itemView.findViewById(R.id.Date);
        }
    }
}
