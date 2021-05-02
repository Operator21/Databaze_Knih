package com.standarts.databazeknih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PridejActivity extends Activity {

	private EditText nazev;
	private EditText ps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pridej);

		this.nazev = findViewById(R.id.nazevKnihy);
		this.ps = findViewById(R.id.pocetStran);

		Button zr = findViewById(R.id.zrusit);
		// TODO - 1. faze - kliknutim na ZRUSIT odeslat zpet stav RESULT_CANCELED
		((Button)findViewById(R.id.zrusit)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

	public void uloz(String nazev, int pocetStran) {
		// TODO - 1. faze - vlozeni zaznamu do DB (otevrit DB, vlozit, uzavrit DB)
		MojeKnihyDB db = new MojeKnihyDB(this);

		long id = db.vlozKnihu(nazev, pocetStran, "Neznamy");

		Log.d("LOG", "ID: " + id + "");
		//long id = 1;
		if(id > 0) {
			posliZpet(id);
		}else {
			posliZpet(RESULT_FIRST_USER);
		}
	}
	
	private void posliZpet(int stav) {
		// TODO - 1. faze - odeslat zpet stav RESULT_FIRST_USER nebo RESULT_CANCELED
		setResult(stav);
		finish();
	}

	private void posliZpet(long id) {
		// TODO - 1. faze - odeslat zpet stav RESULT_OK a id zaznamu
		Intent i = new Intent();
		i.putExtra("id", id);
		setResult(RESULT_OK, i);
		finish();
	}

	public void pridej(View view) {
		// TODO - 1. faze - po kliknuti na tlacitko PRIDEJ pridat zaznam

		String nazev = ((TextView)findViewById(R.id.nazevKnihy)).getText().toString();
		int pocetstran = Integer.parseInt(((TextView)findViewById(R.id.pocetStran)).getText().toString());

		Log.d("KNIHA", "NAZEV: " + nazev + " | STRAN:" + pocetstran);

		if(nazev.length() > 0 && pocetstran > 0){
			uloz(nazev, pocetstran);
		}

	}
}