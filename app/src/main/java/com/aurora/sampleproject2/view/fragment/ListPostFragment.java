package com.aurora.sampleproject2.view.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.MyExoPlayerRecyclerView.MyExoPlayerRecyclerView;
import com.aurora.sampleproject2.adapter.PostAdpterExoPlayer;
import com.aurora.sampleproject2.model.db.entity.Post;
import com.aurora.sampleproject2.viewmodel.ListPostViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListPostFragment extends Fragment {

    MyExoPlayerRecyclerView recyclerView;
    //  ActivityMain2Binding binding;
    private ArrayList<Post> posts = new ArrayList<>();
    ListPostViewModel mViewModel;
    PostAdpterExoPlayer postsAdapter;
    boolean allowSetPost = true;
    private boolean firstTime = true;
    View view;
    FloatingActionButton fabInsert;


    public static ListPostFragment newInstance() {
        return new ListPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_post_fragment, container, false);
        initRecycler();

        fabInsert = view.findViewById(R.id.fabInsert);

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(ListPostFragmentDirections.actionListPostFragmentToInsertPostFragment());
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListPostViewModel.class);
        mViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> p) {
                posts = (ArrayList<Post>) p;
                if(allowSetPost) {
                    recyclerView.setPosts(posts);
                    //Set Adapter
                    postsAdapter.setPosts(posts);
                    //recyclerView.setAdapter(postsAdapter);
                    if (firstTime) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.playVideo(false);
                            }
                        });
                        firstTime = false;
                    }

                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           recyclerView.smoothScrollToPosition(0);
                       }
                   },1000);

                    allowSetPost=false;
                }
            }
        });
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(getContext())
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (recyclerView != null) {
            recyclerView.releasePlayer();
        }
        super.onDestroy();
    }


    private void initRecycler() {

        recyclerView = (MyExoPlayerRecyclerView) view.findViewById(R.id.exoPlayerRecyclerView);
        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        postsAdapter = new PostAdpterExoPlayer(initGlide());
        recyclerView.setAdapter(postsAdapter);
        postsAdapter.setPosts(posts);
        postsAdapter.setOnItemClickListener(new PostAdpterExoPlayer.OnItemClickListener() {
            @Override
            public void onItemClick(Post post, String type) {
                if (type.equals("like")) {
                    mViewModel.updatePost(post);
                } else {
                    sharePost(post);
                }

            }
        });


    }

    private void sharePost(Post post) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        if (!post.getMode().equals("text")) {
            Uri screenshotUri = Uri.parse(post.getAddressFile());
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        }
        sharingIntent.setType(post.getMode() + "/*");
        //sharingIntent.setType("*/*");
        // sharingIntent.putExtra(Intent.EXTRA_SUBJECT, post.getText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, post.getText());
        startActivity(Intent.createChooser(sharingIntent, "Share using"));


    }

    @Override
    public void onPause() {
        recyclerView.onPausePlayer();
        super.onPause();
    }

    @Override
    public void onResume() {
        allowSetPost=true;
        super.onResume();
    }
}