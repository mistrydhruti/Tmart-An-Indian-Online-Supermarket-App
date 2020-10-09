package com.example.magicalwinds.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magicalwinds.Interface.ItemClickListner;
import com.example.magicalwinds.R;

import org.w3c.dom.Text;

public class SeeAllViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_prodname,txt_prodprice,txt_prodsave,txt_mrp,txt_quantity,txt_addtocart;
    public ImageView seeall_image;

    public ItemClickListner listner;
    public SeeAllViewHolder(@NonNull View itemView) {
        super(itemView);


        seeall_image=(ImageView)itemView.findViewById(R.id.see_image);
        txt_prodname=(TextView)itemView.findViewById(R.id.prod_name);
        txt_prodprice=(TextView)itemView.findViewById(R.id.tmart_price);
        txt_mrp=(TextView)itemView.findViewById(R.id.mrp);
        txt_prodsave=(TextView)itemView.findViewById(R.id.save);
        txt_quantity=(TextView)itemView.findViewById(R.id.prod_quantity);
        txt_addtocart=(TextView)itemView.findViewById(R.id.addtocart);

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
