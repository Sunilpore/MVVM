package com.studentlog.dashboard.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.studentlog.R;
import com.studentlog.dashboard.fragment.studentlist.StudentListFragment;

public class StudentFormFragment extends Fragment {

    EditText edStName, edStRollNo, edStStd, edStMobNo, edStEmail, edStPass;

    Button btnSubmit;

    private StudentFormVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_form,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edStName = view.findViewById(R.id.student_name);
        edStRollNo = view.findViewById(R.id.student_roll_no);
        edStStd = view.findViewById(R.id.student_standard);
        edStMobNo = view.findViewById(R.id.student_mob_no);
        edStEmail = view.findViewById(R.id.student_email);
        edStPass = view.findViewById(R.id.student_pass);

        btnSubmit = view.findViewById(R.id.btn_add_student);

        btnSubmit.setOnClickListener(v->{

            if(checkCredentials()){
                viewModel.createStudent(edStName.getText().toString(),
                                        edStRollNo.getText().toString(),
                                        edStStd.getText().toString(),
                                        edStMobNo.getText().toString(),
                                        edStEmail.getText().toString(),
                                        edStPass.getText().toString());
            }

        });


        subscribeObservers();

    }



    private void subscribeObservers(){

        viewModel = ViewModelProviders.of(this).get(StudentFormVM.class);

        viewModel.getInserResult().removeObservers(getViewLifecycleOwner());
        viewModel.getInserResult().observe(getViewLifecycleOwner(), data -> {

            if(data!=null){
                switch (data.status){

                    case SUCCESS:{
                        Toast.makeText(getActivity(), "Student created successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new StudentListFragment()).commit();
                        break;
                    }

                    case ERROR:{
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        });

    }


    private boolean checkCredentials(){

        if(TextUtils.isEmpty(edStName.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter UserName", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edStRollNo.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edStStd.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter the Standard", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edStMobNo.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edStEmail.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edStPass.getText().toString().trim())){
            Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }





}
