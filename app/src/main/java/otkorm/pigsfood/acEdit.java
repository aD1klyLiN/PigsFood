package otkorm.pigsfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/*Экран ввода данных: содержит список бункеров. Для каждого бункера:
* - кнопки "+" и "-" задать значение уровня;
* - переключатель типа корма;
* - кнопка обнуления;
* - кнопка вызова окна ввода данных терминала;
* - отображение приблизительной массы и плотности.*/

public class acEdit extends AppCompatActivity implements View.OnClickListener{

    private Calc myCalc = new Calc();
    private ListView lvBins; //список
    private EditAdapter  eAdp; //адаптер
    private ArrayList<Bin> bins; //коллекция бункеров

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit);

        myCalc = getIntent().getParcelableExtra("Calc"); //получаем объект расчётов
        bins = myCalc.getBins(); //заполняем список бункеров

        eAdp = new EditAdapter(this, bins);
        lvBins = (ListView) findViewById(R.id.lvBins);
        lvBins.setAdapter(eAdp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                myCalc.setBins(bins);
                myCalc.calculate(myCalc.OLD);
                Intent stSh = new Intent(this,acShow.class);
                stSh.putExtra("Calc", myCalc);
                startActivity(stSh);
                finish();
                break;
        }

    }
}
