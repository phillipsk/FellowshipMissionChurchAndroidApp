package io.fmc.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.fmc.R;
import io.fmc.data.models.AboutUsModel;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private ArrayList<AboutUsModel> datas;

    public CustomRecyclerAdapter(ArrayList<AboutUsModel> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AboutUsModel model = datas.get(position);
        holder.bindModel(model);
    }

    @Override
    public int getItemCount() {
        return datas != null? datas.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView details;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            details = (TextView) itemView.findViewById(R.id.tv_details);
        }

        public void bindModel(AboutUsModel model) {
            title.setText(model.getTitle());
            details.setText(model.getDetails());
        }
    }

}
