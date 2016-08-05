package otkorm.pigsfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class acEdit extends AppCompatActivity implements View.OnClickListener{

    private Calc myCalc = new Calc();
    ListView lvBins;
    EditAdapter  eAdp;
    private ArrayList<Bin> bins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit);

        myCalc = getIntent().getParcelableExtra("Calc");
        bins = myCalc.getBins();

        eAdp = new EditAdapter(this, bins);
        lvBins = (ListView) findViewById(R.id.lvBins);
        lvBins.setAdapter(eAdp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                myCalc.setBins(bins);
                myCalc.calculate(myCalc.old);
                Intent stSh = new Intent(this,acShow.class);
                stSh.putExtra("Calc", myCalc);
                startActivity(stSh);
                finish();
                break;
        }

    }
}
