package com.example.cityview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AboutScreen extends Activity {
	
    private Button StartButton;
    
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);


        StartButton = (Button) findViewById(R.id.button_start);        
        StartButton.setOnClickListener(new OnClickListener() {          	
            
			@Override
			public void onClick(View v) {
 
				Intent p = new Intent(AboutScreen.this, ProgressScreen.class);
	             startActivity(p);
			}
        });
    

    }
}
