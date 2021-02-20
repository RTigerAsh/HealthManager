package cn.edu.swufe.healthmanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.opengl.ETC1;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.LoginUser;
import cn.edu.swufe.healthmanager.util.ActivityCollector;
import cn.edu.swufe.healthmanager.widget.TitleLayout;

public class EditName extends AppCompatActivity {
    private LoginUser loginUser = LoginUser.getInstance();
    private TitleLayout tl_title;
    private EditText edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_edit_name);

        tl_title = (TitleLayout) findViewById(R.id.tl_title);
        edit_name = (EditText) findViewById(R.id.et_edit_name);
        edit_name.setText(loginUser.getName());

        //设置监听器
        //如果点击完成，则更新loginUser并销毁
        tl_title.getTextView_forward().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser.setName(edit_name.getText().toString());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
