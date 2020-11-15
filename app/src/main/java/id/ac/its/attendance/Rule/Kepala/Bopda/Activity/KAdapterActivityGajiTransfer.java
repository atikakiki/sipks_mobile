package id.ac.its.attendance.Rule.Kepala.Bopda.Activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.BendaharaGaji.BodyGaji;
import id.ac.its.attendance.Utility.IdTransfe;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class KAdapterActivityGajiTransfer extends RecyclerView.Adapter<KAdapterActivityGajiTransfer.Holder> {
    private Context context;
    private List<BodyGaji> barangs;
    public ArrayList<IdTransfe> checked = new ArrayList<>();
    public KAdapterActivityGajiTransfer( List<BodyGaji> barangs,Context context) {
        this.context = context;
        this.barangs = barangs;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_detail, viewGroup, false);
        return new KAdapterActivityGajiTransfer.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        if (barangs.get(i).getStatus_approve().equals("2")||barangs.get(i).getStatus_approve().equals("3"))
        {
            holder.cek.setEnabled(false);
            holder.cek.setChecked(true);
            holder.cardView.setBackgroundResource(R.drawable.border_cardview_untouch);
            holder.tujuan.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.uang.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.tanda.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.rp.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.title.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.volum.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.jumlah.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimaryGrey));
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryGrey));
        }
        else
        {
            holder.cardView.setBackgroundResource(R.drawable.boder_cardview_blue);
            if (barangs.get(i).getNo_rekening()!=null)
            {
                holder.tujuan.setText(barangs.get(i).getNo_rekening());
            }
            if (holder.cek.isChecked())
            {
                IdTransfe baru = new IdTransfe(barangs.get(i).getKode_pekerjaan(),barangs.get(i).getId(),barangs.get(i).getNo_rekening(),barangs.get(i).getJumlah());
                checked.add(baru);
            }
            holder.cek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.cek.isChecked())
                    {
                        IdTransfe baru = new IdTransfe(barangs.get(i).getKode_pekerjaan(),barangs.get(i).getId(),barangs.get(i).getNo_rekening(),barangs.get(i).getJumlah());
                        checked.add(baru);

                    }
                    else
                    {
                        IdTransfe baru = new IdTransfe(barangs.get(i).getKode_pekerjaan(),barangs.get(i).getId(),barangs.get(i).getNo_rekening(),barangs.get(i).getJumlah());
                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.get(i).getId_pekerjaan().equals(baru.getId_pekerjaan()))
                            { //change here
                                checked.remove(i);
                                break;
                            }
                        }

                    }
                    Log.d(TAG, "onClick: "+String.valueOf(checked.size()));
                }
            });
        }
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

    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tujuan,volum,jumlah, uang,title,rp,tanda,status;
        CheckBox cek;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tujuan = itemView.findViewById(R.id.tujuan);
            uang = itemView.findViewById(R.id.uang);
            title = itemView.findViewById(R.id.namapenggunaan);
            volum = itemView.findViewById(R.id.detailvol);
            jumlah = itemView.findViewById(R.id.detailjumlah);
            cardView = itemView.findViewById(R.id.card_detail);
            cek = itemView.findViewById(R.id.cek);
            rp = itemView.findViewById(R.id.rp);
            tanda = itemView.findViewById(R.id.tanda);
            view = itemView.findViewById(R.id.garis);
            status = itemView.findViewById(R.id.status);
        }
    }
}
