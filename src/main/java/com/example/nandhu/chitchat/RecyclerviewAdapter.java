package com.example.nandhu.chitchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by NANDHU on 23-09-2017.
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {


    List<Userslist> matcheslists;
    private Context context;
    private LayoutInflater inflater;
    public RelativeLayout linear;
    public String imageopen;

    public RecyclerviewAdapter(Context context, List<Userslist> idavaka) {
        this.context = context;
        this.matcheslists = idavaka;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.userslist, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Userslist list=matcheslists.get(position);
        holder.name.setText(""+list.getname());
        holder.mobile.setText(""+list.getmob());
        String status=list.getStatus();
        if (status.equalsIgnoreCase("online")){
            Glide.with(context)
                    .load("https://www.mylol.com/images/new_member_online.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.statusimg);
        }else if (status.equalsIgnoreCase("offline")){
            Glide.with(context)
                    .load("http://www.the-afc.com/divahtml/resources/dvt/img/dvt_circle_red.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.statusimg);
        }



    }
    @Override
    public int getItemCount() {
        return matcheslists.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name,mobile;
        private ImageView statusimg;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text1);
            mobile = (TextView) itemView.findViewById(R.id.text2);
            statusimg= (ImageView) itemView.findViewById(R.id.imgstatus);
            //itemView.setOnClickListener(this);

        }

    }
}
