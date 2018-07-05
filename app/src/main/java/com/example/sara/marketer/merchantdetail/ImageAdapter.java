package com.example.sara.marketer.merchantdetail;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.sara.marketer.R;
import com.example.sara.marketer.model.Image;
import com.example.sara.marketer.model.User;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.ChangeFragment;
import com.example.sara.marketer.widget.ShowDialogImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raad on 12/27/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<Image> list;
    FragmentActivity context;

    private imageAdapterInterface imageAdapterInterface;

    public interface imageAdapterInterface {
        void onAddClicked(int position, Image image);

    }


    public void setImageAdapterInterface(imageAdapterInterface imageAdapterInterface) {
        this.imageAdapterInterface = imageAdapterInterface;

    }


    public ImageAdapter(FragmentActivity context, List<Image> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEADER) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_sign, parent, false);
            viewHolder = new VHHeader(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_image, parent, false);
            viewHolder = new VHItem(itemView);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHItem) {
            final Image dataItem = getItem(position);
            Log.d("", "onBindViewHolder: " + position);
            if (list.get(position).getSent() != null) {
                ((VHItem) holder).layoutResult.setVisibility(View.VISIBLE);
                if (!list.get(position).getSent()) {
                    ((VHItem) holder).imageViewRefresh.setVisibility(View.VISIBLE);
                    ((VHItem) holder).imageViewSuccess.setVisibility(View.GONE);
                } else {
                    ((VHItem) holder).imageViewRefresh.setVisibility(View.GONE);
                    ((VHItem) holder).imageViewSuccess.setVisibility(View.VISIBLE);
                }
//
            }
            ((VHItem) holder).imageViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    uploadFiles(list.get(position).getImagePath(), ((VHItem) holder),position);
                }
            });
            ((VHItem) holder).buttonRemoveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);

                    notifyDataSetChanged();
                }
            });

        } else if (holder instanceof VHHeader) {
            if (list.get(position).getSent() != null) {
                if (!list.get(position).getSent()) {
                    ((VHItem) holder).itemView.setBackgroundColor(context.getResources().getColor(R.color.colorfireenginered));
                }
//
            }
            ((VHHeader) holder).buttonSignature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyboard();

//                    final SignatureFragment fragment = new SignatureFragment();
//                    fragment.setListener(new SignatureListener() {
//                        @Override
//                        public void onSaveClicked(String path) {
//                            hideKeyboard();
////                            ((VHHeader) holder).imageId = position;
////                            ((VHHeader) holder).imageTitle = AppConstant.SIGNATURE;
////                            ((VHHeader) holder).imagePath = path;
////                            ((VHHeader) holder).isSent = false;
////                            list.add(new Image(AppConstant.SIGNATURE,path,"",false));
//                            list.get(position).setImageId(position);
//                            list.get(position).setImageTitle(AppConstant.SIGNATURE);
//                            list.get(position).setImagePath(path);
////                            list.get(position).setSent( false);
//                            notifyDataSetChanged();
//
//                        }
//                    });
//                    ChangeFragment.changeFragment(context, fragment, R.id.container, true);
                }
            });
            //cast holder to VHHeader and set data for header.
        }
    }

    private void hideKeyboard() {
        if (context.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Image getItem(int position) {
        return list.get(position);
    }

    class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button buttonAddImage;
        Button buttonRemoveImage;
        RelativeLayout layoutResult;
        ImageView imageViewRefresh;
        ImageView imageViewSuccess;
        int imageId;
        String imageTitle;
        String imagePath;
        String linkPath;
        Boolean isSent;


        public VHItem(View itemView) {
            super(itemView);
            buttonAddImage = (Button) itemView.findViewById(R.id.button_add);
            buttonRemoveImage = (Button) itemView.findViewById(R.id.button_remove);
            layoutResult = (RelativeLayout) itemView.findViewById(R.id.layout_result);
            imageViewSuccess = (ImageView) itemView.findViewById(R.id.image_success);
            imageViewRefresh = (ImageView) itemView.findViewById(R.id.image_refresh);
            buttonAddImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_add:
                    if (!TextUtils.isEmpty(list.get(getAdapterPosition()).getImagePath())) {
                        new ShowDialogImage(context, list.get(getAdapterPosition()).getImagePath());
                    } else {
                        if (imageAdapterInterface == null) {
                            return;
                        }
                        imageAdapterInterface.onAddClicked(getAdapterPosition(), list.get(getAdapterPosition()));
                    }
                    break;


            }


        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        Button buttonSignature;
        int imageId;
        String imageTitle;
        String imagePath;
        String linkPath;
        Boolean isSent;

        public VHHeader(View itemView) {

            super(itemView);
            buttonSignature = (Button) itemView.findViewById(R.id.button_signature);


        }
    }

    private void uploadFiles(String path, final RecyclerView.ViewHolder holder, final int position) {


//        Web.getInstance().uploadFile(User.getInstance().getJwtToken(), compressFile(path), path).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    list.get(position).setSent(true);
//                    list.get(position).setLinkPath(response.body());
//                    ((VHItem) holder).imageViewSuccess.setVisibility(View.VISIBLE);
//                    ((VHItem) holder).imageViewRefresh.setVisibility(View.GONE);
//
//                } else {
//                    ((VHItem) holder).imageViewSuccess.setVisibility(View.GONE);
//                    ((VHItem) holder).imageViewRefresh.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
////                progressbar.setVisibility(View.GONE);
//                Toast.makeText(context, R.string.network_error_file, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
    private byte[] compressFile(String imageUrl) {
//        int compressionRatio = 2; //1 == originalImage, 2 = 50% compression, 4=25% compress
        ByteArrayOutputStream bos;
        File file = new File(imageUrl);
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);
        } catch (Throwable t) {
            bos = null;
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }

        return bos.toByteArray();
    }


    public void updateAdapter(int position, Image holder) {

        notifyDataSetChanged();
    }
}
