package id.ac.its.attendance.Rule.Bendahara.Bos.Fragment.BBarang;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.its.attendance.Rule.Bendahara.Bos.Activity.DetailTransaksiBarangActivity;
import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Response1Transfer.Data1Transfer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderView> {
    private Context context;
    private List<Data1Transfer> barangs;

    public AdapterBarang(List<Data1Transfer>barangs, Context context)
    {
        this.barangs = barangs;
        this.context = context;
    }
    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_main, viewGroup, false);
        return new AdapterBarang.HolderView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, final int i) {
        holderView.setIsRecyclable(false);
        if (barangs.get(i).getStatus_approve().equals("0"))
        {
            holderView.status.setText("Baru dibuat");
        }
        else if (barangs.get(i).getStatus_approve().equals("1"))
        {
            holderView.status.setText("Sudah disetujui bendahara");
        }
        else if (barangs.get(i).getStatus_approve().equals("2"))
        {
            holderView.status.setText("Sudah distujui Kepsek");
        }
        else if (barangs.get(i).getStatus_approve().equals("3"))
        {
            holderView.status.setText("Proses pengiriman");
        }
        String[] barangku = barangs.get(i).getPenyedia().split("\\|");
        int angka1 = barangku.length;
        if(angka1>1) {
            holderView.detail.setText(barangku[1]);
        }
        else
        {
            holderView.detail.setText(barangku[0]);
        }
        holderView.no.setText(barangs.get(i).getBku());
        holderView.nama.setText(barangs.get(i).getJudul());
        holderView.cardViev.setBackgroundResource(R.drawable.boder_cardview_blue);
        float angka = Float.valueOf(barangs.get(i).getTotal());
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        String format = decimalFormat.format(angka);
        holderView.uang.setText(format.format(format));
        holderView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTransaksiBarangActivity.class);
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

    public class HolderView extends RecyclerView.ViewHolder {
        TextView no,nama,detail,uang,status;
        CardView cardViev;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.namapenggunaan);
            detail = itemView.findViewById(R.id.detail);
            cardViev = itemView.findViewById(R.id.card_home);
            uang = itemView.findViewById(R.id.uangnya);
            status = itemView.findViewById(R.id.status);
        }
    }

}
