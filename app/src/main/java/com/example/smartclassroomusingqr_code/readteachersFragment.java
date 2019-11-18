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
public class readteachersFragment extends Fragment {

private TextView txtdisplayteacherss;
    public readteachersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_readteachers, container, false);

        txtdisplayteacherss=view.findViewById(R.id.txtdisplayteachers);
        List<Teacher> teacherss= teacherActivity.myAppDatabaseTeachers.myDaoTeachers().getteachers();
        String info="";
        for (Teacher teachers1: teacherss)
        {
            int Password=teachers1.getPassword();
            String teachersname=teachers1.getTname();
            String Address=teachers1.getTaddress();
            String Contact=teachers1.getTcontact();
            String Department=teachers1.getTdepartment();


            info= info+"Teachers Details "+"\n\n--------------------------------------------------------\n\n"+"Password :"+Password+"\n Name:"+teachersname+"\n Address:"+Address+"\n Contact :"+Contact+"\n Department :"+Department;
        }

        txtdisplayteacherss.setText(info);
        return view;
    }

}
