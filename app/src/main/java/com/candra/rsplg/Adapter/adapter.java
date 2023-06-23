package com.candra.rsplg.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.candra.rsplg.API.APIRequestData;
import com.candra.rsplg.API.RetroServer;
import com.candra.rsplg.Activity.Detail;
import com.candra.rsplg.Activity.MainActivity;
import com.candra.rsplg.Activity.Ubah;
import com.candra.rsplg.Model.ModelRS;
import com.candra.rsplg.Model.ModelResponse;
import com.candra.rsplg.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapter extends RecyclerView.Adapter<adapter.VHRumahSakit>{
    private Context ctx;
    private List<ModelRS> listRumahSakit;

    public adapter(Context ctx, List<ModelRS> listRumahSakit) {
        this.ctx = ctx;
        this.listRumahSakit = listRumahSakit;
    }

    @NonNull
    @Override
    public VHRumahSakit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_rsplg,parent,false);
        return new VHRumahSakit(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHRumahSakit holder, int position) {
        ModelRS MRS = listRumahSakit.get(position);
        holder.tvId.setText(MRS.getId());
        holder.tvNama.setText(MRS.getNama());
        holder.tvFoto.setText(MRS.getFoto());
        holder.tvDeskripsi.setText(MRS.getDeskripsi());
        holder.tvLokasi.setText(MRS.getLokasi());
        holder.tvKoordinat.setText(MRS.getKoordinat());
        Glide
                .with(ctx)
                .load(MRS.getFoto())
                .into(holder.ivFoto);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xFoto,xDeskripsi, xLokasi, xKoordinat;
                xNama = MRS.getNama();
                xFoto = MRS.getFoto();
                xDeskripsi = MRS.getDeskripsi();
                xLokasi = MRS.getLokasi();
                xKoordinat = MRS.getKoordinat();

                Intent kirim = new Intent(ctx, Detail.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xDeskripsi",xDeskripsi);
                kirim.putExtra("xLokasi",xLokasi);
                kirim.putExtra("xKoordinat",xKoordinat);
                ctx.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRumahSakit.size();
    }

    public class VHRumahSakit extends RecyclerView.ViewHolder{
        private TextView tvId,tvNama, tvFoto,tvDeskripsi, tvLokasi, tvKoordinat;
        private Button btnHapus, btnUbah, btnDetail;
        private ImageView ivFoto;
        public VHRumahSakit(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRumahSakit(tvId.getText().toString());
                }
            });

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, Ubah.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                    kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                    ctx.startActivity(kirim);
                }
            });
        }

        void deletePetShop(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan : "+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retriveRumahSakit();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}