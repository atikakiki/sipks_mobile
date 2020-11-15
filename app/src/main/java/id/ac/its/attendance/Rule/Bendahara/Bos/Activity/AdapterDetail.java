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
import id.ac.its.attendance.Response.DetailTransfer.Body;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.Holder> {
    private Context context;
    private List<Body> barangs;

    public AdapterDetail(List<Body>barangs,Context context)
    {
        this.barangs = barangs;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_detailnya, viewGroup, false);
        return new AdapterDetail.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
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
        holder.pekerjaan.setText(barangs.get(i).getNama_komponen());
        holder.satuan.setText(barangs.get(i).getSatuan());
        holder.jumlah.setText(barangs.get(i).getVolume());
        holder.cardView.setBackgroundResource(R.drawable.boder_cardview_blue);
        float angka = Float.valueOf(barangs.get(i).getBayar());
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        String format = decimalFormat.format(angka);
        holder.uang.setText(format);
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView pekerjaan,uang,jumlah,satuan,status;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            pekerjaan =itemView.findViewById(R.id.namapenggunaan);
            uang = itemView.findViewById(R.id.uangnya);
            jumlah = itemView.findViewById(R.id.detailjumlah);
            satuan = itemView.findViewById(R.id.detailvol);
            cardView = itemView.findViewById(R.id.card_home);
            status = itemView.findViewById(R.id.status);
        }
    }
}
