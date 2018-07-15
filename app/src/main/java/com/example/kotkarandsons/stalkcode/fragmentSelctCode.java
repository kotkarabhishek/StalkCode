package com.example.kotkarandsons.stalkcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class fragmentSelctCode extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentselectedcode,null);
    }
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Button save,datepickerbtn,codecollection;
    Spinner code;
    Spinner level;
    Spinner  status;
    String SelectedCode;
    String CodeType;
    String CodeStatus;
    String CodeLevel;
    String dateSelected;
    EditText editCode;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref;
    Calendar mCalender;
    int date,month,year;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        code=(Spinner)view.findViewById(R.id.spinnercode);
        level=(Spinner)view.findViewById(R.id.spinnerlevel);
        status=(Spinner)view.findViewById(R.id.spinnerstatus);
        editCode=(EditText)view.findViewById(R.id.codeName);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        codecollection=(Button)view.findViewById(R.id.seeCodes);
        save=view.findViewById(R.id.codesubmitbutton);
        datepickerbtn=view.findViewById(R.id.datepick);
        firebaseDatabase=FirebaseDatabase.getInstance();

        mCalender=Calendar.getInstance();
        date=mCalender.get(Calendar.DAY_OF_MONTH);
        month=mCalender.get(Calendar.MONTH);
        year=mCalender.get(Calendar.YEAR);


        ArrayAdapter<String> arrayAdaptercode=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        arrayAdaptercode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        code.setAdapter(arrayAdaptercode);
        ArrayAdapter<String> arrayAdapterlevel=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.level));
        arrayAdaptercode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(arrayAdapterlevel);
        ArrayAdapter<String> arrayAdapterstatus=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.status));
        arrayAdapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(arrayAdapterstatus);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedCode=editCode.getText().toString().trim();
                CodeStatus=status.getSelectedItem().toString();
                CodeLevel=level.getSelectedItem().toString();
                CodeType=code.getSelectedItem().toString();
                CodeInfo codeInfo=new CodeInfo(SelectedCode,CodeType,CodeLevel,CodeStatus,dateSelected);
                myref=firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid()).child("Codes").child(SelectedCode);
                myref.setValue(codeInfo);
            }
        });

        datepickerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        dateSelected=dayOfMonth+"-"+month+"-"+year;
                    }
                },year,month,date);
                datePickerDialog.show();
               // Toast.makeText(getActivity(),dateSelected,Toast.LENGTH_SHORT).show();
            }

        });

        codecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CodeListDisplay.class));
            }
        });



    }
}
