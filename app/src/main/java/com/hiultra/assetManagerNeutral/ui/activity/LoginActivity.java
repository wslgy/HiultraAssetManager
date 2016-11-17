package com.hiultra.assetManagerNeutral.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.MVP_P.LoginPresenter;
import com.hiultra.assetManagerNeutral.MVP_V.ILoginView;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.util.SpUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 登录界面
 * 
 * @author Tom
 * @date 2016年10月31日
 * @Time 下午7:53:10
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements ILoginView {
    
    @ViewInject(R.id.et_login_username)
    private EditText etName;
    @ViewInject(R.id.et_login_password)
    private EditText etPass;
    @ViewInject(R.id.remember)
    private CheckBox cbRemember;
    @ViewInject(R.id.setIp)
    private View setIp;
    @ViewInject(R.id.tvVersion)
    private TextView tvVersion;
    @ViewInject(R.id.rl_loading)
    private View vLoading;
    
    private LoginPresenter loginPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void initView() {}
    
    @Override
    protected void initData(Bundle savedInstanceState) throws DbException {
        super.initData(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        showVersion();
        String loginName = SpUtil.getString(Constants.SP_LOGINNAME, "");
        String passWord = SpUtil.getString(Constants.SP_PASSWORD, "");
        etName.setText(loginName);
        etPass.setText(passWord);
        boolean remember = SpUtil.getBoolean(Constants.SP_REMEMBER, false);
        cbRemember.setChecked(remember);
        Varible.ip = SpUtil.getString(Constants.SP_IP, "");
        Varible.port = SpUtil.getString(Constants.SP_PORT, "");
    }
    
    /**
     * 登录
     * @param v
     */
    @OnClick(R.id.login_button)
    public void login(View v) {
        String loginName = etName.getText().toString().trim();
        String passWord = etPass.getText().toString().trim();
        if(checkLoginInfo(loginName, passWord)) {
            save(loginName, passWord);
            loginPresenter.login(loginName, passWord);
        }
    }
    
    @Override
    public void showLoading() {
        vLoading.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideLoading() {
        vLoading.setVisibility(View.GONE);
    }
    
    @Override
    public void loginFailed() {
        ToastUtil.show(R.string.msg_loginFailed);
    }
    
    @Override
    public void loginSuccess() {
        ToastUtil.show(R.string.msg_loginSuccess);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    /**
     * 显示版本号
     */
    private void showVersion() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
            String name = info.versionName;
            int code = info.versionCode;
            tvVersion.setText(String.valueOf("版本:V" + name));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证登录信息
     * @param loginName
     * @param passWord
     * @return
     */
    private boolean checkLoginInfo(String loginName, String passWord) {
        if(TextUtils.isEmpty(loginName)) {
            ToastUtil.show("登录名不能为空");
            return false;
        }
        if(TextUtils.isEmpty(passWord)) {
            ToastUtil.show("密码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 是否保存用户名以及密码
     * @param loginName
     * @param passWord
     */
    private void save(String loginName, String passWord) {
        if(cbRemember.isChecked()) { // 保存密码
            SpUtil.putString(Constants.SP_LOGINNAME, loginName);
            SpUtil.putString(Constants.SP_PASSWORD, passWord);
            SpUtil.putBoolean(Constants.SP_REMEMBER, true);
        } else { //  不保存密码
            SpUtil.putString(Constants.SP_LOGINNAME, new String(""));
            SpUtil.putString(Constants.SP_PASSWORD, new String(""));
            SpUtil.putBoolean(Constants.SP_REMEMBER, false);
        }
    }
    /**
     * 设置IP和端口
     *
     */
    @OnClick(R.id.setIp)
    private void ipSetting(View v) {
        LayoutInflater inflater = LayoutInflater.from(this); // 间接显示
        final View textEntryView = inflater.inflate(R.layout.activity_ip, null);
        final EditText editInputip = (EditText) textEntryView.findViewById(R.id.edtInputip);
        editInputip.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText editInputport = (EditText) textEntryView.findViewById(R.id.edtInputport);
        editInputport.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        // 获取本地保存的ip和port
        String ip_str = SpUtil.getString(Constants.SP_IP, "");
        String port_str = SpUtil.getString(Constants.SP_PORT, "");
        editInputip.setText(ip_str);
        editInputport.setText(port_str);
        // 弹出对话框
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        // builder.setIcon(R.drawable.dialogicon);
        builder.setTitle(R.string.title_setIp);
        builder.setView(textEntryView);
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // 保存设置的值
                String ip = editInputip.getText().toString().trim();
                String port = editInputport.getText().toString().trim();
                SpUtil.putString(Constants.SP_IP, ip);
                SpUtil.putString(Constants.SP_PORT, port);
                Varible.ip = ip;
                Varible.port = port;
            }
        });
        builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
             // 保存设置的值
                String ip = editInputip.getText().toString().trim();
                String port = editInputport.getText().toString().trim();
                SpUtil.putString(Constants.SP_IP, ip);
                SpUtil.putString(Constants.SP_PORT, port);
            }
        });
        builder.show();
    }
    
}
