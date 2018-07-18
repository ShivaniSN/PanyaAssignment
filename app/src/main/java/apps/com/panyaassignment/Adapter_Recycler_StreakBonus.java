package apps.com.panyaassignment;

import android.content.Context;
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
    TextView textView;

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
        if (position == arrayListStreakBonus.size()-1) {
            holder.imageViewHearts.setImageResource(R.drawable.ic_streak_chest);
        }else {
            holder.imageViewHearts.setImageResource(R.drawable.ic_streak_heart_l);
        }
        holder.textView1.setText(arrayListStreakBonus.get(position));
    }

    @Override
    public int getItemCount(){

        return arrayListStreakBonus.size();
    }
}