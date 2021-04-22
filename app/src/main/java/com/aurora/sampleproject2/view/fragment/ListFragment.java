package com.aurora.sampleproject2.view.fragment;

import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.aurora.sampleproject2.BuildConfig;
import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.adapter.AAH_CustomRecyclerView1;
import com.aurora.sampleproject2.adapter.PostAdpter;
import com.aurora.sampleproject2.databinding.ListFragmentBinding;
import com.aurora.sampleproject2.model.db.entity.Post;
import com.aurora.sampleproject2.viewmodel.ListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ListViewModel mViewModel;
    ListFragmentBinding binding;
    AAH_CustomRecyclerView1 recyclerView;
    PostAdpter postsAdapter;
    ArrayList<Post> posts = new ArrayList<>();
    int selectBookId;
   boolean allowSetPost=true;
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.list_fragment, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        initRecycler();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(ListFragmentDirections.actionListFragmentToInsertPostFragment());
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        mViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> p) {
                posts = (ArrayList<Post>) p;
                if(allowSetPost) {
                    postsAdapter.setPosts(posts);
                    allowSetPost=false;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.playAvailableVideos(0);
    }
    @Override
    public void onStop() {
        super.onStop();
        recyclerView.stopVideos();
    }
    private void initRecycler() {
        recyclerView = binding.recycler;
        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        postsAdapter = new PostAdpter();
        recyclerView.setAdapter(postsAdapter);
        postsAdapter.setPosts(posts);
        postsAdapter.setOnItemClickListener(new PostAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post,String type) {
                if(type.equals("like")){
                    mViewModel.updatePost(post);
                }else {
                   sharePost(post); 
                }

            }
        });





        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //todo before setAdapter
        recyclerView.setActivity(getActivity());

        //optional - to play only first visible video
        recyclerView.setPlayOnlyFirstVideo(true); // false by default

        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
        recyclerView.setCheckForMp4(false); //true by default

        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
        // recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default

        // recyclerView.setDownloadVideos(true); // false by default

        recyclerView.setVisiblePercent(80); // percentage of View that needs to be visible to start playing

        //extra - start downloading all videos in background before loading RecyclerView
//        List<String> urls = new ArrayList<>();
//        for (MyModel object : modelList) {
//            if (object.getVideo_url() != null && object.getVideo_url().contains("http"))
//                urls.add(object.getVideo_url());
//        }
        // recyclerView.preDownload(urls);

        recyclerView.setAdapter(postsAdapter);
        //call this functions when u want to start autoplay on loading async lists (eg firebase)
        recyclerView.smoothScrollBy(0,1);
        recyclerView.smoothScrollBy(0,-1);


    }

    private void sharePost(Post post) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(post.getAddressFile());
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
       // sharingIntent.putExtra(Intent.EXTRA_SUBJECT, post.getText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, post.getText());
        startActivity(Intent.createChooser(sharingIntent, "Share using"));


    }

}