package id.ac.its.attendance.Rule.Kepala.Bopda.Fragment.Gaji;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Response1Transfer.Data1Transfer;
import id.ac.its.attendance.Rule.Kepala.Bopda.Activity.TunaiGajiActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class KAdapaterGajiTunai extends RecyclerView.Adapter<KAdapaterGajiTunai.Holder> {
    private Context context;
    private List<Data1Transfer> barangs;
    public KAdapaterGajiTunai(List<Data1Transfer>barangs, Context context)
    {
        this.barangs = barangs;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_main, viewGroup, false);
        return new KAdapaterGajiTunai.Holder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.setIsRecyclable(false);
        String[] barangku = barangs.get(i).getPenyedia().split("\\|");
        int angka1 = barangku.length;
        if (barangs.get(i).getStatus_approve().equals("0"))
        {
            holder.status.setText("Baru dibuat");
        }
        else if (barangs.get(i).getStatus_approve().equals("1"))
        {
            holder.status.setText("Sudah disetujui bendahara");
        }
        else if (barangs.get(i).getStatus_approve().equals("2"))
        {
            holder.status.setText("Sudah distujui Kepsek");
        }
        else if (barangs.get(i).getStatus_approve().equals("3"))
        {
            holder.status.setText("Proses pengiriman");
        }
        if(angka1>1) {
            holder.detail.setText(barangku[1]);
        }
        else
        {
            holder.detail.setText(barangku[0]);
        }
        holder.no.setText(barangs.get(i).getBku());
        holder.nama.setText(barangs.get(i).getJudul());
        holder  .cardViev.setBackgroundResource(R.drawable.boder_cardview_blue);
        float angka = Float.valueOf(barangs.get(i).getTotal());
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        String format = decimalFormat.format(angka);
        holder.uang.setText(format.format(format));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TunaiGajiActivity.class);
                intent.putExtra("id",barangs.get(i).getKode());
                intent.putExtra("uang",barangs.get(i).getTotal());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView no,nama,detail,uang,status;
        CardView cardViev;

        public Holder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.namapenggunaan);
            detail = itemView.findViewById(R.id.detail);
            cardViev = itemView.findViewById(R.id.card_home);
            uang = itemView.findViewById(R.id.uangnya);
            status= itemView.findViewById(R.id.status);

        }
    }
}
