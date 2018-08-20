package com.gainwise.gaine.randompass;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

/**
 * Created by gaine on 12/26/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PasswordEntryViewHolder>{

    private Context myContext;
    PasswordEntry passwordEntry;
    private List<PasswordEntry> passwordEntryList;
    Util util;


    public MyAdapter(Context myContext, List<PasswordEntry> passwordEntryList) {
        this.myContext = myContext;
        this.passwordEntryList = passwordEntryList;
        this.util = new Util(this.myContext);
    }

    @Override
    public PasswordEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.customlayout, null);
        return new PasswordEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasswordEntryViewHolder holder, int position) {
         passwordEntry = passwordEntryList.get(position);
        Typeface custom_font = Typeface.createFromAsset(myContext.getAssets(), "fonts/font.ttf");

        holder.password.setText(passwordEntry.getPassword());
        holder.imageView.setImageLevel(passwordEntry.getImageNum());
        holder.label.setText(passwordEntry.getLabel());
        holder.password.setTypeface(custom_font);
        holder.label.setTypeface(custom_font);



    }



    @Override
    public int getItemCount() {
       return passwordEntryList.size();
    }

    class PasswordEntryViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView label;
        TextView password;

        public PasswordEntryViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            label = itemView.findViewById(R.id.textViewTitle);
            password = itemView.findViewById(R.id.textViewPassword);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) myContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Pass copy", password.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    FabToast.makeText(myContext, "Success! Copying...", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_CENTER ).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final String tempPass = password.getText().toString();
                    final String tempLabel = label.getText().toString();
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(myContext, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(myContext);
                    }
                    builder.setTitle(" Delete Entry")
                            .setMessage("Are you sure you want to delete this entry?\n\n" + tempLabel + "\n\n" +
                                    tempPass)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    util.deleteItemWithPass(tempPass, tempLabel);
                                    Intent intent = new Intent(myContext, Main2Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    myContext.startActivity(intent);
                                    Bungee.fade(myContext);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return true;
                }
            });
        }





    }
}
