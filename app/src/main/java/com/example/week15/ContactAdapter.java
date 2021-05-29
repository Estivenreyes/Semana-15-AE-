package com.example.week15;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    private ArrayList<Contact> contactsList;
    private static final int REQUEST_CALL =1;


    public ContactAdapter() {
        contactsList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View renglon = layoutInflater.inflate(R.layout.contactview,null);
        TextView name = renglon.findViewById(R.id.nameTv);
        TextView phone = renglon.findViewById(R.id.phoneTv);
        ImageView call = renglon.findViewById(R.id.btnCall);
        ImageView delete = renglon.findViewById(R.id.btnDelete);
        name.setText(contactsList.get(i).getName());
        phone.setText(contactsList.get(i).getPhoneNumber());
        call.setOnClickListener(
                (v)->{

                    if(ContextCompat.checkSelfPermission(viewGroup.getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions((Activity) viewGroup.getContext(),new String[]{
                                Manifest.permission.CALL_PHONE},REQUEST_CALL);

                    }
                    else{
                        MakeCall(viewGroup.getContext(),contactsList.get(i).getPhoneNumber());

                    }
                }
        );

        delete.setOnClickListener(
                (v)->{

                    String id = contactsList.get(i).getId();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Contacts").child(id);
                    databaseReference.setValue(null);
                }
        );

        return renglon;
    }
    public void ClearContacts(){

        contactsList.clear();
        notifyDataSetChanged();
    }

    public void AddContact(Contact contact){

        contactsList.add(contact);
        notifyDataSetChanged();
    }

    public void MakeCall(Context context, String phone){

        String dial = "tel:"+phone;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
        context.startActivity(callIntent);
    }


}
