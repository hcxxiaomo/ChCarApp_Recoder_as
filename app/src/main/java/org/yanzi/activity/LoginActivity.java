package org.yanzi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiaomo.util.RestClient;
import com.zed3.R;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity  extends Activity  implements OnClickListener,OnLongClickListener {

	// 声明控件对象
	private EditText et_name, et_pass;
	private Button mLoginButton,mLoginError,mRegister,ONLYTEST;
	int selectIndex=1;
	int tempSelect=selectIndex;
	boolean isReLogin=false;
	private int SERVER_FLAG=0;
	private RelativeLayout countryselect;
	private TextView   coutry_phone_sn, coutryName;//
	// private String [] coutry_phone_sn_array,coutry_name_array;
	public final static int LOGIN_ENABLE=0x01;    //注册完毕了
	public final static int LOGIN_UNABLE=0x02;    //注册完毕了
	public final static int PASS_ERROR=0x03;      //注册完毕了
	public final static int NAME_ERROR=0x04;      //注册完毕了

	final Handler UiMangerHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case LOGIN_ENABLE:
					mLoginButton.setClickable(true);
//	    mLoginButton.setText(R.string.login);
					break;
				case LOGIN_UNABLE:
					mLoginButton.setClickable(false);
					break;
				case PASS_ERROR:

					break;
				case NAME_ERROR:
					break;
			}
			super.handleMessage(msg);
		}
	};
	private Button bt_username_clear;
	private Button bt_pwd_clear;
	private Button bt_pwd_eye;
	private TextWatcher username_watcher;
	private TextWatcher password_watcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//  requestWindowFeature(Window.FEATURE_NO_TITLE);
		//  //不显示系统的标题栏
		//  getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//	    WindowManager.LayoutParams.FLAG_FULLSCREEN );

		setContentView(R.layout.activity_login);
		et_name = (EditText) findViewById(R.id.username);
		et_pass = (EditText) findViewById(R.id.password);

		bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
		bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
		bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
		bt_username_clear.setOnClickListener(this);
		bt_pwd_clear.setOnClickListener(this);
		bt_pwd_eye.setOnClickListener(this);
		initWatcher();
		et_name.addTextChangedListener(username_watcher);
		et_pass.addTextChangedListener(password_watcher);

		mLoginButton = (Button) findViewById(R.id.login);
		mLoginError  = (Button) findViewById(R.id.login_error);
		mRegister    = (Button) findViewById(R.id.register);
		ONLYTEST     = (Button) findViewById(R.id.registfer);
		ONLYTEST.setOnClickListener(this);
		ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
		mLoginButton.setOnClickListener(this);
		mLoginError.setOnClickListener(this);
		mRegister.setOnClickListener(this);

		SharedPreferences sp =  this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		et_name.setText(sp.getString("user", ""));
		et_pass.setText(sp.getString("pass", ""));
		//  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
		//  countryselect.setOnClickListener(this);
		//  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
		//  coutryName=(TextView) findViewById(R.id.country_name);

		//  coutryName.setText(coutry_name_array[selectIndex]);    //默认为1
		//  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);
	}
	/**
	 * 手机号，密码输入控件公用这一个watcher
	 */
	private void initWatcher() {
		username_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void afterTextChanged(Editable s) {
				et_pass.setText("");
				if(s.toString().length()>0){
					bt_username_clear.setVisibility(View.VISIBLE);
				}else{
					bt_username_clear.setVisibility(View.INVISIBLE);
				}
			}
		};

		password_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void afterTextChanged(Editable s) {
				if(s.toString().length()>0){
					bt_pwd_clear.setVisibility(View.VISIBLE);
				}else{
					bt_pwd_clear.setVisibility(View.INVISIBLE);
				}
			}
		};
	}



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.login:  //登陆
				login();
				break;
			case R.id.login_error: //无法登陆(忘记密码了吧)
				//   Intent login_error_intent=new Intent();
				//   login_error_intent.setClass(LoginActivity.this, ForgetCodeActivity.class);
				//   startActivity(login_error_intent);
				break;
			case R.id.register:    //注册新的用户
				//   Intent intent=new Intent();
				//   intent.setClass(LoginActivity.this, ValidatePhoneNumActivity.class);
				//   startActivity(intent);

				break;

			case R.id.registfer:
				if(SERVER_FLAG>10){
					Toast.makeText(this, "[内部测试--谨慎操作]", Toast.LENGTH_SHORT).show();
				}
				SERVER_FLAG++;
				break;
			case R.id.bt_username_clear:
				et_name.setText("");
				et_pass.setText("");
				break;
			case R.id.bt_pwd_clear:
				et_pass.setText("");
				break;
			case R.id.bt_pwd_eye:
				if(et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
					bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_s);
					et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
				}else{
					bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_n);
					et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				et_pass.setSelection(et_pass.getText().toString().length());
				break;
		}
	}
	/**
	 * 登陆
	 */
	private void login() {

		Toast.makeText(this, "正在联网登录中...", Toast.LENGTH_SHORT).show();
		//连接网络得到数据
		RequestParams params = new RequestParams();
		params.put("policeId", et_name.getText().toString());
		params.put("password", et_pass.getText().toString());
		RestClient.post("carplatenumber/carNumber/policeLogin", params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {
				Log.i("-xiaomo-", "noticePage--->:"+response);
				if ("success".equals(response.optString("login"))) {
					JSONObject policeInfo = response.optJSONObject("policeInfo");
					Editor edit = LoginActivity.this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE).edit();
					edit.putString("policeId", policeInfo.optString("policeId"));
					edit.putString("name", policeInfo.optString("name"));
					edit.putString("position", policeInfo.optString("position"));
					edit.putString("jobTitle", policeInfo.optString("jobTitle"));
					edit.putString("depart", policeInfo.optString("depart"));
					edit.putString("unit", policeInfo.optString("unit"));
					edit.putString("telephone", policeInfo.optString("telephone"));
					edit.putString("user", et_name.getText().toString());
					edit.putString("pass", et_pass.getText().toString());
					edit.commit();
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					LoginActivity.this.startActivity(intent);
					LoginActivity.this.finish();
				}else{
					Toast.makeText(LoginActivity.this, "用户或密码错误，请重新输入！", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(LoginActivity.this, "网络连接错误，请检查网络连接！", Toast.LENGTH_LONG).show();
			}

		});

	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.registfer:
				if(SERVER_FLAG>9){

				}
				//   SERVER_FLAG++;
				break;
		}
		return true;
	}


	/**
	 * 监听Back键按下事件,方法2:
	 * 注意:
	 * 返回值表示:是否能完全处理该事件
	 * 在此处返回false,所以会继续传播该事件.
	 * 在具体项目中此处的返回值视情况而定.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(isReLogin){
				Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
				mHomeIntent.addCategory(Intent.CATEGORY_HOME);
				mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				LoginActivity.this.startActivity(mHomeIntent);
			}else{
				LoginActivity.this.finish();
			}
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
