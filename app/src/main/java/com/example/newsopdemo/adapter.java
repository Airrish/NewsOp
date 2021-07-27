package com.example.newsopdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Map;

public class adapter extends RecyclerView.Adapter<ViewHold> {

    ArrayList<Map<String,String>> all;
    Context context;
    clickMe li;

    public adapter(Context context, ArrayList<Map<String,String>> all,clickMe li){
        this.all=all;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.kk,parent,false);
        return new ViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {

        holder.textView.setText(all.get(position).get("title"));
        holder.textView2.setText(all.get(position).get("des"));

        String photo = all.get(position).get("urlToImage");
        Glide.with(context.getApplicationContext()).load(photo).into(holder.imageView);

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li.onClickMe(all.get(position).get("url"));
            }
        });

    }

    @Override
    public int getItemCount() {
       int count=all.size();
        return count;
    }
}




class ViewHold extends RecyclerView.ViewHolder {

TextView textView;
TextView textView2;
ImageView imageView;
LinearLayout ll;
    public ViewHold(@NonNull View itemView) {
        super(itemView);
       textView=itemView.findViewById(R.id.textviiii);
       textView2 = itemView.findViewById(R.id.text);
       imageView = itemView.findViewById(R.id.ig);
       ll = itemView.findViewById(R.id.ll);
    }

}


interface clickMe {
    void onClickMe(String s);
}
