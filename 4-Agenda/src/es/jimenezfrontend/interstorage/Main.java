package es.jimenezfrontend.interstorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {

	public Button btn;
	public EditText text_name, text_phone;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.layout_main );

		btn = ( Button ) findViewById ( R.id.btn_add );
		text_name = ( EditText ) findViewById ( R.id.name_edt );
		text_phone = ( EditText ) findViewById ( R.id.phone_edt );

		btn.setOnClickListener ( new OnClickListener () {
			@Override
			public void onClick ( View v ) {
				filewrite ();
			}
		} );

	}

	protected void filewrite () {
		OutputStreamWriter escritor = null;
		try {
			File theDir = new File ( Environment.getDataDirectory () + "/data/personas" );
			if ( !theDir.exists () ) {
				theDir.mkdir ();
				theDir.setReadable ( true, false );
				theDir.setExecutable ( true, false );
			}
			File f = new File ( Environment.getDataDirectory () + "/data/personas/personas.txt" );
			Log.d ( "dir", Environment.getDataDirectory () + "/data/personas/personas.txt" );
			Log.d ( "existe", f.exists () + "" );
			if ( f.exists () ) {
				FileInputStream fin = new FileInputStream ( f );
				int c;
				String temp = "";
				while ( ( c = fin.read () ) != -1 ) {
					temp = temp + Character.toString ( ( char ) c );
				}
				fin.close ();
				Log.d ( "tem", temp );
				escritor = new OutputStreamWriter ( new FileOutputStream ( f ) );
				escritor.write ( temp );
				escritor.write ( text_name.getText ().toString () + "-" );
				escritor.write ( text_phone.getText ().toString () + "\n" );
			}
			else {
				escritor = new OutputStreamWriter ( new FileOutputStream ( f ) );
				escritor.write ( text_name.getText ().toString () + "-" );
				escritor.write ( text_phone.getText ().toString () + "\n" );
			}
			text_name.setText ( "" );
			text_phone.setText ( "" );
			f.setReadable ( true, false );
		}
		catch ( Exception e ) {
			Log.e ( "error", e.toString () );
		}
		finally {
			try {
				if ( escritor != null ) {
					escritor.close ();
				}
			}
			catch ( IOException ex ) {
				Log.e ( "error", ex.toString () );
			}
		}
	}
}
