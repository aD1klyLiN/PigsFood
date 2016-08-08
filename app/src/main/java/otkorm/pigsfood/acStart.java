package otkorm.pigsfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*----------------Стартовый экран--------------
* Версия 0.0.1
* Кнопка начала измерений
* Кнопка просмотра результатов
* */

public class acStart extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            /*Версия 0.0.1
            * Начало нового цикла измерений
            * Переход к экрану выбора типа корма
            * */
            case R.id.btnWrite:
                Intent it = new Intent(this, acFoodType.class);
                startActivity(it);
                finish();
                break;

            /*Версия 0.0.1
            * Загрузка результатов измерения из хранилища настроек
            * Переход к экрану отображения результатов*/
            case R.id.btnShow:
                Intent stSh = new Intent(this,acShow.class);
                startActivity(stSh);
                finish();
                break;
        }

    }
}
