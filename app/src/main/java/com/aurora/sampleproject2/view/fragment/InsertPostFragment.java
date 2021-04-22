package com.aurora.sampleproject2.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.databinding.InsertPostFragmentBinding;
import com.aurora.sampleproject2.model.db.entity.Post;
import com.aurora.sampleproject2.viewmodel.InsertPostViewModel;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;


import java.io.File;
import java.util.ArrayList;

public class InsertPostFragment extends Fragment {

    private InsertPostViewModel mViewModel;
    InsertPostFragmentBinding binding;
    String selectedPath = "";

    public static InsertPostFragment newInstance() {
        return new InsertPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.insert_post_fragment, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.insert_post_fragment, container, false);

        binding.btnInsertImageOrVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePicker();
            }
        });
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPost(view);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsertPostViewModel.class);
        // TODO: Use the ViewModel


    }

    public void filePicker() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //dialogNewProduct.dismiss();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        } else {
            Intent intent = new Intent(getActivity(), PickerActivity.class);
            intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
            long maxSize = 188743680L;//long long long long类型
            intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //最大选择大小，默认180M（非必填参数）
            intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //最大选择数量，默认40（非必填参数）
            // ArrayList<Media> defaultSelect = select;//可以设置默认选中的照片，比如把select刚刚选择的list设置成默认的。
            //  intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,defaultSelect); //可以设置默认选中的照片(非必填参数)
            startActivityForResult(intent, 200);


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {
            ArrayList<Media> select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);//选择完后返回的list

            ImageView videoview = binding.imgPoster;
            try {
                selectedPath = select.get(0).path;


                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(selectedPath, MediaStore.Images.Thumbnails.MINI_KIND);
                Matrix matrix = new Matrix();
                Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,
                        thumb.getWidth(), thumb.getHeight(), matrix, true);
                videoview.setImageBitmap(bmThumbnail);
            } catch (Exception e) {
                File imageFile = new File(selectedPath);
                if (imageFile.exists()) {
                    videoview.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 11: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    filePicker();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "اپلیکیشن نیاز دارد به حافظه دسترسی داشته باشد", Toast.LENGTH_LONG).show();
                    // ActivityCompat.requestPermissions(listProduct.this, new String[]{Manifest.permission.CAMERA}, 11);

                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void insertPost(View  view) {
        String text = binding.edtDescription.getText().toString();

        if (text.equals("")) {
            binding.edtDescription.setError("توضیحات نباید خالی باشد");
        }
        else {
            Post post = new Post();
            post.setText(text);
            post.setLike(false);
            if (selectedPath.equals("")) {
                post.setMode("text");
            } else {
                String formatFile = selectedPath.substring(selectedPath.indexOf(".") + 1, selectedPath.length());
                if (formatFile.equals("png") || formatFile.equals("jpg") || formatFile.equals("jpeg")) {
                    post.setMode("image");
                } else {
                    post.setMode("video");
                }
            }
            post.setAddressFile(selectedPath);

            mViewModel.insertPost(post);
            // This callback will only be called when MyFragment is at least Started.
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    // Handle the back button event
                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

            requireActivity().onBackPressed();
            Navigation.findNavController(view).popBackStack();

        }
    }
}