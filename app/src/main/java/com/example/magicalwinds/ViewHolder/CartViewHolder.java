package com.example.magicalwinds.ViewHolder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.magicalwinds.Interface.ItemClickListner;
import com.example.magicalwinds.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_prodname,txt_prodprice,txt_prodamt,txt_prodmrp;
    public ImageView prod_image;
    public ImageView editbtn,deletebtn;
   // public ElegantNumberButton numberButton;



    private ItemClickListner itemClickListner;

    @SuppressLint("WrongViewCast")
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        prod_image=(ImageView)itemView.findViewById(R.id.prod_image);
        txt_prodname=itemView.findViewById(R.id.cart_prod_name);
        txt_prodprice=itemView.findViewById(R.id.cart_prod_price);
        txt_prodmrp=itemView.findViewById(R.id.cart_prod_mrp);
        txt_prodamt=itemView.findViewById(R.id.amunt);
        //numberButton=itemView.findViewById(R.id.number_button);
       // editbtn=itemView.findViewById(R.id.edit_btn);
        deletebtn=itemView.findViewById(R.id.delete_btn);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
