package org.yanzi.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiaomo.util.RestClient;
import com.zed3.R;

import org.apache.http.Header;
import org.json.JSONObject;
import org.yanzi.activity.MainActivity;
import org.yanzi.constant.Constant;

public class SettingFragment extends BaseFragment {

	private TextView setting_name_position;
	private TextView setting_depart;
	private TextView user_info_name;
	private TextView user_info_id;
	private TextView user_info_depart;
	private TextView user_info_position;
	private TextView user_info_phone;

	private EditText setting_old_pass;
	private EditText setting_new_pass;

	private Button setting_change_pass;
	private SharedPreferences sp ;

	private Activity mActivity;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.setting_layout,
				container, false);
		setting_name_position = (TextView)settingLayout.findViewById(R.id.setting_name_position);
		setting_depart = (TextView)settingLayout.findViewById(R.id.setting_depart);
		mActivity = ((MainActivity) getActivity());
		sp = mActivity.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		Log.i("-xiaomo-", sp.getAll().toString());
		setting_name_position.setText(sp.getString("name", "警官")+"   "+sp.getString("jobTitle", "职称")+"   您好！");//姓名   职称  您好！
		setting_depart.setText(sp.getString("unit", "单位")+"   "+sp.getString("depart", "部门"));

		user_info_name = (TextView)settingLayout.findViewById(R.id.user_info_name);
		user_info_id = (TextView)settingLayout.findViewById(R.id.user_info_id);
		user_info_depart = (TextView)settingLayout.findViewById(R.id.user_info_depart);
		user_info_position = (TextView)settingLayout.findViewById(R.id.user_info_position);
		user_info_phone = (TextView)settingLayout.findViewById(R.id.user_info_phone);

		setting_new_pass = (EditText)settingLayout.findViewById(R.id.setting_new_pass);
		setting_old_pass = (EditText)settingLayout.findViewById(R.id.setting_old_pass);

		user_info_name.append(sp.getString("name", "姓名"));
		user_info_id.append(sp.getString("policeId", "编号"));
		user_info_depart.append(sp.getString("depart", "部门"));
		user_info_position.append(sp.getString("position", "职位"));
		user_info_phone.append(sp.getString("telephone", "电话"));

		setting_change_pass = (Button) settingLayout.findViewById(R.id.setting_change_pass);

		setting_change_pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RequestParams params = new RequestParams();
				params.put("policeId", sp.getString("policeId", "编号"));
				params.put("oldPassword", setting_old_pass.getText().toString());
				params.put("newPassword", setting_new_pass.getText().toString());
				RestClient.post("carplatenumber/carNumber/policeChangePass", params, new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(int statusCode, Header[] headers,
										  JSONObject response) {
						Log.i("-xiaomo-", "policeChangePass-success:"+response);
						if (response.optInt("msg") == 1) {//修改成功
							Toast.makeText(mActivity, "密码修改成功！", Toast.LENGTH_LONG).show();
							sp.edit().putString("pass",  setting_new_pass.getText().toString()).commit();
							setting_new_pass.setText("");
							setting_old_pass.setText("");
						}else{
							Toast.makeText(mActivity, response.optString("data"), Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
										  String responseString, Throwable throwable) {
						Toast.makeText(mActivity, "网络无法连接，请检查网络信息！", Toast.LENGTH_LONG).show();
						super.onFailure(statusCode, headers, responseString, throwable);
					}

				});
			}
		});

		return settingLayout;
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_SETTING;

	}


}
