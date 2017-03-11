package com.hacker.eaun.cigmanotes.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.model.MyNotesGS;
import java.util.List;
import static com.hacker.eaun.cigmanotes.R.*;

/**
 * Created by Eaun-Ballinger on 27/08/2016.
 *
 *
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DatabaseViewHolder> {
    private ThreadLocal<List<MyNotesGS>> DatabaseList;

    public MyAdapter(List<MyNotesGS> DataList) {
        this.DatabaseList = new ThreadLocal<>();
        this.DatabaseList.set(DataList);
    }
    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return DatabaseList.get().size();
    }

    //Create new views (invoked by the layout manager)
    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        View v;
            v = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(layout.main_card_view_layout, viewGroup, false);
            return new DatabaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DatabaseViewHolder holder, int position) {
        MyNotesGS ci = DatabaseList.get().get(position);
        holder.vAction.setText(ci.getAction());
        holder.vFirstTitle.setText(ci.getTitle());
        holder.vMessage.setText(ci.getMessage());
        holder.vID = ci.getid();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class DatabaseViewHolder extends RecyclerView.ViewHolder {

        TextView vAction;
        TextView vFirstTitle;
        TextView vMessage;
        String vID;

        DatabaseViewHolder(View v) {
            super(v);
            vAction = (TextView) v.findViewById(id.txtActions);
            vFirstTitle = (TextView) v.findViewById(id.txtTitle);
            vMessage = (TextView) v.findViewById(id.txtMessage);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

}
