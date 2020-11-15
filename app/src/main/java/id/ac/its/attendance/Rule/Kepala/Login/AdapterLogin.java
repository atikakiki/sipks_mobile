package id.ac.its.attendance.Rule.Kepala.Login;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Sekolah.Sekolah;
import id.ac.its.attendance.Rule.Kepala.KepalaBagianActivity;
import id.ac.its.attendance.Utility.Constans;

import java.util.List;

public class AdapterLogin extends RecyclerView.Adapter<AdapterLogin.HolderView> {
    private List<Sekolah> sekolahs;
    private Context context;

    public AdapterLogin (List<Sekolah> sekolahs, Context context)
    {
        this.sekolahs = sekolahs;
        this.context = context;
    }
    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pilih, viewGroup, false);
        return new AdapterLogin.HolderView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, final int i) {
        holderView.nama.setText(sekolahs.get(i).getNama_sekolah());
        holderView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KepalaBagianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Constans.setId_sekolah(sekolahs.get(i).getId_sekolah());
                Constans.setNama_sekolah(sekolahs.get(i).getNama_sekolah());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sekolahs.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        TextView nama;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_seolah);
        }
    }
}
