package com.bwellthy.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwellthy.app.R;
import com.bwellthy.app.models.Words;
import com.bwellthy.app.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {

    private List<Words> wordsList;
    private Context context;
    private final String imageUrl = "http://appsculture.com/vocab/images/";
    private int pixel;

    public VocabularyAdapter(List<Words> wordsList, Context context) {
        this.wordsList = new ArrayList<>(wordsList);
        this.context = context;
        this.pixel = Utility.convertDpToPixel(context.getResources().getDimension(R.dimen.dp80), context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocabulary_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Words word = wordsList.get(position);
        if (word != null) {
            holder.word.setText(word.getWord());
            holder.meaning.setText(word.getMeaning());
            Glide.with(context).load(imageUrl + word.getId() + ".png").placeholder(R.mipmap.ic_launcher).override(pixel, pixel).centerCrop().into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView word, meaning;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.word);
            meaning = (TextView) view.findViewById(R.id.meaning);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

}
