package com.hacker.eaun.cigmanotes.ui.Search_UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.model.MyNotesGS;
import java.util.List;

/**
 * Created by Eaun-Ballinger on 29/08/2016.
 *  Recycler View Adapter with card view
 *
 */

public class SearchAdapterRecyclerView extends
        RecyclerView.Adapter<SearchAdapterRecyclerView.SearchView> {

    private ThreadLocal<List<MyNotesGS>> DatabaseList;

    public SearchAdapterRecyclerView(List<MyNotesGS> DataList) {
        this.DatabaseList = new ThreadLocal<>();
        this.DatabaseList.set(DataList);
    }

    @Override
    public int getItemCount(){return DatabaseList.get().size();}

    @Override
    public SearchView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_layout, viewGroup, false);
        return new SearchView(v);
    }

    @Override
    public void onBindViewHolder(SearchView holder, int position) {
        MyNotesGS ci = DatabaseList.get().get(position);
        holder.vSupplierCode.setText(ci.getCode());
        holder.vSupplier.setText(ci.getSupplier());
        holder.vPlanner.setText(ci.getPlaner());
        holder.vPhoneNumber.setText(ci.getPhone());
        holder.vType.setText(ci.getType());
        holder.vCountry.setText(ci.getCountry());
        holder.vParts.setText(ci.getParts());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class SearchView extends RecyclerView.ViewHolder {

        TextView vSupplierCode;
        TextView vSupplier;
        TextView vPlanner;
        TextView vPhoneNumber;
        TextView vType;
        TextView vCountry;
        TextView vParts;


        public SearchView(View v) {
            super(v);

            vSupplierCode = (TextView) v.findViewById(R.id.id_Title_cs);
            vSupplier = (TextView) v.findViewById(R.id.id_supplier_cs);
            vPlanner = (TextView) v.findViewById(R.id.id_planner_cs);
            vPhoneNumber = (TextView) v.findViewById(R.id.id_phone_cs);
            vType = (TextView) v.findViewById(R.id.id_type_cs);
            vCountry = (TextView) v.findViewById(R.id.id_Country_cs);
            vParts = (TextView) v.findViewById(R.id.id_parts_cs);
        }
    }
}
