package com.pass.passmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.pass.passmanager.adapters.CategoriasAdapter;
import com.pass.passmanager.databinding.ActivityMainBinding;
import com.pass.passmanager.modelos.Categoria;
import com.pass.passmanager.modelos.Cuenta;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private CategoriasAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private List<Categoria> catetgorias;

    private ArrayList<Cuenta> cuentas;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(binding.toolbar);

        database = FirebaseDatabase.getInstance("https://passmanager-294ea-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference(uid).child("Categorias");
        catetgorias = new ArrayList<>();
        adapter = new CategoriasAdapter(catetgorias, R.layout.categoria_view_holder, this, reference);
        lm = new GridLayoutManager(this,2);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catetgorias.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Categoria>> gti = new GenericTypeIndicator<ArrayList<Categoria>>() {};
                    catetgorias.addAll(snapshot.getValue(gti));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(lm);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCategoria().show();
                //startActivity(new Intent(MainActivity.this, CategoriaActivity.class));
            }
        });
    }

    private AlertDialog createCategoria(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear nueva categoría");
        builder.setCancelable(true);

        View categoriaView = getLayoutInflater().inflate(R.layout.categoria_alert, null);
        EditText txtNombre = categoriaView.findViewById(R.id.txtNombreCategoriaAlert);
        builder.setView(categoriaView);

        builder.setNegativeButton("Cancelar",null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = txtNombre.getText().toString();

                if (!nombre.isEmpty()){
                    catetgorias.add(new Categoria(nombre, cuentas));
                    reference.setValue(catetgorias);
                }
            }
        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.btnLogout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        return true;
    }
}