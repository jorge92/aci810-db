package com.example.aci570_db;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aci570_db.db.MyAppDataSource;
import com.example.aci570_db.model.Person;

public class PersonActivity extends Activity {

	private MyAppDataSource ds;
	private Person personToUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		// Show the Up button in the action bar.
		setupActionBar();
		
		ds = new MyAppDataSource(this);
	    ds.open();
	    
	    Intent i = this.getIntent();
	    
	    if(i.hasExtra(MainActivity.EXTRA_PERSON))
	    {
	    	Person p = (Person) i.getSerializableExtra(MainActivity.EXTRA_PERSON);
	    	
	    	EditText firstNameField = (EditText) this.findViewById(R.id.firstNameField);
			firstNameField.setText(p.getFirstName());
			
			EditText lastNameField = (EditText) this.findViewById(R.id.lastNameField);
			lastNameField.setText(p.getLastName());
			
			EditText emailField = (EditText) this.findViewById(R.id.emailField);
			emailField.setText(p.getEmail());
			
			EditText userField = (EditText) this.findViewById(R.id.userField);
			userField.setText(p.getUser());
			
			Button saveButton = (Button) this.findViewById(R.id.saveButton);
			saveButton.setText("Update");
			
			Button deleteButton = (Button) this.findViewById(R.id.deleteButton);
			deleteButton.setVisibility(Button.VISIBLE);
			
			this.setTitle("Update Person");
			
			this.personToUpdate = p;
	    }
	    else
	    {
	    	Button saveButton = (Button) this.findViewById(R.id.saveButton);
	    	saveButton.setText("Create");
	    	
	    	Button deleteButton = (Button) this.findViewById(R.id.deleteButton);
	    	deleteButton.setVisibility(Button.VISIBLE);
	    	
	    	this.setTitle("Create Person");
	    	
	    	this.personToUpdate = null;
	    }
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_person, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSaveButtonClicked(View view) {
		EditText firstNameField = (EditText) this.findViewById(R.id.firstNameField);
		String firstName = firstNameField.getText().toString();
		
		EditText lastNameField = (EditText) this.findViewById(R.id.lastNameField);
		String lastName = lastNameField.getText().toString();
		
		EditText emailField = (EditText) this.findViewById(R.id.emailField);
		String email = emailField.getText().toString();
		
		EditText userField = (EditText) this.findViewById(R.id.userField);
		String user = userField.getText().toString();
		
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || user.isEmpty())
		{
			Toast.makeText(this, "Complete the form before saving", Toast.LENGTH_LONG).show();
			return;
		}
		
		Person p = null;
		
		if(this.personToUpdate != null)
		{
			p = ds.updatePerson(this.personToUpdate, firstName, lastName, email, user);
		}
		else
		{
			p = ds.createPerson(firstName, lastName, email, user);
		}
		
		Intent i = new Intent();
		i.putExtra(MainActivity.EXTRA_PERSON, p);
		i.putExtra(MainActivity.EXTRA_REMOVE, false);
		this.setResult(RESULT_OK, i);
		
		this.finish();
	}
	
	public void onDeleteButtonClicked(View view) {
		
		Person p = ds.deletePerson(this.personToUpdate);
		
		Intent i = new Intent();
		i.putExtra(MainActivity.EXTRA_PERSON, p);
		i.putExtra(MainActivity.EXTRA_REMOVE, true);
		this.setResult(RESULT_OK, i);
		
		this.finish();
	}
	
	@Override
	protected void onResume() {
		ds.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		ds.close();
		super.onPause();
	}
}
