package com.example.syed.popularmoviesstage1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by syed on 2017-07-13.
 */

public class CustomAdapter extends ArrayAdapter<Trailer> {
    public CustomAdapter(Activity context, ArrayList<Trailer> trailerList) {
        super(context,0, trailerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_list, parent, false);
        }

        final Trailer currentString = getItem(position);
        TextView textView= (TextView) listItemView.findViewById(R.id.trailer_name);

        textView.setText(currentString.getName());

        listItemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + currentString.getTrailerLinks()));
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }

}
