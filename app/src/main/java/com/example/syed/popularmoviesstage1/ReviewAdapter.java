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
 * Created by syed on 2017-07-16.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    public ReviewAdapter( Activity context, ArrayList<Review> reviewsList) {

        super(context,0 , reviewsList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_list, parent, false);
        }

        final Review currentString = getItem(position);
        TextView textView= (TextView) listItemView.findViewById(R.id.review_summary);
        TextView reviewer =(TextView)  listItemView.findViewById(R.id.reviewer);

        textView.setText(currentString.getContent());
        reviewer.setText(currentString.getAuthor());

        listItemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentString.getUrl()));
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }
}
