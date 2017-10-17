package org.yanzi.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.szOCR.general.CGlobal;
import com.xiaomo.util.RestClient;
import com.zed3.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yanzi.activity.MainActivity;
import org.yanzi.bean.MessageInfo;
import org.yanzi.constant.Constant;
import org.yanzi.fragment.adapter.MessageInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageIndexFragment extends BaseFragment {


	//分页相关功能
	private int currentPage = 1;//当前页
	private int pageSize = 10;//每页数据量

	private  View messageLayout ;

	private TextView index_name_position;
	private TextView index_depart;

	private static final String TAG = "MessageFragment";
	private MainActivity mMainActivity ;
	private ListView mListView;
	private ImageView page_index_icon_car_xxqc_02;
	private ImageView page_index_icon_car_dxqc_01;
	private ImageView page_index_icon_car_jlc_16;
	private ImageView page_index_icon_car_gc_15;
	private ImageView page_index_icon_car_qbmtc_08;
	private MessageInfoAdapter mMsgAdapter;
	private List<MessageInfo> mMsgBean = new ArrayList<MessageInfo>();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.page_index_layout,
				container, false);
		Log.d(TAG, "onCreateView---->");
		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		mListView = (ListView)messageLayout.findViewById(R.id.page_index_listview);
		page_index_icon_car_xxqc_02 = (ImageView) messageLayout.findViewById(R.id.page_index_icon_car_xxqc_02);
		page_index_icon_car_dxqc_01 = (ImageView) messageLayout.findViewById(R.id.page_index_icon_car_dxqc_01);
		page_index_icon_car_jlc_16 = (ImageView) messageLayout.findViewById(R.id.page_index_icon_car_jlc_16);
		page_index_icon_car_gc_15 = (ImageView) messageLayout.findViewById(R.id.page_index_icon_car_gc_15);
		page_index_icon_car_qbmtc_08 = (ImageView) messageLayout.findViewById(R.id.page_index_icon_car_qbmtc_08);
		showFirstPage();

		index_name_position = (TextView)messageLayout.findViewById(R.id.index_name_position);
		index_depart = (TextView)messageLayout.findViewById(R.id.index_depart);

		SharedPreferences sp = mMainActivity.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		Log.i("-xiaomo-", sp.getAll().toString());
		index_name_position.setText(sp.getString("name", "警官")+"   "+sp.getString("jobTitle", "职称")+"   您好！");//姓名   职称  您好！
		index_depart.setText(sp.getString("unit", "单位")+"   "+sp.getString("depart", "部门"));

		/*mMsgAdapter = new MessageInfoAdapter(mMsgBean, mMainActivity);
		mListView.setAdapter(mMsgAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(mMainActivity, mMsgBean.get(position).toString(),
						Toast.LENGTH_SHORT).show();
				PopupWindowNoticeInfo bpw = new PopupWindowNoticeInfo(mMainActivity,null);
				bpw.showAtLocation(messageLayout, Gravity.TOP | Gravity.START, 0, 0);

			}

		});
		*/

		page_index_icon_car_xxqc_02.setOnClickListener(on);
		page_index_icon_car_dxqc_01.setOnClickListener(on);
		page_index_icon_car_jlc_16.setOnClickListener(on);
		page_index_icon_car_gc_15.setOnClickListener(on);
		page_index_icon_car_qbmtc_08.setOnClickListener(on);

		return messageLayout;
	}

	android.view.View.OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			/*case R.id.page_index_icon_car_xxqc_02:
				CGlobal.carType = 02;
				break;*/
				case R.id.page_index_icon_car_dxqc_01:
					CGlobal.carType = "01";
					break;
				case R.id.page_index_icon_car_jlc_16:
					CGlobal.carType = "16";
					break;
				case R.id.page_index_icon_car_gc_15:
					CGlobal.carType = "15";
					break;
				case R.id.page_index_icon_car_qbmtc_08:
					CGlobal.carType = "07";
					break;
				default:
					CGlobal.carType = "02";
					break;
			}
			mMainActivity.setTabSelection(Constant.FRAGMENT_FLAG_PAGE_CAR_CHECK);
		}
	};

	private void showFirstPage(){

//		 mMsgBean = new ArrayList<MessageInfo>();
//		 mMsgBean.add(new MessageInfo(1, "张三吃饭没?", "2017-05-02"));
		Toast.makeText(mMainActivity, "正在联网获取数据信息", Toast.LENGTH_SHORT).show();
		//连接网络得到数据
		RequestParams params = new RequestParams();
		params.put("page", String.valueOf(currentPage));
		params.put("row", String.valueOf(pageSize));
		RestClient.post("carplatenumber/carNumber/getInfoByPage", params, new JsonHttpResponseHandler(){


			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {
				Log.i("-xiaomo-", "noticePage--->:"+response);
				mMsgBean = jsonArrayToList(response.optJSONArray("rows"));
				mMsgAdapter = new MessageInfoAdapter(mMsgBean, mMainActivity);
				mListView.setAdapter(mMsgAdapter);
			}


			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
/*				Toast.makeText(mMainActivity, mMsgBean.get(position).toString(),
						Toast.LENGTH_SHORT).show();*/

				PopupWindowNoticeInfo bpw = new PopupWindowNoticeInfo(mMainActivity,mMsgBean.get(position));
				bpw.showAtLocation(messageLayout, Gravity.TOP | Gravity.START, 0, 0);
			}

		});
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d(TAG, "PageIndexFragment-onAttach-----");

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "PageIndexFragment-onCreate------");
		/*mMsgBean.add(new MessageInfo(1, "张三吃饭没?", "2017-05-02"));
		mMsgBean.add(new MessageInfo(2, "控件内部内容", "2017-04-02"));
		mMsgBean.add(new MessageInfo(3, "钮上设置的内容例如图片", "2017-03-02"));
		mMsgBean.add(new MessageInfo(4, "果imageview对应的图片", "2017-02-02"));
		mMsgBean.add(new MessageInfo(5, " 不同设备显示效果相同", "2017-01-02"));
		mMsgBean.add(new MessageInfo(6, "将来的显示器类型上正常", "2017-01-01"));*/
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "PageIndexFragment-onActivityCreated-------");
		mMsgBean.add(new MessageInfo(1, "张三吃饭没?", "2017-05-02"));
		mMsgBean.add(new MessageInfo(2, "控件内部内容", "2017-04-02"));
		mMsgBean.add(new MessageInfo(3, "钮上设置的内容例如图片", "2017-03-02"));
		mMsgBean.add(new MessageInfo(4, "果imageview对应的图片", "2017-02-02"));
		mMsgBean.add(new MessageInfo(5, " 不同设备显示效果相同", "2017-01-02"));
		mMsgBean.add(new MessageInfo(6, "将来的显示器类型上正常", "2017-01-01"));
		Log.e(TAG, "onActivityCreated--end-------");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log.d(TAG, "PageIndexFragment-onStart----->");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "PageIndexFragment-onresume---->");
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_MESSAGE;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "PageIndexFragment-onpause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "PageIndexFragment-onStop");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d(TAG, "PageIndexFragment-ondestoryView");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "PageIndexFragment-ondestory");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d(TAG, "PageIndexFragment-onDetach------");

	}


	private List<MessageInfo> jsonArrayToList(JSONArray ja){
		List<MessageInfo> list  = new ArrayList<MessageInfo>();
		JSONObject jo = null;
		try {
			for (int i = 0; i < ja.length(); i++) {
				jo = ja.getJSONObject(i);
				list.add(new MessageInfo(jo.optInt("noId"), jo.optString("title"),
						jo.optString("content"), jo.optString("position"), jo.optString("depart"),jo.optString("author"),
						jo.optString("createTime")));
			}
		} catch (JSONException e) {
			Log.e("-xiaomo-", "jsonArrayToList--MessageInfo",e);
		}
		return list;
	}

}
