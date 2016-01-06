package com.lxxself.findfood.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxxself.findfood.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.lxxself.findfood.util.ToastUtil.show;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    private de.hdodenhof.circleimageview.CircleImageView profileimage;
    private android.widget.TextView profilename;
    private android.widget.TextView emailtitle;
    private android.widget.TextView emailcontent;
    private android.widget.TextView mobiletitle;
    private android.widget.TextView mobilecontent;
    private RelativeLayout emaillayout;
    private RelativeLayout mobilelayout;
    private TextView changepstitle;
    private RelativeLayout changepslayout;
    private String title = "";
    private int flag = -1;
    private BmobUser nowUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        nowUser = BmobUser.getCurrentUser(this);
        initView();
        initInfo();

        emaillayout.setOnClickListener(this);
        mobilelayout.setOnClickListener(this);
        changepslayout.setOnClickListener(this);
        profilename.setOnClickListener(this);
    }

    private void initInfo() {
        if (nowUser != null) {
            profilename.setText(nowUser.getUsername());
            emailcontent.setText(nowUser.getEmail());
            mobilecontent.setText(nowUser.getMobilePhoneNumber());
        }

    }

    private void initView() {
        this.changepslayout = (RelativeLayout) findViewById(R.id.changeps_layout);
        this.changepstitle = (TextView) findViewById(R.id.changeps_title);
        this.mobilelayout = (RelativeLayout) findViewById(R.id.mobile_layout);
        this.mobilecontent = (TextView) findViewById(R.id.mobile_content);
        this.mobiletitle = (TextView) findViewById(R.id.mobile_title);
        this.emaillayout = (RelativeLayout) findViewById(R.id.email_layout);
        this.emailcontent = (TextView) findViewById(R.id.email_content);
        this.emailtitle = (TextView) findViewById(R.id.email_title);
        this.profilename = (TextView) findViewById(R.id.profile_name);
        this.profileimage = (CircleImageView) findViewById(R.id.profile_image);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_layout:
                title = "邮箱";
                flag = 0;
                break;
            case R.id.mobile_layout:
                title = "手机号";
                flag = 1;
                break;
            case R.id.changeps_layout:
                title = "密码";
                flag = 2;
                break;
            case R.id.profile_name:
                title = "用户名";
                flag = 3;
                break;
        }
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("更改" + title)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        changeTask(flag,text);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    private void changeTask(int flag,String text) {
        BmobUser newUser = new BmobUser();
        switch (flag) {
            case 0:
                newUser.setEmail(text);
                break;
            case 1:
                newUser.setMobilePhoneNumber(text);
                break;
            case 2:
                newUser.setPassword(text);
                break;
            case 3:
                newUser.setUsername(text);
                break;
        }
        newUser.update(this,nowUser.getObjectId(),new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                show(PersonActivity.this, "更新用户信息成功");
                initInfo();
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                show(PersonActivity.this, "更新用户信息失败:" + msg);
            }
        });
    }
}
