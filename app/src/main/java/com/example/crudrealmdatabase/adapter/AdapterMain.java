package com.example.crudrealmdatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudrealmdatabase.R;
import com.example.crudrealmdatabase.model.ModelMasterData;

public class AdapterMain extends RealmRecyclerViewAdapter<ModelMasterData> {
    private Context mContext;
    private UbahDataInterface mUbahDataInterface;
    private HapusDataInterface mHapusDataInterface;

    public AdapterMain(Context context) {
        this.mContext = context;
    }

    public void ubahData(UbahDataInterface ubahDataInterface) {
        this.mUbahDataInterface = ubahDataInterface;
    }

    public void hapusData(HapusDataInterface hapusDataInterface) {
        this.mHapusDataInterface = hapusDataInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_master_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder       = (ViewHolder) viewHolder;
        ModelMasterData result  = getItem(position);

        holder.textViewAfdelings.setText(result.getAfdeling());
        holder.textViewBloks.setText(""+result.getBlok());
        holder.textViewGroupSamples.setText(""+result.getGroupSample());

        holder.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUbahDataInterface.ubahData(v, position);
            }
        });

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHapusDataInterface.hapusData(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public interface UbahDataInterface {
        void ubahData(View view, int position);
    }

    public interface HapusDataInterface {
        void hapusData(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAfdelings, textViewBloks, textViewGroupSamples;
        private Button btnHapus, btnUbah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAfdelings    = itemView.findViewById(R.id.textViewAfdeling);
            textViewBloks        = itemView.findViewById(R.id.textViewBlok);
            textViewGroupSamples = itemView.findViewById(R.id.textViewGroupSample);
            btnHapus             = itemView.findViewById(R.id.buttonHapus);
            btnUbah              = itemView.findViewById(R.id.buttonUbah);
        }
    }
}
