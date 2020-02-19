package com.studentlog.dashboard.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studentlog.R;
import com.studentlog.model.local.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private ArrayList<Student> students = new ArrayList<>();
    private Context mContext;
    OnItemClickListener listener;

    public StudentListAdapter(Context context) {
        this.mContext = context;
    }

    public void populateLust(List<Student> list){
        students.clear();
        students.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        Student model = students.get(position);
        if(model!=null){
            holder.tvUserName.setText(model.getName());
            holder.tvMobNo.setText(model.getMobileNumber());
        }

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvMobNo;
        ImageView ivDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tv_student_name);
            tvMobNo = itemView.findViewById(R.id.tv_student_mob_no);
            ivDelete = itemView.findViewById(R.id.iv_delete);

            ivDelete.setOnClickListener(v->{
                listener.onItemClick(students.get(getAdapterPosition()));
            });

        }

    }

    public void onItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }


}
