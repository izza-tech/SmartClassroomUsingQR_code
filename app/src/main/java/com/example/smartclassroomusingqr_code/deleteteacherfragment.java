package com.example.smartclassroomusingqr_code;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class deleteteacherfragment extends Fragment {
    private EditText password;
    private Button deleteteacher;

    public deleteteacherfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_deleteteacherfragment, container, false);

        password=view.findViewById(R.id.deletepass);
        deleteteacher=view.findViewById(R.id.btndeleteteacherroom);
        deleteteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int passwords = Integer.parseInt(password.getText().toString());
                Teacher teachers=new Teacher();
                teachers.setPassword(passwords);
                teacherActivity.myAppDatabaseTeachers.myDaoTeachers().deleteteacher(teachers);
                Toast.makeText(getActivity(),"teacher successfully deleted",Toast.LENGTH_LONG).show();
                password.setText("");

            }
        });
        return view;
    }

}
