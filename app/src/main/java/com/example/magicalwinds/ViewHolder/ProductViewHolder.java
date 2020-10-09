package com.example.magicalwinds.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magicalwinds.Interface.ItemClickListner;
import com.example.magicalwinds.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_prodname,txt_prodprice,txt_prodsave,txt_mrp,txt_quantity;
    public ImageView prod_image;

    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        prod_image=(ImageView)itemView.findViewById(R.id.product_image);
        txt_prodname=(TextView)itemView.findViewById(R.id.product_name);
        txt_prodprice=(TextView)itemView.findViewById(R.id.tmart_price);
        txt_mrp=(TextView)itemView.findViewById(R.id.product_price);
        txt_prodsave=(TextView)itemView.findViewById(R.id.product_savings);
        txt_quantity=(TextView)itemView.findViewById(R.id.product_quantity);

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