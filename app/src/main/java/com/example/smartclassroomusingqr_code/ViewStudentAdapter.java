package com.example.smartclassroomusingqr_code;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentAdapter.ViewHolder> {
    ArrayList<profiledata> profiledata;
    public ViewStudentAdapter(ArrayList<com.example.smartclassroomusingqr_code.profiledata> profiledata){
        this.profiledata = profiledata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(profiledata.get(position).getIMAGEURL()).into(holder.studentImage);
        holder.stSemester.setText(profiledata.get(position).getSEMESTER());
        holder.stMobile.setText(profiledata.get(position).getMOBILE());
        holder.stEmail.setText(profiledata.get(position).getEMAIL());
        holder.stName.setText(profiledata.get(position).getNAME());
    }

    @Override
    public int getItemCount() {
        return profiledata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView studentImage;
        TextView stName,stMobile,stEmail,stSemester;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentImage = itemView.findViewById(R.id.imgProfile);
            stName = itemView.findViewById(R.id.stName);
            stSemester = itemView.findViewById(R.id.stSemester);
            stMobile = itemView.findViewById(R.id.stMobile);
            stEmail = itemView.findViewById(R.id.stEmail);
        }
    }
}
