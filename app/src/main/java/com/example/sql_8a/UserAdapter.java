package com.example.sql_8a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
//    ContactClicked parentActivity;
//    public interface ContactClicked{
//        public void deleteContactFromList(int index);
//    }

    ArrayList<User> users;
    Context context;

    public UserAdapter(Context c, ArrayList<User> list)
    {
        context = c;
        users = list;
        //parentActivity = (ContactClicked) c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(users.get(position).getUsername());
        holder.tvPhone.setText(users.get(position).getPassword());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // delete code
                       MyDatabaseHelper database = new MyDatabaseHelper(context);
                       database.open();
                       database.deleteUser(users.get(holder.getAdapterPosition()).getId());
                       database.close();

                       users.remove(holder.getAdapterPosition());
                       notifyDataSetChanged();
                       //parentActivity.deleteContactFromList(holder.getAdapterPosition());
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

                return false;
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.edit_password_layout, null, false);
                editDialog.setView(view);

                EditText etUsername = view.findViewById(R.id.etUsername);
                EditText etPassword = view.findViewById(R.id.etPassword);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                Button btnCancel = view.findViewById(R.id.btnCancel);

                etUsername.setText(users.get(holder.getAdapterPosition()).getUsername());
                etPassword.setText(users.get(holder.getAdapterPosition()).getPassword());

                editDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etUsername.getText().toString().trim();
                        String phone = etPassword.getText().toString();
                        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                        myDatabaseHelper.open();
                        myDatabaseHelper.updateUser(users.get(holder.getAdapterPosition()).getId(),
                                name, phone);
                        myDatabaseHelper.close();

                        editDialog.dismiss();

                        users.get(holder.getAdapterPosition()).setUsername(name);
                        users.get(holder.getAdapterPosition()).setPassword(phone);
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvPhone;
        ImageView ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
