package com.example.smartclassroomusingqr_code;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.smartclassroomusingqr_code.R.id.btnaddteachers;
import static com.example.smartclassroomusingqr_code.R.id.btndeleteteachers;
import static com.example.smartclassroomusingqr_code.R.id.btnupdateteachers;
import static com.example.smartclassroomusingqr_code.R.id.btnviewteachers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManagetyeachersFragment extends Fragment implements View.OnClickListener{
    private Button bnaddteacher,bnviewteacher, bndeleteteacher, bnupdateteacher;

    public ManagetyeachersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_managetyeachers, container, false);
        bnaddteacher = view.findViewById(btnaddteachers);
        bnaddteacher.setOnClickListener(this);
     bnviewteacher = view.findViewById(R.id.btnviewteachers);
       bnviewteacher.setOnClickListener(this);


      bndeleteteacher = view.findViewById(R.id.btndeleteteachers);
        bndeleteteacher.setOnClickListener(this);


        bnupdateteacher = view.findViewById(R.id.btnupdateteachers);
        bnupdateteacher.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case btnaddteachers:
                teacherActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container2, new Fragmentaddteacher()).addToBackStack(null).commit();
                break;


            case btnviewteachers:
                teacherActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container2, new readteachersFragment()).addToBackStack(null).commit();
                break;


            case btndeleteteachers:
                teacherActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container2, new deleteteacherfragment()).addToBackStack(null).commit();
                break;

            case btnupdateteachers:
                teacherActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container2, new UpdateteachersFragment()).addToBackStack(null).commit();
                break;

        }
    }
}
