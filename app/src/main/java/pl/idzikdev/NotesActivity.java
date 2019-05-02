package pl.idzikdev;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity {
    @BindView(R.id.addClickButton)
    Button buttonAdd;

    @BindView(R.id.cancelClickButton)
    Button cancelButton;

    @BindView(R.id.editName)
    EditText editName;

    @BindView(R.id.editCategory)
    Spinner editCategory;

    static Button editDate;

    Intent intent;

    @OnClick(R.id.addClickButton)
    public void addClick(View v){
        if (editName.getText().toString().equals("")) {
            editName.setText("Name");
        }
        if (editDate.getText().toString().equals("")) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            editDate.setText(day + "/" + (month + 1) + "/" + year);
        }
        intent.putExtra("name",editName.getText().toString());
        intent.putExtra("date",editDate.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @OnClick(R.id.cancelClickButton)
    public void cancelClick(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        editDate = (Button)findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });
        intent=new Intent();
        String [] categoryList={"category: praca","category: zakupy","category: inne"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoryList);
        editCategory.setAdapter(adapter);
        editCategory.setSelection(0);
        editCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        intent.putExtra("category","praca");
                        break;
                    case 1:
                        intent.putExtra("category","zakupy");
                        break;
                    case 2:
                        intent.putExtra("category","inne");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            editDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
}
