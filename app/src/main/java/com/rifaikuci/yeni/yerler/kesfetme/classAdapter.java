package com.rifaikuci.yeni.yerler.kesfetme;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class classAdapter extends ArrayAdapter<dataInfo> {

    private  dataInfo[] dataList;
    private  int resource;

    public classAdapter(@NonNull Context context, int resource, @NonNull dataInfo[] dataList) {
        super(context, resource, dataList);
        this.dataList = dataList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;

        LayoutInflater layoutInflater = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = layoutInflater.inflate(resource,null);

        ImageView resimler  = (ImageView) row.findViewById(R.id.resimler);
        TextView  basliklar = (TextView) row.findViewById(R.id.basliklar);
        ImageView isCheck = (ImageView)  row.findViewById(R.id.checkler);


        dataInfo veriler = dataList[position];

        resimler.setImageResource(veriler.getImage());
        basliklar.setText(veriler.getName());

        if(veriler.isSelected ==false){
            isCheck.setImageResource(R.drawable.check);
        }else {
            isCheck.setImageResource(R.drawable.checked);
        }

        return row;

    }
}
