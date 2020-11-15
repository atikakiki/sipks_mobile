package id.ac.its.attendance.Rule.Bendahara.Bos.Activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.BendaharaGaji.BodyGaji;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterGajiDetail extends RecyclerView.Adapter<AdapterGajiDetail.Holder> {
    private Context context;
    private List<BodyGaji> barangs;

    public AdapterGajiDetail(List<BodyGaji>barangs,Context context)
    {
        this.barangs = barangs;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_bendahara_gaji, viewGroup, false);
        return new AdapterGajiDetail.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.cardView.setBackgroundResource(R.drawable.boder_cardview_blue);
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
        float angka = Float.valueOf(barangs.get(i).getJumlah());
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        String format = decimalFormat.format(angka);
        holder.uang.setText(format);
        holder.title.setText(barangs.get(i).getDetail_komponen());
        holder.volum.setText(barangs.get(i).getSatuan());
        holder.jumlah.setText(barangs.get(i).getVolume());
        if (barangs.get(i).getNo_rekening()!=null)
        {
            holder.tujuan.setText(barangs.get(i).getNo_rekening());
        }
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tujuan,volum,jumlah, uang,title,status;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tujuan = itemView.findViewById(R.id.tujuan);
            uang = itemView.findViewById(R.id.uang);
            title = itemView.findViewById(R.id.namapenggunaan);
            volum = itemView.findViewById(R.id.detailvol);
            jumlah = itemView.findViewById(R.id.detailjumlah);
            cardView = itemView.findViewById(R.id.card_detail);
            status = itemView.findViewById(R.id.status);
        }
    }
}
