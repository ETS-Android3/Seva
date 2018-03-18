package com.example.helloworld.seva;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.seva.ListModel;
import com.example.helloworld.seva.MainActivity;
import com.example.helloworld.seva.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Shaik Nazeer on 20-02-2018.
 */

public class CustomAdapterMyAdds extends RecyclerView.Adapter<CustomAdapterMyAdds.ViewHolder>{
    private ArrayList data;
    Context context;

    public CustomAdapterMyAdds(ArrayList d,Context ct) {
        data = d;
        context=ct;
    }
    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data,Context ct) {
        context=ct;  //remove if it creates any null pointer problem - nazeer
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView t_name;
        public TextView t_phoneNumber;
        public TextView t_title;
        public TextView t_description;
        public TextView t_location;
        public TextView t_expiryDate;
        public ImageView t_call_image;
        public ImageView t_location_image;
        public ImageView t_post_image;
        public TextView t_item_post_date;
        public ImageView t_like_image;
        public ImageView t_edit;
        public ImageView t_delete;


        public ViewHolder(View v){
            super(v);
            this.t_name = v.findViewById(R.id.item_name);
            this.t_title = v.findViewById(R.id.item_title);
            this.t_phoneNumber = v.findViewById(R.id.item_phone_number);
            this.t_description = v.findViewById(R.id.item_description);
            this.t_location = v.findViewById(R.id.item_location);
            this.t_expiryDate = v.findViewById(R.id.item_expiry_date);
            this.t_call_image = v.findViewById(R.id.call);
            this.t_location_image = v.findViewById(R.id.location);
            this.t_post_image = v.findViewById(R.id.postImage);
            this.t_item_post_date = v.findViewById(R.id.item_post_date);
            this.t_like_image = v.findViewById(R.id.like);
            this.t_edit = v.findViewById(R.id.edit);
            this.t_delete = v.findViewById(R.id.delete);
        }
    }



    @Override
    public CustomAdapterMyAdds.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //Context doubt
        final View v =  LayoutInflater.from(context).inflate(R.layout.list_item_myadds, parent, false);
        return new ViewHolder(v);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListModel tempValues = (ListModel) data.get(position);
        if(tempValues != null) {
            holder.t_name.setText(tempValues.getItemName());
            holder.t_title.setText(tempValues.getItemTitle());
            holder.t_phoneNumber.setText(tempValues.getItemPhoneNumber());
            holder.t_description.setText(tempValues.getItemDescription());
            holder.t_location.setText(tempValues.getItemLocation());
            holder.t_expiryDate.setText(tempValues.getItemExpiryDate());
            holder.t_item_post_date.setText(tempValues.getItemPostDate());

            Picasso.with(context).load(tempValues.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.t_post_image, new Callback() {
                @Override
                public void onSuccess() {
                    Picasso.with(context).load(tempValues.getImage()).into(holder.t_post_image);
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(tempValues.getImage()).into(holder.t_post_image);
                }
            });

            //holder.t_post_image.setImageURI(tempValues.getImageUri());
        }


        holder.t_call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tempValues.getItemPhoneNumber()));
                context.startActivity(intent);
            }
        });

        holder.t_location_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String map = "http://maps.google.co.in/maps?daddr=" + tempValues.getItemLocation();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        });

        holder.t_like_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.t_like_image.getTag().equals("0")){
                    //state unlike --> like
                    holder.t_like_image.setImageResource(R.mipmap.ic_unlike);
                    holder.t_like_image.setTag("1");
                }
                else{
                    holder.t_like_image.setImageResource(R.mipmap.ic_like_g);
                    holder.t_like_image.setTag("0");
                }
            }
        });

        holder.t_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit button pressed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,EditActivity.class);
                context.startActivity(i);
            }
        });

        holder.t_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete button pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
