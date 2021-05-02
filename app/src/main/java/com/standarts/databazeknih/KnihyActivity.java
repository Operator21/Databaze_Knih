package com.standarts.databazeknih;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class KnihyActivity extends Activity {

	private ListView seznam;	
	private ToggleButton razeni;
	private static final String LOG_TAG = "VAVOP KnihyActivity";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_knihy);

		this.seznam = findViewById(R.id.seznamKnih);
		this.razeni = findViewById(R.id.toggleButton);

		vypisKnihy(razeni);
	}

	public void vypisKnihy(View v) {
		vypisKnihy();
	}

	private void vypisKnihy(){
		ArrayList<Kniha> knihy = new ArrayList<>();

		// TODO - 1. faze - napln ListView
		MojeKnihyDB db = new MojeKnihyDB(this);
		db.open();
		Cursor data;
		if(((ToggleButton)findViewById(R.id.toggleButton)).isChecked()){
			data = db.vsechnyKnihy(MojeKnihyDB.COL_POCET_STRANEK);
		} else {
			data = db.vsechnyKnihy(MojeKnihyDB.COL_NAZEV);
		}
		if(data.moveToFirst()){
			do {
				knihy.add(new Kniha(data.getLong(data.getColumnIndex(MojeKnihyDB.COL_ID)), data.getString(data.getColumnIndex(MojeKnihyDB.COL_NAZEV)), data.getInt(data.getColumnIndex(MojeKnihyDB.COL_POCET_STRANEK)), data.getString(data.getColumnIndex(MojeKnihyDB.COL_AUTOR))));
			} while(data.moveToNext());
		}
		ListView lv = findViewById(R.id.seznamKnih);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Kniha kniha = (Kniha)parent.getAdapter().getItem(position);
				smazKnihu((int)kniha.id);
			}
		});
		KnihaAdapter adapter = new KnihaAdapter(this, knihy);
		lv.setAdapter(adapter);
	}

	public void smazKnihu(int id) {
		// TODO - 2. faze
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Potvrdit");
		builder.setMessage("Smazat tuto knihu?");

		Context context = this;
		builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MojeKnihyDB db = new MojeKnihyDB(context);
				if(db.smazKnihu(id)){
					Toast.makeText(context, "Smazáno", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					vypisKnihy();
				} else {
					Toast.makeText(context, "Něco se pokazilo", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}
		});
		builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void pridejZaznam(View view) {
		// TODO - 1. faze - volej aktivitu PridejActivity s requestCode 1984
		Intent i = new Intent(this, PridejActivity.class);
		startActivityForResult(i, 1984);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1984) {
			switch (resultCode) {
				case RESULT_OK:
					long id = data.getLongExtra("id", 0);
					Toast.makeText(this, "Kniha přidána (id: " + id + ").", Toast.LENGTH_LONG).show();
					vypisKnihy(razeni);
					break;
				case RESULT_CANCELED:
					Toast.makeText(this, "Akce zrušena", Toast.LENGTH_SHORT).show();
					break;
				case RESULT_FIRST_USER:
					Toast.makeText(this, "Nepodařilo se přidat knihu", Toast.LENGTH_LONG).show();
					break;
			}
		}
	}
}