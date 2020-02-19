package com.studentlog.dashboard.fragment.studentlist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studentlog.R;
import com.studentlog.dashboard.fragment.StudentFormVM;
import com.studentlog.dashboard.fragment.adapter.StudentListAdapter;
import com.studentlog.model.Data;
import com.studentlog.model.local.Student;
import com.studentlog.room.LocalDatabase;
import com.studentlog.room.StudentDao;

public class StudentListFragment extends Fragment {

    StudentListAdapter adapter;

    StudentListVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyView(view);

        LocalDatabase.getInstance().studentDao().getAllStudents().observe(getViewLifecycleOwner(), students -> {
            adapter.populateLust(students);
        });

        subscribeObservers();
    }

    private void subscribeObservers(){

        viewModel = ViewModelProviders.of(this).get(StudentListVM.class);

        viewModel.onDeleteResObserver().removeObservers(getViewLifecycleOwner());

        viewModel.onDeleteResObserver().observe(getViewLifecycleOwner(), data -> {

            if(data!=null){
                switch (data.status){

                    case SUCCESS:{
                        Toast.makeText(getActivity(), "Student deleted successfully", Toast.LENGTH_SHORT).show();
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

    private void initRecyView(View view){

        RecyclerView recyclerView = view.findViewById(R.id.rv_student);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        adapter = new StudentListAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        adapter.onItemClickListener(student -> {
            viewModel.deleteStudent(student);
        });
    }





}
