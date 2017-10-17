package org.yanzi.fragment;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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

public class ContactsFragment extends BaseFragment {

	private static final String TAG = "MessageFragment";
	private MainActivity mMainActivity ;
	private ListView mListView;
	private MessageInfoAdapter mMsgAdapter;
	private List<MessageInfo> mMsgBean;
	private  View contactsLayout ;

	//分页相关功能
	private int currentPage = 1;//当前页
	private int pageSize = 24;//每页数据量
	private int lastItem = 0 ;//保存最后一项
	private int allCount = 0 ;//总数据量
	private int totalPage = 0 ;//总页数

	//增加一个分页加载时候对应的ProcessBar
	private LinearLayout linearLayout;//进度条组件的布局容器
	private LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private ProgressBar progressBar ;//进度条组件

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		contactsLayout = inflater.inflate(R.layout.contacts_layout,
				container, false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		mListView = (ListView)contactsLayout.findViewById(R.id.contact_listview);
		//初始化一个有processBar的LinearLayout
		linearLayout = new LinearLayout(mMainActivity);
		progressBar = new ProgressBar(mMainActivity);
		linearLayout.addView(progressBar, params);
		linearLayout.setGravity(Gravity.CENTER);

		showFirstPage();

		totalPage = (allCount-1) / pageSize +1;
		return contactsLayout;
	}

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
				allCount = response.optInt("total");
				mMsgBean = jsonArrayToList(response.optJSONArray("rows"));
				mMsgAdapter = new MessageInfoAdapter(mMsgBean, mMainActivity);
				mListView.setAdapter(mMsgAdapter);
				mListView.addFooterView(linearLayout);//要在listView.setAdapter(adapter);之前添加数据信息
				mListView.setOnScrollListener(new MyOnScrollListener());

				if (allCount <= pageSize) {
					mListView.removeFooterView(linearLayout);
				}
			}

		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
			/*	Toast.makeText(mMainActivity, mMsgBean.get(position).toString(),
						Toast.LENGTH_SHORT).show();*/

				PopupWindowNoticeInfo bpw = new PopupWindowNoticeInfo(mMainActivity,mMsgBean.get(position));
				bpw.showAtLocation(contactsLayout, Gravity.TOP | Gravity.START, 0, 0);
			}

		});
	}

	private void appendData(){
		RequestParams params = new RequestParams();
		params.put("page", String.valueOf(currentPage));
		params.put("row", String.valueOf(pageSize));
		RestClient.post("carplatenumber/carNumber/getInfoByPage", params, new JsonHttpResponseHandler(){


			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {
				Log.i("-xiaomo-", "noticePage--->:"+response);
				List<MessageInfo> list = jsonArrayToList(response.optJSONArray("rows"));
				mMsgBean.addAll(list);
				mMsgAdapter.notifyDataSetChanged();
			}


			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});
	}


	public class MyOnScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (lastItem == mMsgAdapter.getCount() //划到当前listView的最底端
					&& currentPage < totalPage //当前页小于总页数 ，不是最后一页
					&& scrollState == OnScrollListener.SCROLL_STATE_IDLE //已经停止划动，不再划动
					) {
				currentPage ++;
				ContactsFragment.this.appendData();//内部类调用外部类的方法
			}else if (currentPage == totalPage) {//如果是最后一页了，就不需要再显示这个加载信息了
				mListView.removeFooterView(linearLayout);
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
							 int visibleItemCount, int totalItemCount) {
			lastItem = firstVisibleItem + visibleItemCount -1 ;//多增加了一个ProgressBar
			/*Log.i("-xiaomo-", "firstVisibleItem="+firstVisibleItem
					+"\tvisibleItemCount="+visibleItemCount
					+"\tlastItem="+lastItem
					+"\tadapter.getCount()="+adapter.getCount()
					);*/

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*mMsgBean.add(new MessageInfo(2, "控件内部内容", "2017-04-02"));
		mMsgBean.add(new MessageInfo(3, "钮上设置的内容例如图片", "2017-03-02"));
		mMsgBean.add(new MessageInfo(4, "果imageview对应的图片", "2017-02-02"));
		mMsgBean.add(new MessageInfo(5, " 不同设备显示效果相同", "2017-01-02"));
		mMsgBean.add(new MessageInfo(6, "将来的显示器类型上正常", "2017-01-01"));
		mMsgBean.add(new MessageInfo(7, "置的内容例如图三吃饭没?", "2017-05-02"));
		mMsgBean.add(new MessageInfo(8, "备显示效果相部内容", "2017-04-02"));
		mMsgBean.add(new MessageInfo(9, "内不同设备显示容例如图片", "2017-03-02"));
		mMsgBean.add(new MessageInfo(10, "果imageview对应的图片", "2017-02-02"));
		mMsgBean.add(new MessageInfo(11, "效果相部效果相同", "2017-01-02"));
		mMsgBean.add(new MessageInfo(12, "类来的显示器", "2017-01-01"));
		mMsgBean.add(new MessageInfo(13, "类型上正常将来的显示器", "2017-01-01"));
		mMsgBean.add(new MessageInfo(14, "正常将来的显类型上示器", "2017-01-01"));
		mMsgBean.add(new MessageInfo(15, "常将来的显示器类型上正", "2017-01-01"));
		mMsgBean.add(new MessageInfo(16, "正将来的显示类型上器", "2017-01-01"));*/

	}




	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_CONTACTS;
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
