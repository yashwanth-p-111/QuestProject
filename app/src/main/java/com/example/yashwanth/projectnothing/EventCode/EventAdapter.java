package com.example.yashwanth.projectnothing.EventCode;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yashwanth.projectnothing.R;

import java.util.ArrayList;

/**
 * Created by Yashwanth on 17-Jan-17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder> {
    LayoutInflater inflater;
    ArrayList<EventData> list=new ArrayList<>();
    private VolleySingleton volleySingleton;
    Context context;
static int number=0;
    public EventAdapter(Context context){
        volleySingleton = VolleySingleton.getInstance();
        inflater=LayoutInflater.from(context);
        this.context=context;
    }
    public void setEventList(ArrayList<EventData> list){
        this.list=list;
        notifyItemRangeChanged(0,list.size());
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.event_list_row, parent, false);
        CustomViewHolder holder=new CustomViewHolder(itemView,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
      EventData event=list.get(position);
      holder.setValues(event);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder
    {
       TextView eventName,eventNo,eventDescription;
        RelativeLayout layout;
        Button next;
        public CustomViewHolder(View itemView, final Context context) {
            super(itemView);
           eventName=(TextView) itemView.findViewById(R.id.wname1);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/exo.otf");
            Typeface textFont = Typeface.createFromAsset(context.getAssets(),  "fonts/robotolt.ttf");
            Typeface headFont = Typeface.createFromAsset(context.getAssets(),  "fonts/hmed.ttf");
            eventName.setTypeface(custom_font);
            eventDescription =(TextView) itemView.findViewById(R.id.eventDescription);
            eventDescription.setTypeface(textFont);
            layout=(RelativeLayout) itemView.findViewById(R.id.layout);
           eventNo=(TextView)itemView.findViewById(R.id.workno1);
            next=(Button) itemView.findViewById(R.id.nextBtn);
            next.setTypeface(headFont);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EventActivity)context).userItemClick(getAdapterPosition());
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EventActivity)context).userItemClick(getAdapterPosition());
                }
            });
        }
        public void setValues(EventData event)
        {
       eventName.setText(event.getName());
       eventDescription.setText(event.getOverview());
            eventNo.setText("EVENT #"+event.getNumber());
            number++;
        }
    }

}

