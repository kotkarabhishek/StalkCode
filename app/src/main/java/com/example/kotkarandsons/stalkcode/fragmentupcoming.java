package com.example.kotkarandsons.stalkcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class fragmentupcoming extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmentupcoming,null);

    }

    private WebView webView;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        webView=(WebView)view.findViewById(R.id.upcomingcontests);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        String url="https://clist.by/";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        if(webView.isInLayout()){
            progressDialog.dismiss();
        }
    }
}
