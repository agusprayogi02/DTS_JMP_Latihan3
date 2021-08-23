package io.github.agusprayogi02.crudsqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import io.github.agusprayogi02.crudsqlite.adapter.StudentAdapter;
import io.github.agusprayogi02.crudsqlite.helper.DatabaseHelper;
import io.github.agusprayogi02.crudsqlite.model.StudentModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StudentAdapter.OnStudentClickListener {

    public TextInputEditText editNama;
    public TextInputEditText editEmail;
    public DatabaseHelper db;
    public int id;
    public boolean edited;
    public ArrayList<StudentModel> list;
    private StudentAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("My Students");
        editNama = findViewById(R.id.inpNama);
        editEmail = findViewById(R.id.inpEmail);
        db = new DatabaseHelper(this);
        rv = findViewById(R.id.rvMain);

        init();
    }

    void init() {
        findViewById(R.id.btnSimpan).setOnClickListener(this);
        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnHapus).setOnClickListener(this);
        findViewById(R.id.btnBersih).setOnClickListener(this);
        list = db.getAllStudent();
        adapter = new StudentAdapter(list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnEdit) {
            visibility(true);
            edited = true;
        } else if (viewId == R.id.btnHapus) {
            long rest = db.deleteStudent(id - 1);
            if (rest < 0) {
                makeToast("Gagal, Menghapus data!");
            } else {
                makeToast("Berhasil, Menghapus data!");
                adapter.deleteItem(id - 1);
                setEmptyText();
                visibility(true);
            }
        } else if (viewId == R.id.btnSimpan) {
            StudentModel murid = new StudentModel(0, null, null);
            murid.setNameField(Objects.requireNonNull(editNama.getText()).toString());
            murid.setEmailField(Objects.requireNonNull(editEmail.getText()).toString());
            long rest;
            String message = "";
            if (edited) {
                murid.setIdField(id);
                rest = db.updateStudent(murid);
                edited = !edited;
                message = "Menghapus";
            } else {
                rest = db.addStudent(murid);
                message = "Menambahkan";
            }
            if (rest < 0) {
                makeToast("Gagal, " + message + " data!");
            } else {
                makeToast("Berhasil, " + message + " data!");
                updateAll();
            }
        } else if (viewId == R.id.btnBersih) {
            visibility(true);
            setEmptyText();
        }
    }

    void updateAll() {
        list = db.getAllStudent();
        adapter.uppList(list);
        setEmptyText();
    }

    void setEmptyText() {
        editEmail.setText("");
        editNama.setText("");
        editNama.findFocus();
    }

    void makeToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    void visibility(Boolean status) {
        editEmail.setEnabled(status);
        editNama.setEnabled(status);
    }

    @Override
    public void onItemClick(View view, int position) {
        StudentModel item = list.get(position);
        editNama.setText(item.getNameField());
        editEmail.setText(item.getEmailField());
        id = position + 1;
        visibility(false);
    }
}