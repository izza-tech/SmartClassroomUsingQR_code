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
public class UpdateteachersFragment extends Fragment {
    private EditText password,teachernames,addresss,contacts,departments;
    private Button update;

    public UpdateteachersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_updateteachers, container, false);
        password= view.findViewById(R.id.teachersUPDATEpassswords);
        teachernames= view.findViewById(R.id.teacherUPDATEname);
        addresss=view.findViewById(R.id.teachersUPDATEaddress);
        contacts=view.findViewById(R.id.teachersUPDATEcontact);
        departments=view.findViewById(R.id.teachersUPDATEdepartment);


        update=view.findViewById(R.id.btnUPDATEteacherroom);
        update.setOnClickListener(new View.OnClickListener() {
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


                teacherActivity.myAppDatabaseTeachers.myDaoTeachers().updateteacher(teacher);
                Toast.makeText(getActivity(), "teachers updated successfully", Toast.LENGTH_SHORT).show();

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
