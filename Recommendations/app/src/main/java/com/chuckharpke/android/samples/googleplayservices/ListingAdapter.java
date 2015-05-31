package com.chuckharpke.android.samples.googleplayservices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuckharpke.android.samples.googleplayservices.model.ActiveListings;
import com.chuckharpke.android.samples.googleplayservices.model.Listing;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by charpke on 5/31/15.
 */public class ListingAdapter  extends RecyclerView.Adapter<ListingAdapter.ListingHolder>
implements Callback<ActiveListings> {

    private LayoutInflater inflater;
    private ActiveListings activeListings;

    public ListingAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public ListingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ListingHolder(inflater.inflate(R.layout.layout_listing, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ListingHolder holder, int i) {
        final Listing listing = activeListings.results[i];
        holder.titleView.setText(listing.title);
        holder.priceView.setText(listing.price);
        holder.shopNameView.setText(listing.Shop.shop_name);

        Picasso.with(holder.imageView.getContext())
                .load(listing.Images[0].url_570xN)
                .into(holder.imageView);
    }

    @Override


    public int getItemCount() {
        if (activeListings == null)
        return 0;

        if (activeListings.results == null)
            return  0;

        return activeListings.results.length;

    }

    @Override
    public void success(ActiveListings activeListings, Response response) {
        this.activeListings = activeListings;
        notifyDataSetChanged();
    }

    @Override
    public void failure(RetrofitError error) {

    }


    public class ListingHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView titleView;
        public TextView shopNameView;
        public TextView priceView;

        public ListingHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.listing_image);
            titleView = (TextView) itemView.findViewById(R.id.listing_title);
            shopNameView = (TextView) itemView.findViewById(R.id.listing_shop_name);
            priceView = (TextView) itemView.findViewById(R.id.listing_price);
        }
    }

}
