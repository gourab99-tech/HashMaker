package com.HashMaker.hasher;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FragmentContent extends Fragment {
    private TextView textViewHashtagContent, textViewSubCategory;
    private ImageView imageViewCategory;
    private ImageButton buttonHome, buttonCopyContent, buttonRegenerate, buttonShareContent;
    private String mHashtagsContent, mHashtagsCategory;
    private Bundle bundle;
    private int mThumbnailHashtag;
    private AdView mAdview;
    private Animation animationBubble;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_content, container, false);
        textViewHashtagContent = myView.findViewById(R.id.textViewContentHashtags);
        textViewSubCategory = myView.findViewById(R.id.textViewSubCategoryHashtagContent);
        buttonHome = myView.findViewById(R.id.buttonHomeContent);
        buttonCopyContent = myView.findViewById(R.id.buttonCopyContent);
        buttonShareContent = myView.findViewById(R.id.buttonShareContent);
        buttonRegenerate = myView.findViewById(R.id.buttonRegenerateContent);
        imageViewCategory = myView.findViewById(R.id.imageViewHashtagContent);
        //Ads
        mAdview = myView.findViewById(R.id.admobBannerContent);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        Hashtags.FINAL_YOUTUBE_HASHTAGS = "";
        //Getting Animation to show
        animationBubble = AnimationUtils.loadAnimation(getContext(), R.anim.bubble_animation);
        // Getting Data From Arguments To Show
        bundle = getArguments();
        mHashtagsContent = bundle.getString(Hashtags.HASHTAGS_TO_SHOW);
        mHashtagsCategory = bundle.getString(Hashtags.CONTENT_CATEGORY);
        mThumbnailHashtag = bundle.getInt(Hashtags.THUMBNAIL_CONTENT);
        // Setup Buttons

        SetAllButtonsConfigs();

        // Setting Data To Right Place
        textViewSubCategory.setText(mHashtagsCategory);
        // textViewHashtagContent.setText(mHashtagsContent);
        imageViewCategory.setImageResource(mThumbnailHashtag);


        return myView;
    }

    private void SetAllButtonsConfigs() {

        //Set On click Listner

        // Home Button Click event
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHome.startAnimation(animationBubble);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container_framelayout, new FragmentAllHashtags()).commit();
            }
        });
        // Copy Button Click Event
        buttonCopyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCopyContent.startAnimation(animationBubble);
                CopyText(mHashtagsContent);
                Toast.makeText(getContext(), R.string.hashtags_copied, Toast.LENGTH_SHORT).show();

            }
        });


        // ((GradientDrawable)background).setColor(ContextCompat.getColor(getContext(),R.color.colorYoutubeContentButtons));
        // ((GradientDrawable)background2).setColor(ContextCompat.getColor(getContext(),R.color.colorYoutubeContentButtons));
        // ((GradientDrawable)background3).setColor(ContextCompat.getColor(getContext(),R.color.colorYoutubeContentButtons));
        // Set up the List
        List<String> hashtagsSeparatedList;
        Hashtags.FINAL_YOUTUBE_HASHTAGS = "";
        mHashtagsContent = mHashtagsContent.trim();
        hashtagsSeparatedList = new ArrayList<>(Arrays.asList(mHashtagsContent.split("#")));
        //removing Empty hashtags
        for (int x = 0; x < hashtagsSeparatedList.size(); x++) {
            if (hashtagsSeparatedList.get(x).length() < 3) {
                hashtagsSeparatedList.remove(x);
            }
        }
        Collections.shuffle(hashtagsSeparatedList);
        Hashtags.FINAL_YOUTUBE_HASHTAGS = "#" + hashtagsSeparatedList.get(0) + " #" + hashtagsSeparatedList.get(1) + " #" + hashtagsSeparatedList.get(2) + " #" + hashtagsSeparatedList.get(3) + " #" + hashtagsSeparatedList.get(4) + " #" + hashtagsSeparatedList.get(5) + " #" + hashtagsSeparatedList.get(6) + " #" + hashtagsSeparatedList.get(7) + " #" + hashtagsSeparatedList.get(8) + " #" + hashtagsSeparatedList.get(9) + " #" + hashtagsSeparatedList.get(10) + " #" + hashtagsSeparatedList.get(11) + " #" + hashtagsSeparatedList.get(12) + " #" + hashtagsSeparatedList.get(13) + " #" + hashtagsSeparatedList.get(14) + " #" + hashtagsSeparatedList.get(15) + " #" + hashtagsSeparatedList.get(16) + " #" + hashtagsSeparatedList.get(17) + " #" + hashtagsSeparatedList.get(18) + " #" + hashtagsSeparatedList.get(19) + " #" + hashtagsSeparatedList.get(20) + " #" + hashtagsSeparatedList.get(21) + " #" + hashtagsSeparatedList.get(22) + " #" + hashtagsSeparatedList.get(23) + " #" + hashtagsSeparatedList.get(24) + " ";
        textViewHashtagContent.setText(Hashtags.FINAL_YOUTUBE_HASHTAGS);


        // Button ReGenerate

        final List<String> finalHashtagsSeparatedList = hashtagsSeparatedList;
        buttonRegenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegenerate.startAnimation(animationBubble);
                Hashtags.FINAL_YOUTUBE_HASHTAGS = "";
                Collections.shuffle(finalHashtagsSeparatedList);
                Hashtags.FINAL_YOUTUBE_HASHTAGS = "#" + finalHashtagsSeparatedList.get(0) + " #" + finalHashtagsSeparatedList.get(1) + " #" + finalHashtagsSeparatedList.get(2) + finalHashtagsSeparatedList.get(3) + finalHashtagsSeparatedList.get(4) + finalHashtagsSeparatedList.get(5) + finalHashtagsSeparatedList.get(6) + finalHashtagsSeparatedList.get(7) + finalHashtagsSeparatedList.get(8) + finalHashtagsSeparatedList.get(9) + finalHashtagsSeparatedList.get(10)  + finalHashtagsSeparatedList.get(11)  + finalHashtagsSeparatedList.get(12)  + finalHashtagsSeparatedList.get(13)  + finalHashtagsSeparatedList.get(14)  + finalHashtagsSeparatedList.get(15)  + finalHashtagsSeparatedList.get(16)  + finalHashtagsSeparatedList.get(17)  + finalHashtagsSeparatedList.get(18)  + finalHashtagsSeparatedList.get(19)  + finalHashtagsSeparatedList.get(20)  + finalHashtagsSeparatedList.get(21)  + finalHashtagsSeparatedList.get(22)  + finalHashtagsSeparatedList.get(23)  + finalHashtagsSeparatedList.get(24) + " ";
                textViewHashtagContent.setText(Hashtags.FINAL_YOUTUBE_HASHTAGS);
                //Toast.makeText(getContext(),"Generate Button Clicked !",Toast.LENGTH_SHORT).show();
            }
        });

        // Button Copy
        buttonCopyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCopyContent.startAnimation(animationBubble);
                CopyText(Hashtags.FINAL_YOUTUBE_HASHTAGS);
                Toast.makeText(getContext(), R.string.hashtags_copied, Toast.LENGTH_SHORT).show();
            }
        });

        // Share Content Button

        buttonShareContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShareContent.startAnimation(animationBubble);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if (Hashtags.FINAL_YOUTUBE_HASHTAGS.length() > 1) {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, Hashtags.FINAL_YOUTUBE_HASHTAGS);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

            }
        });


    }


    public void CopyText(String textToCopy) {

        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", textToCopy);
        clipboard.setPrimaryClip(clip);
    }
}
