package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Instruction extends Activity{
	Button btninstruction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instruction);

	Button btnokk = (Button)findViewById (R.id.btnok);
	btnokk.setOnClickListener(new OnClickListener() {
		

		@Override
		public void onClick(View v) {

			Intent i = new Intent(Instruction.this, SelectionActivity.class);
			startActivity(i);
		}
	});
	}
/** Called when the user clicks the Send button */
public void sendMessage(View view) {
    // Do something in response to button
}
}
			
