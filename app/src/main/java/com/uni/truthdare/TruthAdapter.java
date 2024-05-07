package com.uni.truthdare;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TruthAdapter extends RecyclerView.Adapter<TruthAdapter.TruthViewHolder>  {
    private ArrayList<TruthItem> mTruthList;
    private Context mContext;

    public static class TruthViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TruthViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public TruthAdapter(ArrayList<TruthItem> truthList, Context context) {
        mTruthList = truthList;
        mContext = context;
    }
    @NonNull
    @Override
    public TruthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_card, parent, false);
        return new TruthViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TruthViewHolder holder, int position) {
        TruthItem currentItem = mTruthList.get(position);
        holder.textView.setText(currentItem.getText());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsDialog(view.getContext(), currentItem);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mTruthList.size();
    }

    // Method to show options dialog
    private void showOptionsDialog(final Context context, final TruthItem selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(selectedItem.getText());
        builder.setItems(new CharSequence[]{"Upload a photo", "Record a video", "Done"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Handle Upload a photo
                        uploadPhoto(context, selectedItem);
                        break;
                    case 1:
                        // Handle Record a video
                        uploadPhoto(context, selectedItem);
                        break;
                    case 2:
                        // Handle Done
                        removeItem(selectedItem);
                        break;
                }
            }
        });
        builder.show();
    }
    private void uploadPhoto(final Context context, final TruthItem item) {
        // Intent to open PhotoUploadActivity
        Intent intent = new Intent(context, PhotoUploadActivity.class);
        context.startActivity(intent);
    }


    private void removeItem(TruthItem item) {
        mTruthList.remove(item);
        notifyDataSetChanged();
    }
}
