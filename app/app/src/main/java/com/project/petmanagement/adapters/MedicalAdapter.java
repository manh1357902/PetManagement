package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.medical.MedicalDocumentDetailsActivity;
import com.project.petmanagement.models.entity.MedicalDocument;
import com.project.petmanagement.payloads.responses.Response;
import com.project.petmanagement.services.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MedicalDocumentViewHolder> {
    private Context context;
    private List<MedicalDocument> medicalList;
    private Long medicalId;

    public MedicalAdapter(Context context, List<MedicalDocument> medicalList) {
        this.context = context;
        this.medicalList = medicalList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMedicalList(List<MedicalDocument> medicalList) {
        this.medicalList = medicalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicalDocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_document, parent, false);
        return new MedicalDocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalDocumentViewHolder holder, int position) {
        final MedicalDocument medicalDocument = medicalList.get(position);
        holder.nameMedical.setText(medicalDocument.getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicalDocumentDetailsActivity.class);
                intent.putExtra("medicalId", medicalDocument.getId());
                context.startActivity(intent);
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo")
                        .setMessage("Bạn chắc chán muốn xóa document")
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                APIService.apiService.deleteMedicalDocument(medicalDocument.getId()).enqueue(new Callback<Response>() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                        if (response.isSuccessful()) {
                                            if (response.code() == 200) {
                                                medicalList.remove(medicalDocument);
                                                Toast.makeText(context, "Xóa document thành công.", Toast.LENGTH_SHORT).show();
                                                notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Response> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (medicalList != null) {
            return medicalList.size();
        }
        return 0;
    }

    private void openDialog() {

    }

    public static class MedicalDocumentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameMedical;
        private final RelativeLayout layout;

        public MedicalDocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMedical = itemView.findViewById(R.id.name_medical);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
