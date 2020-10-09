package com.example.magicalwinds.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magicalwinds.Interface.ItemClickListner;
import com.example.magicalwinds.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_orderdate,txt_orderId;
    public ImageView txt_next;
    public Button order_cancel;
    public LinearLayout cancelorder;

    public ItemClickListner listner;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);


        txt_orderId=(TextView)itemView.findViewById(R.id.orderId);
        txt_orderdate=(TextView)itemView.findViewById(R.id.orderdate);
        txt_next=(ImageView)itemView.findViewById(R.id.next);
        order_cancel=(Button)itemView.findViewById(R.id.ordercancel);
        cancelorder=(LinearLayout) itemView.findViewById(R.id.cancelorder);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);

    }
}
