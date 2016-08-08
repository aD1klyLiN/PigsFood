package otkorm.pigsfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/*------Экран ввода типа корма-----------
* Версия 0.0.1
* Поля для каждой линии пустые
* Необходимо заполнить все поля*/

public class acFoodType extends AppCompatActivity implements View.OnClickListener{

    public Calc myCalc = new Calc();
    ListView lvType;
    TypeAdapter  tAdp;
    ArrayList<String> ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_foodtype);

        ft = new ArrayList<>(15);
        String m;
        for (int i=0; i<=14; i++) {
            m = Integer.toString(myCalc.getNumber(i));
            ft.add(m);
        }

        tAdp = new TypeAdapter(this, ft);
        lvType = (ListView) findViewById(R.id.lvType);
        lvType.setAdapter(tAdp);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDone:
                if (tAdp.isChecked()) {
                    myCalc.setFoodType(tAdp.getFoodType());
                    int i = 1;
                    if (myCalc.getCalcType()==myCalc.STD) {
                        Intent stIn = new Intent(this,acValueInput.class);
                        stIn.putExtra("Calc", myCalc);
                        stIn.putExtra("i", i);
                        startActivity(stIn);
                        finish();
                    } else {
                        Intent stIn = new Intent(this,acEdit.class);
                        stIn.putExtra("Calc", myCalc);
                        //stIn.putExtra("i", i);
                        startActivity(stIn);
                        finish();
                    }

                    /*String m = "";
                    for (int j=0; j<=14; j++) {
                        m = m + Character.toString(myCalc.getFoodType(j));
                    }
                    Toast.makeText(this, m, Toast.LENGTH_LONG).show();*/
                } else {
                    Toast.makeText(this, R.string.type_error, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancel:
                break;
        }
    }
}
