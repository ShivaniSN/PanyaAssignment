package apps.com.panyaassignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Recycler_StreakBonus extends RecyclerView.Adapter<Adapter_Recycler_StreakBonus.ViewHolder>{

    List<Integer> arrayListStreakBonus;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    SharedPreferences sharedPreferences;

    public Adapter_Recycler_StreakBonus(Context context1,List<Integer> arrayListStreakBonus1){

        arrayListStreakBonus = arrayListStreakBonus1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewHearts;
        public TextView textView1,textView2,textView3,textView4,textView5;

        public ViewHolder(View v){

            super(v);

            imageViewHearts = (ImageView)v.findViewById(R.id.iv_hearts);
            textView1 = (TextView)v.findViewById(R.id.tvnumber1);
            textView2 = (TextView)v.findViewById(R.id.tvnumber2);
            textView3 = (TextView)v.findViewById(R.id.tvnumber3);
            textView4 = (TextView)v.findViewById(R.id.tvnumber4);
            textView5 = (TextView)v.findViewById(R.id.tvnumber5);
        }
    }

    @Override
    public Adapter_Recycler_StreakBonus.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view1 = LayoutInflater.from(context).inflate(R.layout.recycleritem_streakbonus,parent,false);
        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        int consecutiveRoundCount = Integer.parseInt(sharedPreferences.getString("consecutive_round_count",""));

        if (((position + 1) * 5) <= consecutiveRoundCount){
            holder.textView1.setBackgroundResource(R.drawable.rounded_left_pink);
            holder.textView2.setBackgroundResource(R.drawable.bg_streak_info_pink);
            holder.textView3.setBackgroundResource(R.drawable.bg_streak_info_pink);
            holder.textView4.setBackgroundResource(R.drawable.bg_streak_info_pink);
            holder.textView5.setBackgroundResource(R.drawable.rounded_right_pink);

            holder.imageViewHearts.setImageResource(R.drawable.ic_streak_received);
            holder.imageViewHearts.setBackgroundResource(R.drawable.ic_streak_heart_l_50);
        }
        else if (position == arrayListStreakBonus.size()-1) {
            holder.textView1.setBackgroundResource(R.drawable.rounded_left_blue);
            holder.textView2.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView3.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView4.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView5.setBackgroundResource(R.drawable.rounded_right_blue);

            holder.imageViewHearts.setImageResource(R.drawable.ic_streak_chest);
            holder.imageViewHearts.setBackgroundResource(android.R.color.transparent);
        }else {
            holder.textView1.setBackgroundResource(R.drawable.rounded_left_blue);
            holder.textView2.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView3.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView4.setBackgroundResource(R.color.colorPrimaryTransparent);
            holder.textView5.setBackgroundResource(R.drawable.rounded_right_blue);

            holder.imageViewHearts.setImageResource(R.drawable.ic_streak_heart_l);
            holder.imageViewHearts.setBackgroundResource(android.R.color.transparent);
        }
        holder.textView1.setText(String.valueOf(arrayListStreakBonus.get(position)));
    }

    @Override
    public int getItemCount(){
        return arrayListStreakBonus.size();
    }
}