package com.patrick.bitvilltenologies.gounihub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView ;
    public ViewHolder(View itemView) {
        super(itemView);

        mView =  itemView;

    }
    public void  setDetails(Context ctx,String title ,String description, String image){


        TextView mTiltleView = mView.findViewById(R.id.titlez);
        ImageView mimageView = mView.findViewById(R.id.rimageview);
        TextView mdiscription = mView.findViewById(R.id.rdiscriptionv);


        mTiltleView.setText(title);
        mdiscription.setText(description);
        Picasso.get().load(image).into(mimageView);

    }

}
