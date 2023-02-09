package com.pass.passmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.pass.passmanager.databinding.ActivityCategoriaBinding;
import com.pass.passmanager.modelos.Categoria;
import com.pass.passmanager.modelos.Cuenta;

import java.util.ArrayList;

public class CategoriaActivity extends AppCompatActivity {
    private ArrayList<Cuenta> cuentas;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FloatingActionButton fab;
    private Cuenta cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        inicializaVistas();
        database = FirebaseDatabase.getInstance("https://passmanager-294ea-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference(uid).child("Categorias").child(cuenta.getNombre());
        cuentas = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cuentas.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Cuenta>> gti = new GenericTypeIndicator<ArrayList<Cuenta>>() {};
                    cuentas.addAll(snapshot.getValue(gti));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                String nombre = "dipemol";
                String password = "1111111";
                Categoria c = (Categoria) bundle.getSerializable("CATEGORIA");
                Cuenta cuenta = new Cuenta(nombre,password,c.getNombre());

                cuentas.add(cuenta);
                reference.setValue(cuentas);
            }
        });
    }

    private void inicializaVistas() {
        fab = findViewById(R.id.fabCuenta);
    }
}