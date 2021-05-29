package com.example.week15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.UUID;

public class ContactActivity extends AppCompatActivity {

    private EditText ContactInput, phoneInput;
    private String userName;
    private Button addBtn;
    private User activeUser;
    private FirebaseDatabase db;
    private ContactAdapter contactAdapter;
    private ValueEventListener valueEventListener;
    private ListView ContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Bundle bundle = getIntent().getExtras();
        db = FirebaseDatabase.getInstance();
        ContactInput = findViewById(R.id.ContactInput);
        phoneInput = findViewById(R.id.phoneInput);
        addBtn = findViewById(R.id.addBtn);
        ContactList = findViewById(R.id.contactList);
        userName = bundle.getString("name", null);
        contactAdapter = new ContactAdapter();
        UserExist();
        addBtn.setOnClickListener(
                (v) -> {

                    if (ContactInput.getText().toString().isEmpty() || phoneInput.getText().toString().isEmpty()) {

                        Toast.makeText(this, "Por favor verifique los datos ingresados", Toast.LENGTH_SHORT).show();
                    } else {

                        db.getReference().child("Contacts").orderByChild("name").equalTo(ContactInput.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()) {

                                    Toast.makeText(ContactActivity.this, "Ya existe un contacto con este nombre", Toast.LENGTH_SHORT).show();
                                } else {
                                    String id = UUID.randomUUID().toString();
                                    Contact tempc = new Contact(id,activeUser.getId(), ContactInput.getText().toString(), phoneInput.getText().toString());
                                    db.getReference().child("Contacts").child(id).setValue(tempc);
                                    phoneInput.setText("");
                                    ContactInput.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }


                }
        );

        listaContactos.setAdapter(contactAdapter);
    }

    private void LoadDatabase() {

        valueEventListener = db.getReference().child("Contacts").orderByChild("idUser").equalTo(activeUser.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactAdapter.ClearContacts();
                for (DataSnapshot child : snapshot.getChildren()) {

                    Contact tempC = child.getValue(Contact.class);
                    Log.e("TAG", tempC.getName());
                    contactAdapter.AddContact(tempC);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void UserExist() {

        db.getReference().child("User").orderByChild("name").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds:snapshot.getChildren()) {
                        activeUser=ds.getValue(User.class);
                    }

                } else {
                    String id = UUID.randomUUID().toString();
                    User tempUser = new User(id, userName);
                    db.getReference("User").child(id).setValue(tempUser);
                    activeUser = tempUser;
                    Log.e("TAG", activeUser.getId());
                }

                LoadDatabase();

            }

            @Override
            public void onCancelled(DatabaseError error) {

                throw error.toException();
            }
        });

    }

    @Override
    protected void onPause() {
        db.getReference().removeEventListener(valueEventListener);
        super.onPause();
    }

}