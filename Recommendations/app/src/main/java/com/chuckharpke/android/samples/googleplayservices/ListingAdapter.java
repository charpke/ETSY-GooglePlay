package com.chuckharpke.android.samples.googleplayservices;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuckharpke.android.samples.googleplayservices.api.Etsy;
import com.chuckharpke.android.samples.googleplayservices.google.GoogleServicesHelper;
import com.chuckharpke.android.samples.googleplayservices.model.ActiveListings;
import com.chuckharpke.android.samples.googleplayservices.model.Listing;
import com.google.android.gms.plus.PlusOneButton;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by charpke on 5/31/15.
 */public class ListingAdapter  extends RecyclerView.Adapter<ListingAdapter.ListingHolder>
        implements Callback<ActiveListings>, GoogleServicesHelper.GoogleServicesListener {

    private MainActivity activity;
    private LayoutInflater inflater;
    private ActiveListings activeListings;


    private boolean isGooglePlayServicesAvailable;

    public ListingAdapter(MainActivity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.isGooglePlayServicesAvailable = false;
    }
    @Override
    public ListingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ListingHolder(inflater.inflate(R.layout.layout_listing, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ListingHolder listingHolder, int i) {
        final Listing listing = activeListings.results[i];
        listingHolder.titleView.setText(listing.title);
        listingHolder.priceView.setText(listing.price);
        listingHolder.shopNameView.setText(listing.Shop.shop_name);


        if(isGooglePlayServicesAvailable) {
            listingHolder.plusOneButton.setVisibility(View.VISIBLE);
            listingHolder.plusOneButton.initialize(listing.url, i);
            listingHolder.plusOneButton.setAnnotation(PlusOneButton.ANNOTATION_NONE);
        } else {
            listingHolder.plusOneButton.setVisibility(View.GONE);
        }

        Picasso.with(listingHolder.imageView.getContext())
                .load(listing.Images[0].url_570xN)
                .into(listingHolder.imageView);
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
        this.activity.showList();
    }

    @Override
    public void failure(RetrofitError error) {
        this.activity.showError();
    }
    public ActiveListings getActiveListings() {
        return activeListings;
    }

    @Override
    public void onConnected() {

        if(getItemCount() == 0) {
            Etsy.getActiveListings(this);
        }

        isGooglePlayServicesAvailable = true;
        notifyDataSetChanged();
    }

    @Override
    public void onDisconnected() {

        if(getItemCount() == 0) {
            Etsy.getActiveListings(this);
        }

        isGooglePlayServicesAvailable = false;
        notifyDataSetChanged();
    }


    public class ListingHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView titleView;
        public TextView shopNameView;
        public TextView priceView;
        public PlusOneButton plusOneButton;
        public ImageButton shareButton;

        public ListingHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.listing_image);
            titleView = (TextView) itemView.findViewById(R.id.listing_title);
            shopNameView = (TextView) itemView.findViewById(R.id.listing_shop_name);
            priceView = (TextView) itemView.findViewById(R.id.listing_price);
            plusOneButton = (PlusOneButton) itemView.findViewById(R.id.listing_plus_one_btn);
            shareButton = (ImageButton) itemView.findViewById(R.id.listing_share_btn);
        }
    }

}
