package com.example.sara.marketer.merchantlist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sara.marketer.R;
import com.example.sara.marketer.merchantdetail.AddNewMerchantFragment;
import com.example.sara.marketer.model.Merchant;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.ChangeFragment;

import java.util.List;

/**
 * Created by Raad on 9/2/17.
 */

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.MyViewHolder> {

    List<Merchant> merchantList;
    FragmentActivity context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_merchant, parent, false);

        return new MyViewHolder(itemView);
    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.storeName.setText(merchantList.get(position).getStoreName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "onClick: " + merchantList.get(position));

//                if (isNetworkAvailable()) {
                    ChangeFragment.changeFragment(context, AddNewMerchantFragment.newInstance(merchantList.get(position)), R.id.container, true);


//                } else {
//                    Toast.makeText(context, "No Internt", Toast.LENGTH_SHORT).show();
//
//                }

            }
        });
        Log.i("", "onBindViewHolder: " + merchantList.get(position).getState());
        switch (merchantList.get(position).getState()) {
            case AppConstant.NOT_SENT:
                holder.storeState.setTextColor(context.getResources().getColor(R.color.colorYellow));
                holder.storeState.setText(R.string.no_sent);
                break;
            case AppConstant.TEMPACTIVE:
                Log.i("", "onBindViewHolder: TEMPACTIVE");
                holder.storeState.setTextColor(context.getResources().getColor(R.color.colorpalatinateblue));
                holder.storeState.setText(R.string.sent_no_active);
                break;
            case AppConstant.VERIFY:
                holder.storeState.setTextColor(context.getResources().getColor(R.color.colorufogreen));
                holder.storeState.setText(R.string.sent_active);
                break;
            case AppConstant.DOCERROR:
                holder.storeState.setTextColor(context.getResources().getColor(R.color.colorfireenginered));
                holder.storeState.setText(R.string.sent_doc_err);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView storeState;

        //        CardView cardView;
//        CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            storeName = (TextView) view.findViewById(R.id.text_view_store_name);
            storeState = (TextView) view.findViewById(R.id.text_view_store_state);
//            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public MerchantAdapter(FragmentActivity context, List<Merchant> merchantList) {
        this.merchantList = merchantList;
        this.context = context;
    }


}
