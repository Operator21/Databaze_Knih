package com.standarts.databazeknih;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class KnihaAdapter extends ArrayAdapter<Kniha> {
    private Context context;
    private ArrayList<Kniha> knihy = new ArrayList<>();
    public KnihaAdapter(Context context, ArrayList<Kniha> knihy){
        super(context, 0, knihy);
        this.knihy = knihy;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        }
        Kniha aktualni = knihy.get(position);
        TextView nazev =  view.findViewById(R.id.knNazev);
        TextView pocetstran =  view.findViewById(R.id.knPocetStran);
        TextView autor =  view.findViewById(R.id.knAutor);

        nazev.setText(aktualni.nazev);
        pocetstran.setText(aktualni.ps + "");
        autor.setText(aktualni.autor);

        return view;
    }
    public Kniha getItem(int position){
        return knihy.get(position);
    }
}
