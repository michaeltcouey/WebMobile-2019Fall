package com.vijaya.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vijaya.sqlite.databinding.ActivityEmployeeUpdateBinding;



public class EmployeeUpdate extends AppCompatActivity {

    private ActivityEmployeeUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_update);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_update);


        Bundle bundle = getIntent().getExtras();
        final String employee_id = bundle.getString("EMPLOYEE_ID");
        final SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        final HashMap<String, String> update_data = new HashMap<String, String>();

        Cursor currentCursor = getByID(employee_id, database) ;

        pupulateInitialData(currentCursor);

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(employee_id, database);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employee_id, database);
            }
        });
    }

    private void pupulateInitialData(Cursor cursor){

        cursor.moveToFirst();

        binding.firstnameEditText.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_FIRSTNAME)
        ));

        binding.lastnameEditText.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_LASTNAME)
        ));
        binding.jobDescEditText.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION)
        ));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH)));
        binding.dobEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

        binding.employerTextView.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_NAME)
        ));

        binding.jobDescEditText.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_DESCRIPTION)
        ));

        calendar.setTimeInMillis(cursor.getLong(
                cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_FOUNDED_DATE)));
        binding.employedEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

    }

    private void deleteEmployee(String employee_id, SQLiteDatabase database){
        database.delete( SampleDBContract.Employee.TABLE_NAME,
                         SampleDBContract.Employee._ID + "=?",
                         new String[] { employee_id } ) ;

        Intent i = new Intent( EmployeeUpdate.this, com.vijaya.sqlite.EmployeeActivity.class);
        startActivity(i);
    }

    private void updateEmployee(String employee_id,
                                SQLiteDatabase database){

        ContentValues values = new ContentValues();

        values.put(SampleDBContract.Employee.COLUMN_FIRSTNAME, binding.firstnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_LASTNAME, binding.lastnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION, binding.jobDescEditText.getText().toString());

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(binding.dobEditText.getText().toString()));
            long date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH, date);
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(binding.employedEditText.getText().toString()));
            date = calendar.getTimeInMillis();
        } catch (Exception e) {
            Toast.makeText(this, "Date is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }


        database.update("employee" ,  values ,SampleDBContract.Employee._ID + "=?", new String[ ] {employee_id} );

        Intent i = new Intent( EmployeeUpdate.this, com.vijaya.sqlite.EmployeeActivity.class);
        startActivity(i);
    }

    private Cursor getByID(String employee_id, SQLiteDatabase database){
        String[] selectionArgs = {employee_id};
        Cursor cursor = database.rawQuery(SampleDBContract.SELECT_EMPLOYEE_BY_ID_WITH_EMPLOYER, selectionArgs);
        return cursor;
    }
}
