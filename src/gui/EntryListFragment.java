package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import listeners.DateSetListener;
import model.Category;
import model.DataSingleton;
import model.Entry;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tinker.studio.cheque_manager.R;

import controller.CategoryArrayAdapter;
import controller.EntryArrayAdapter;

public class EntryListFragment extends Fragment implements DateSetListener{

	private ListView lv_entries;
	private Spinner s_categories;
	private TextView tv_show_negative_value, tv_show_positive_value, tv_show_total_value;
	private EditText et_date;
	private ArrayList<Entry> entries;
	public static final int CREATE_ENTRY = 10;
	
	public EntryListFragment(){};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		lv_entries = (ListView) rootView.findViewById(R.id.lv_entries);
		s_categories = (Spinner) rootView.findViewById(R.id.s_category_filter);
		entries = new ArrayList<Entry>();
		
		tv_show_negative_value = (TextView) rootView.findViewById(R.id.tv_show_negative_value);
		tv_show_negative_value.setTextColor(Color.RED);
		tv_show_positive_value = (TextView) rootView.findViewById(R.id.tv_show_positive_value);
		tv_show_positive_value.setTextColor(Color.GREEN);
		tv_show_total_value = (TextView) rootView.findViewById(R.id.tv_show_total_value);
		et_date = (EditText) rootView.findViewById(R.id.et_date_filter);
		final Fragment thisFragment = this;
		et_date.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					DateDialogFragment dialog = new DateDialogFragment();
					dialog.setListener((DateSetListener) thisFragment);
					dialog.show(getFragmentManager(), "Date Picker");
				}
				
			}
		});
		et_date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DateDialogFragment dialog = new DateDialogFragment();
				dialog.setListener((DateSetListener) thisFragment);
				dialog.show(getFragmentManager(), "Date Picker");
			}
		});
		et_date.setKeyListener(null);
		
		
		updateSpinner();
		updateEntryList();
		updateValues();
        return rootView;
	}
	
	public void updateEntryList(){
		
		entries.clear();
		
		SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  
		Date date = null;
		try {  
		    date = format.parse(et_date.getText().toString());  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		
		boolean hasNoFilter = s_categories.getSelectedItemPosition() == 0? true:false;
		
		Category category = null;
		if(!hasNoFilter)
			category = DataSingleton.getInstance().getCategories().get(s_categories.getSelectedItemPosition());
		
		for(Entry e: DataSingleton.getInstance().getEntries()){
			if((date == null ||date.before(e.getDueDate())) && (hasNoFilter || category.equals(e.getCategory()) 
					|| category.equals(e.getCategory().getUpperCategory()))){
				entries.add(e);
			}
			
		}
		
		EntryArrayAdapter entryAdapter = new EntryArrayAdapter(getActivity(), R.layout.list_entry, entries);
		lv_entries.setAdapter(entryAdapter);
		
	}
	
	public void updateValues(){
		
		float negative_value=0;
		float positive_value=0;
		float total_value=0;
		
		for(Entry e: entries){
			if(e.getValue()<0){
				negative_value+=e.getValue();
			}else{
				positive_value+=e.getValue();
			}
		}
		
		total_value = negative_value + positive_value;
		
		tv_show_negative_value.setText(String.valueOf(negative_value));
		tv_show_positive_value.setText(String.valueOf(positive_value));
		String value = String.format("%.2f", total_value) ;
		tv_show_total_value.setText(value);
		
		if(total_value>0){
			tv_show_total_value.setTextColor(Color.GREEN);
		}else if(total_value<0){
			tv_show_total_value.setTextColor(Color.RED);
		}else{
			tv_show_total_value.setTextColor(Color.BLUE);
		}
		
	}
	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setTitle(R.string.home);
		super.onCreateOptionsMenu(menu, inflater);
	}


	public void updateSpinner(){
		
		ArrayList<Category> categories = DataSingleton.getInstance().getCategories();
		
		CategoryArrayAdapter  categoryAdapter = new CategoryArrayAdapter(getActivity(), R.layout.spinner_category, categories);
		s_categories.setAdapter(categoryAdapter);
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.action_add_entry){
			Intent i = new Intent(getActivity(), CreateEntryActivity.class);
        	startActivityForResult(i, CREATE_ENTRY);
		};
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println(data);
		if(requestCode ==  EntryListFragment.CREATE_ENTRY){
			if(resultCode == getActivity().RESULT_OK){
				Entry entry = (Entry) data.getSerializableExtra(CreateEntryActivity.ENTRY);
				System.out.println(entry.getValue()+"  " + entry.getDueDate() + "  " + entry.getCategory());
				DataSingleton.getInstance().addEntry(entry);
				updateEntryList();
				updateValues();
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void fetchDate(String date) {
		et_date.setText(date);	
		updateEntryList();
		updateValues();
	}
	
	

}
