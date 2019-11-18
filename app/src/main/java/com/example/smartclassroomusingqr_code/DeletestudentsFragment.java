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
public class DeletestudentsFragment extends Fragment {
private EditText rollno;
private Button deletestudent;

    public DeletestudentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_deletestudents, container, false);
        rollno=view.findViewById(R.id.deleteroll);
        deletestudent=view.findViewById(R.id.btndeletestudent);
        deletestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roll = Integer.parseInt(rollno.getText().toString());
                Studentss studentss=new Studentss();
                studentss.setRollNo(roll);
                fragmentActivity.myAppDatabase.myDao().deletestudent(studentss);
                Toast.makeText(getActivity(),"student successfully deleted",Toast.LENGTH_LONG).show();
                rollno.setText("");

            }
        });

        return view;
    }

}
