package cn.edu.swufe.healthmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.MainActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.ActivityCollector;

public class HealthReport extends AppCompatActivity implements View.OnClickListener{
    private  Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_healthreport);
        bt = (Button)findViewById(R.id.button_healthreport);
        bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_healthreport:
                Intent intent1 = new Intent(HealthReport.this, MainActivity.class);
                startActivity(intent1);

                break;

            default:
                break;
        }
    }
}
