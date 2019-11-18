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
public class UpdatestudentFragment extends Fragment {
    private EditText registerations,Studentnames,rollnos,semesters,addresss,contacts,departments;
    private Button update;

    public UpdatestudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_updatestudent, container, false);
        registerations= view.findViewById(R.id.updatereg_no);
        Studentnames= view.findViewById(R.id.updatestudentname);
        rollnos= view.findViewById(R.id.updateroll);

        semesters=view.findViewById(R.id.updatesemester);
        addresss=view.findViewById(R.id.updateaddress);
        contacts=view.findViewById(R.id.updatecontact);
        departments=view.findViewById(R.id.updatedepartment);

        update=view.findViewById(R.id.btnupdatesstudents);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Registeration = Integer.parseInt(registerations.getText().toString());
                String studentname = Studentnames.getText().toString();
                int roll = Integer.parseInt(rollnos.getText().toString());
                int Semester = Integer.parseInt(semesters.getText().toString());
                String Address = addresss.getText().toString();
                int Contact = Integer.parseInt(contacts.getText().toString());
                String Department=departments.getText().toString();

                Studentss studentss=new Studentss();

                studentss.setRegisteration(Registeration);
                studentss.setName(studentname);
                studentss.setRollNo(roll);
                studentss.setSemester(Semester);
                studentss.setAddress(Address);
                studentss.setContact(Contact);
                studentss.setDepartment(Department);


                fragmentActivity.myAppDatabase.myDao().updatestudent(studentss);
                Toast.makeText(getActivity(), "student updated successfully", Toast.LENGTH_SHORT).show();

                registerations.setText("");
                Studentnames.setText("");
                rollnos.setText("");
                semesters.setText("");
                addresss.setText("");
                contacts.setText("");
                departments.setText("");

            }
        });
        return view;
    }

}
