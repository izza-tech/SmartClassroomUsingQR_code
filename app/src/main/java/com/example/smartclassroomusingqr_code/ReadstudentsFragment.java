package com.example.smartclassroomusingqr_code;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadstudentsFragment extends Fragment {
private TextView txtinfo;

    public ReadstudentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_readstudents, container, false);
        txtinfo=view.findViewById(R.id.txt_display_info);
        List<Studentss> studentss= fragmentActivity.myAppDatabase.myDao().getstudentss();
        String info="";
        for (Studentss studentss1: studentss)
        {
            int Registeration=studentss1.getRegisteration();
            String studentname=studentss1.getName();
            int roll=studentss1.getRollNo();
            int Semester=studentss1.getSemester();
            String Address=studentss1.getAddress();
            int Contact=studentss1.getContact();
            String Department=studentss1.getDepartment();


            info= info+"Students Details "+"\n\n--------------------------------------------------------\n\n"+"Registeration :"+Registeration+"\n Name:"+studentname+"\n Roll no:"+roll+"\n Semester:"+Semester+"\n Address:"+Address+"\n Contact :"+Contact+"\n Department :"+Department;
        }

        txtinfo.setText(info);
        return view;
    }

}
