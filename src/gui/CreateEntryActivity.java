package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import listeners.DateSetListener;
import model.Category;
import model.DataSingleton;
import model.Entry;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tinker.studio.cheque_manager.R;

import controller.CategoryArrayAdapter;

public class CreateEntryActivity extends Activity implements DateSetListener{

	public static final String ENTRY = "ENTRY";
	
	private Button btn_save;
	private EditText et_date;
	private EditText et_value;
	private Spinner s_categories;
	private CheckBox cb_isDone;
	private EditText et_description;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		
		btn_save = (Button) findViewById(R.id.btn_save);
		et_date = (EditText) findViewById(R.id.et_create_date);
		et_value = (EditText) findViewById(R.id.et_create_value);
		s_categories = (Spinner) findViewById(R.id.s_create_category);
		cb_isDone = (CheckBox) findViewById(R.id.cb_create_isDone);
		et_description = (EditText) findViewById(R.id.et_create_description);
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String date = et_date.getText().toString();
				String value = et_value.getText().toString();
				
				if(date.equals("") || value.equals("")){
					Toast.makeText(getApplicationContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show();
				}else{
								
				AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
				dialog.setTitle(getString(R.string.confirm_create_entry));
				dialog.setButton(DialogInterface.BUTTON_POSITIVE,getString(android.R.string.yes),new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  
						Date date = null;
						try {  
						    date = format.parse(et_date.getText().toString());  
						} catch (Exception e) {  
							e.printStackTrace();  
						}
						float value;
						try{
							value = Float.valueOf(et_value.getText().toString());
							
						}catch(NumberFormatException e){
							value = Float.NaN;
							e.printStackTrace();
						}
						boolean isDone = cb_isDone.isChecked();
						
						Category category = DataSingleton.getInstance().getCategories().get(s_categories.getSelectedItemPosition());
						String description = et_description.getText().toString();
						
						Entry entry = new Entry(date, value, category, isDone,description);
						
						getIntent().putExtra(ENTRY, entry);
						dialog.dismiss();
						setResult(RESULT_OK, getIntent());
						finish();
					}
				});
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.no), new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				dialog.show();
			}
			}
		});
		
		
		et_date.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					DateDialogFragment dialog = new DateDialogFragment();
					dialog.show(getFragmentManager(), "Date Picker");
				}
				
			}
		});
		et_date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DateDialogFragment dialog = new DateDialogFragment();
				dialog.show(getFragmentManager(), "Date Picker");
			}
		});
		et_date.setKeyListener(null);
		updateSpinner();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_entry, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void updateSpinner(){
		
		ArrayList<Category> categories = DataSingleton.getInstance().getCategories();
		
		CategoryArrayAdapter  categoryAdapter = new CategoryArrayAdapter(this, R.layout.spinner_category, categories);
		s_categories.setAdapter(categoryAdapter);
		
	}

	@Override
	public void fetchDate(String date) {
		et_date.setText(date);
	}
}
