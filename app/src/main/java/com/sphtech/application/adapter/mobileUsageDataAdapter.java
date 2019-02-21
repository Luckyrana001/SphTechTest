package com.sphtech.application.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphtech.application.R;
import com.sphtech.application.listener.ImageClickedListener;
import com.sphtech.application.model.YearDataModel;

import java.util.ArrayList;

public class MobileUsageDataAdapter extends RecyclerView.Adapter<MobileUsageDataAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<YearDataModel> yearlyDataList;
    private ImageClickedListener listener;

    public MobileUsageDataAdapter(Context context, ArrayList<YearDataModel> dataList, ImageClickedListener listener) {
        this.context = context;
        this.yearlyDataList = dataList;
        this.listener = listener;
    }

    @Override
    public MobileUsageDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_item_view_layout, parent, false);

        return new MobileUsageDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobileUsageDataAdapter.MyViewHolder holder, final int position) {
        final YearDataModel data = yearlyDataList.get(position);

        holder.totalDataConsumptionTv.setText(data.getTotalYearlyataConsumption() + "");

        holder.yearNameTv.setText("Total Data Consumption For the Year: " + data.getYear());

        if (data.getIsthereDecreaseInVolume()) {
            holder.decreaseFoundinVolIv.setVisibility(View.VISIBLE);
        } else {
            holder.decreaseFoundinVolIv.setVisibility(View.GONE);
        }
        holder.decreaseFoundinVolIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.imageClicked();
            }
        });

    }

    @Override
    public int getItemCount() {
        return yearlyDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView decreaseFoundinVolIv;
        private TextView totalDataConsumptionTv;
        private TextView yearNameTv;


        public MyViewHolder(View view) {
            super(view);
            yearNameTv = view.findViewById(R.id.yearNameTv);
            decreaseFoundinVolIv = view.findViewById(R.id.dataVoldecreaseIv);
            totalDataConsumptionTv = view.findViewById(R.id.total_data_consumption_tv);

        }
    }


}