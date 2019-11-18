package com.example.smartclassroomusingqr_code;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private Button bnadduser, btviewuser, bndeleteuser, bnupdate;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        bnadduser = view.findViewById(R.id.btnaddstudents);
        bnadduser.setOnClickListener(this);
        btviewuser = view.findViewById(R.id.btnviewtudents);
btviewuser.setOnClickListener(this);


        bndeleteuser = view.findViewById(R.id.btndeletestudents);
        bndeleteuser.setOnClickListener(this);


        bnupdate = view.findViewById(R.id.btnupdatestudents);
        bnupdate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnaddstudents:
                fragmentActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddStudentFragment()).addToBackStack(null).commit();
                break;


            case R.id.btnviewtudents:
                fragmentActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new ReadstudentsFragment()).addToBackStack(null).commit();
                break;


            case R.id.btndeletestudents:
                fragmentActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new DeletestudentsFragment()).addToBackStack(null).commit();
                break;

            case R.id.btnupdatestudents:
                fragmentActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new UpdatestudentFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
