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
public class Fragmentaddteacher extends Fragment {

    private EditText password,teachernames,addresss,contacts,departments;
    private Button register;
    public Fragmentaddteacher() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_fragmentaddteacher, container, false);
        password= view.findViewById(R.id.teacherspassswords);
        teachernames= view.findViewById(R.id.teachername);
        addresss=view.findViewById(R.id.teachersaddress);
        contacts=view.findViewById(R.id.teacherscontact);
        departments=view.findViewById(R.id.teachersdepartment);


        register=view.findViewById(R.id.btnregiterteacherroom);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Password = Integer.parseInt(password.getText().toString());
                String Teachername = teachernames.getText().toString();
                String Address = addresss.getText().toString();
                String Contact = contacts.getText().toString();
                String Department=departments.getText().toString();

                Teacher teacher=new Teacher();

                teacher.setPassword(Password);
                teacher.setTname(Teachername);
                teacher.setTaddress(Address);
                teacher.setTcontact(Contact);
                teacher.setTdepartment(Department);


                teacherActivity.myAppDatabaseTeachers.myDaoTeachers().addteachers(teacher);
                Toast.makeText(getActivity(), "teachers added successfully", Toast.LENGTH_SHORT).show();

                password.setText("");
                teachernames.setText("");
                addresss.setText("");
                contacts.setText("");
                departments.setText("");


            }
        });
        return view;
    }

}
