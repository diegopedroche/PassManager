package com.pass.passmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.pass.passmanager.CategoriaActivity;
import com.pass.passmanager.MainActivity;
import com.pass.passmanager.R;
import com.pass.passmanager.modelos.Categoria;

import java.io.Serializable;
import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaVH> {

    private List<Categoria> objects;
    private int resource;
    private Context context;
    private DatabaseReference reference;

    public CategoriasAdapter(List<Categoria> objects, int resource, Context context, DatabaseReference reference) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.reference = reference;
    }

    @NonNull
    @Override
    public CategoriaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoriaView = LayoutInflater.from(context).inflate(resource, null);
        categoriaView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CategoriaVH(categoriaView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaVH holder, int position) {
        Categoria c = objects.get(position);
        holder.lbNombre.setText(c.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoriaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CATEGORIA", objects.get(holder.getAdapterPosition()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class CategoriaVH extends RecyclerView.ViewHolder{
        TextView lbNombre;

        public CategoriaVH(@NonNull View itemView) {
            super(itemView);
            lbNombre = itemView.findViewById(R.id.lbNombreCard);
        }
    }
}
