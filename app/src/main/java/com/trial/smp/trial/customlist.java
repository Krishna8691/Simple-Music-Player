package com.trial.smp.trial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by krish on 2/16/2017.
 */

public class customlist extends ArrayAdapter {
    public customlist(Context context, String[] items) {
        super(context,R.layout.textviewlayout,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customview = myinflater.inflate(R.layout.textviewlayout,parent,false);
        String singleitems = (String) getItem(position);
        TextView mytv = (TextView) customview.findViewById(R.id.customtextview);
        mytv.setText(singleitems);
        return customview;
    }
}
