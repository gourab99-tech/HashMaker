package com.HashMaker.hasher;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class TrendFragment extends Fragment {

    private ImageButton copyButton, shareButton;
    private Animation animationBubble;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        copyButton = getView().findViewById(R.id.buttonCopyContent1);
        shareButton = getView().findViewById(R.id.buttonShareContent1);
        textView = getView().findViewById(R.id.trending_hashtags);
        animationBubble = AnimationUtils.loadAnimation(getContext(), R.anim.bubble_animation);
        setButtonConfigs();
    }

    private void setButtonConfigs() {

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyButton.startAnimation(animationBubble);
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", textView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), R.string.hashtags_copied, Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareButton.startAnimation(animationBubble);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
    }
}