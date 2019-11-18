package com.example.smartclassroomusingqr_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.Viewholder> {
RecyclerView recyclerView;
Context context;
ArrayList<String> items=new ArrayList<>();
    ArrayList<String> urls=new ArrayList<>();
    private CallbackValue callbackValue;

    public void update(String name,String url){
        items.add(name);
        urls.add(url);
        notifyDataSetChanged();//refeshes the recycler view automatically
    }

    public Myadapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls, CallbackValue callbackValue) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls=urls;
        this.callbackValue = callbackValue;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //initialize the elements of individual items...
        holder.nameoffile.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
TextView nameoffile;
        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            nameoffile=itemView.findViewById(R.id.nameoffiles);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildLayoutPosition(view);
                    callbackValue.onFetchValue(items.get(position),urls.get(position));

                }
            });

        }
    }
}
