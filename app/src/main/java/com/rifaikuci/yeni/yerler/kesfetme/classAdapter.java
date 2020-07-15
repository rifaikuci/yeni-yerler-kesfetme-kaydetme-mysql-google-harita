package com.rifaikuci.yeni.yerler.kesfetme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;
import com.squareup.picasso.Picasso;

import java.util.List;



public class classAdapter extends BaseAdapter {

    Activity activity;
    List<dataTur> datas;
    LayoutInflater inflater;

    public classAdapter(Activity activity, List<dataTur> datas) {

        this.activity = activity;
        this.datas = datas;
        inflater = activity.getLayoutInflater();

    }

    public int  secilmis() {
       int s =0;

       for (int i =0; i<datas.size(); i++)
       {
           if(datas.get(i).isSelected() == true){
               s++;
           }
       }

        return s;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {

            view = inflater.inflate(R.layout.custom_view, viewGroup, false);

            holder = new ViewHolder();
            holder.basliklar = (TextView) view.findViewById(R.id.basliklar);
            holder.resimler = (ImageView) view.findViewById(R.id.resimler);
            holder.checkler = (ImageView) view.findViewById(R.id.checkler);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();}

            dataTur model = datas.get(i);
            holder.basliklar.setText(model.getTurAd());
            Picasso.get().load(datas.get(i).getTurResim()).into(holder.resimler);

            if (model.isSelected()) {
                holder.checkler.setBackgroundResource(R.drawable.checked);
            } else {
                holder.checkler.setBackgroundResource(R.drawable.check);
            }

            return view;
    }

    public void updateRecords(List<dataTur> datas){
        this.datas = datas;
        notifyDataSetChanged(); }

    class ViewHolder {
        TextView basliklar;
        ImageView resimler;
        ImageView checkler;
    }

}