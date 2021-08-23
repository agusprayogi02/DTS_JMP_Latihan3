package io.github.agusprayogi02.crudsqlite.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.agusprayogi02.crudsqlite.R;
import io.github.agusprayogi02.crudsqlite.model.StudentModel;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private ArrayList<StudentModel> list;
    private OnStudentClickListener listener;

    public StudentAdapter(ArrayList<StudentModel> list) {
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void uppList(ArrayList<StudentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnStudentClickListener {
        void onItemClick(View view, int position);
    }

    public void setListener(OnStudentClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        holder.bindViewHolder(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView email;
        private final LinearLayout rootL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.txtNama);
            email = itemView.findViewById(R.id.txtEmail);
            rootL = itemView.findViewById(R.id.item);
            rootL.setOnClickListener(
                    view -> listener.onItemClick(itemView, getBindingAdapterPosition())
            );
        }

        public void bindViewHolder(StudentModel item) {
            nama.setText(item.getNameField());
            email.setText(item.getNameField());
        }
    }
}
