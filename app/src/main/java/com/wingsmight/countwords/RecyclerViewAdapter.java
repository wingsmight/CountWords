package com.wingsmight.countwords;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.TreeMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    private TreeMap<String, Integer> words;
    private String[] wordsArray;
    private Integer[] countsArray;
    private Context context;

    public RecyclerViewAdapter(Context context, TreeMap<String, Integer> words)
    {
        this.words = words;
        this.context = context;

        wordsArray = words.keySet().toArray(new String[words.size()]);
        countsArray = words.values().toArray(new Integer[words.size()]);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.word.setText(wordsArray[position]);
        holder.count.setText(countsArray[position].toString());

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));
//
//                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(mContext, GalleryActivity.class);
//                intent.putExtra("image_url", mImages.get(position));
//                intent.putExtra("image_name", mImageNames.get(position));
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return words.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView word;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            count = itemView.findViewById(R.id.count);
        }
    }
}





