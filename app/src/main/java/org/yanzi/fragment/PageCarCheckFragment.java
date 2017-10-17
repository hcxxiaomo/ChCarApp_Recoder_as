package org.yanzi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carOCR.activity.ScanActivity;
import com.xiaomo.db.dao.CarNumberInfoDao;
import com.xiaomo.util.MyDbHelper;
import com.zed3.R;

import org.yanzi.activity.MainActivity;
import org.yanzi.constant.Constant;

import java.util.LinkedList;

public class PageCarCheckFragment extends BaseFragment {

	private static final String TAG = "PageCarCheckFragment";
	private MainActivity mMainActivity ;
	private ImageView photo_get_phone_number;
	private ImageView video_get_phone_number;

	private MyDbHelper myDbHelper;
	private CarNumberInfoDao carNumberInfoDao;

	private TextView car_number_0_isCheckOkCar;
	private TextView car_number_0_isYellowCar;
	private TextView car_number_0_isBlackListCar;
	private TextView car_number_0_legalNumber;
	private TextView car_number_0_isScrappedCar;
	private TextView car_number_0_total;

	private TextView car_number_7_isCheckOkCar;
	private TextView car_number_7_isYellowCar;
	private TextView car_number_7_isBlackListCar;
	private TextView car_number_7_legalNumber;
	private TextView car_number_7_isScrappedCar;
	private TextView car_number_7_total;

	private TextView car_number_15_isCheckOkCar;
	private TextView car_number_15_isYellowCar;
	private TextView car_number_15_isBlackListCar;
	private TextView car_number_15_legalNumber;
	private TextView car_number_15_isScrappedCar;
	private TextView car_number_15_total;

	private TextView car_number_30_isCheckOkCar;
	private TextView car_number_30_isYellowCar;
	private TextView car_number_30_isBlackListCar;
	private TextView car_number_30_legalNumber;
	private TextView car_number_30_isScrappedCar;
	private TextView car_number_30_total;

	//	private ListView mListView;
//	private MessageInfoAdapter mMsgAdapter;
//	private List<MessageInfo> mMsgBean = new ArrayList<MessageInfo>();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.page_check_car_layout,
				container, false);
		Log.d(TAG, "onCreateView---->");
		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		photo_get_phone_number = (ImageView) messageLayout.findViewById(R.id.photo_get_phone_number);
		video_get_phone_number = (ImageView) messageLayout.findViewById(R.id.video_get_phone_number);

		myDbHelper = new MyDbHelper(mMainActivity, "db_car_number", 1);
		carNumberInfoDao = new CarNumberInfoDao(myDbHelper.getWritableDatabase());//得到dao

		photo_get_phone_number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mMainActivity, ScanActivity.class);
				mMainActivity.startActivity(intent);
			}
		});

		car_number_0_isCheckOkCar = (TextView) messageLayout.findViewById(R.id.car_number_0_isCheckOkCar);
		car_number_0_isYellowCar = (TextView) messageLayout.findViewById(R.id.car_number_0_isYellowCar);
		car_number_0_isBlackListCar = (TextView) messageLayout.findViewById(R.id.car_number_0_isBlackListCar);
		car_number_0_legalNumber = (TextView) messageLayout.findViewById(R.id.car_number_0_legalNumber);
		car_number_0_isScrappedCar = (TextView) messageLayout.findViewById(R.id.car_number_0_isScrappedCar);
		car_number_0_total = (TextView) messageLayout.findViewById(R.id.car_number_0_total);

		car_number_7_isCheckOkCar = (TextView) messageLayout.findViewById(R.id.car_number_7_isCheckOkCar);
		car_number_7_isYellowCar = (TextView) messageLayout.findViewById(R.id.car_number_7_isYellowCar);
		car_number_7_isBlackListCar = (TextView) messageLayout.findViewById(R.id.car_number_7_isBlackListCar);
		car_number_7_legalNumber = (TextView) messageLayout.findViewById(R.id.car_number_7_legalNumber);
		car_number_7_isScrappedCar = (TextView) messageLayout.findViewById(R.id.car_number_7_isScrappedCar);
		car_number_7_total = (TextView) messageLayout.findViewById(R.id.car_number_7_total);

		car_number_15_isCheckOkCar = (TextView) messageLayout.findViewById(R.id.car_number_15_isCheckOkCar);
		car_number_15_isYellowCar = (TextView) messageLayout.findViewById(R.id.car_number_15_isYellowCar);
		car_number_15_isBlackListCar = (TextView) messageLayout.findViewById(R.id.car_number_15_isBlackListCar);
		car_number_15_legalNumber = (TextView) messageLayout.findViewById(R.id.car_number_15_legalNumber);
		car_number_15_isScrappedCar = (TextView) messageLayout.findViewById(R.id.car_number_15_isScrappedCar);
		car_number_15_total = (TextView) messageLayout.findViewById(R.id.car_number_15_total);

		car_number_30_isCheckOkCar = (TextView) messageLayout.findViewById(R.id.car_number_30_isCheckOkCar);
		car_number_30_isYellowCar = (TextView) messageLayout.findViewById(R.id.car_number_30_isYellowCar);
		car_number_30_isBlackListCar = (TextView) messageLayout.findViewById(R.id.car_number_30_isBlackListCar);
		car_number_30_legalNumber = (TextView) messageLayout.findViewById(R.id.car_number_30_legalNumber);
		car_number_30_isScrappedCar = (TextView) messageLayout.findViewById(R.id.car_number_30_isScrappedCar);
		car_number_30_total = (TextView) messageLayout.findViewById(R.id.car_number_30_total);

		//数据库进行字段的查询功能
		//select sum(is_legal_car),sum(is_yellow_car),sum(is_blacklist_car),sum(is_seized_car),sum(is_checkok_car),sum(is_scrapped_car)
		LinkedList<Integer> list_0 = carNumberInfoDao.getCarInfoTime(0);//当天数据
		car_number_0_legalNumber.append(list_0.get(0).toString()+"辆");
		car_number_0_isYellowCar.append(list_0.get(1).toString()+"辆");
		car_number_0_isBlackListCar.append(list_0.get(2).toString()+"辆");
		car_number_0_isCheckOkCar.append(list_0.get(4).toString()+"辆");
		car_number_0_isScrappedCar.append(list_0.get(5).toString()+"辆");
		car_number_0_total.append((list_0.get(0)+list_0.get(1)+list_0.get(2)+list_0.get(4)+list_0.get(5))+"");
		LinkedList<Integer> list_7 = carNumberInfoDao.getCarInfoTime(7);//7天数据
		car_number_7_legalNumber.append(list_7.get(0).toString()+"辆");
		car_number_7_isYellowCar.append(list_7.get(1).toString()+"辆");
		car_number_7_isBlackListCar.append(list_7.get(2).toString()+"辆");
		car_number_7_isCheckOkCar.append(list_7.get(4).toString()+"辆");
		car_number_7_isScrappedCar.append(list_7.get(5).toString()+"辆");
		car_number_7_total.append((list_7.get(0)+list_7.get(1)+list_7.get(2)+list_7.get(4)+list_7.get(5))+"");
		LinkedList<Integer> list_15 = carNumberInfoDao.getCarInfoTime(15);//15天数据
		car_number_15_legalNumber.append(list_15.get(0).toString()+"辆");
		car_number_15_isYellowCar.append(list_15.get(1).toString()+"辆");
		car_number_15_isBlackListCar.append(list_15.get(2).toString()+"辆");
		car_number_15_isCheckOkCar.append(list_15.get(4).toString()+"辆");
		car_number_15_isScrappedCar.append(list_15.get(5).toString()+"辆");
		car_number_15_total.append((list_15.get(0)+list_15.get(1)+list_15.get(2)+list_15.get(4)+list_15.get(5))+"");
		LinkedList<Integer> list_30 = carNumberInfoDao.getCarInfoTime(30);//30天数据
		car_number_30_legalNumber.append(list_30.get(0).toString()+"辆");
		car_number_30_isYellowCar.append(list_30.get(1).toString()+"辆");
		car_number_30_isBlackListCar.append(list_30.get(2).toString()+"辆");
		car_number_30_isCheckOkCar.append(list_30.get(4).toString()+"辆");
		car_number_30_isScrappedCar.append(list_30.get(5).toString()+"辆");
		car_number_30_total.append((list_30.get(0)+list_30.get(1)+list_30.get(2)+list_30.get(4)+list_30.get(5))+"");

		return messageLayout;
	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d(TAG, "onAttach-----");

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate------");
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
		Log.d(TAG, "onActivityCreated-------");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log.d(TAG, "onStart----->");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onresume---->");
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_MESSAGE;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onpause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();


	}

	@Override
	public void onDestroyView() {
		if (myDbHelper != null) {
			myDbHelper.close();
		}
		Log.d(TAG, "onStop");
		super.onDestroyView();
		Log.d(TAG, "ondestoryView");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "ondestory");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d(TAG, "onDetach------");

	}



}
