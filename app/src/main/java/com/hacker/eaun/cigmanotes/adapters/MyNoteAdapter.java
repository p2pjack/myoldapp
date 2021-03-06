package com.hacker.eaun.cigmanotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hacker.eaun.cigmanotes.NoteActivity;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.passthrough.NoteGS;
import java.util.List;

/**
 * Created by Eaun-Ballinger on 31/08/2016.
 *
 *
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.DatabaseViewHolder> {
    private ThreadLocal<List<NoteGS>> DatabaseList;
    private String GetMyId;
    private Context mContext;

    public MyNoteAdapter(Context pContext,  List<NoteGS> DataList) {
        this.DatabaseList = new ThreadLocal<>();
        this.DatabaseList.set(DataList);
        this.mContext = pContext;
    }

    class DatabaseViewHolder extends RecyclerView.ViewHolder {
        TextView vFirstTitle;
        TextView vMessage;
        String vID;

        DatabaseViewHolder(View v) {
            super(v);
            vFirstTitle = (TextView) v.findViewById(R.id.note_title);
            vMessage = (TextView) v.findViewById(R.id.note_message);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View pView) {
                    GetMyId = vID;
                    OpenDetails(GetMyId);
                }
            });
        }
    }

    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.note_card_view_layout, parent, false);
        return new DatabaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DatabaseViewHolder holder, int position)  {
        NoteGS ci = DatabaseList.get().get(position);
        holder.vFirstTitle.setText(ci.getTITLE());
        holder.vMessage.setText(ci.getNOTE());
        holder.vID = String.valueOf(ci.getID());
    }

    @Override
    public int getItemCount(){return DatabaseList.get().size();}

    private void OpenDetails(String item){
        Intent intent;
        intent = new Intent(mContext,NoteActivity.class);
        Bundle args = new Bundle();
        args.putInt("MY_ID", Integer.parseInt(item));
        intent.putExtras(args);
        mContext.startActivity(intent);
    }
}
