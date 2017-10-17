package org.yanzi.fragment;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.szOCR.general.CGlobal;
import com.xiaomo.db.dao.CarNumberInfoDao;
import com.xiaomo.db.model.CarNumberInfo;
import com.xiaomo.util.MyDbHelper;
import com.xiaomo.util.PageBean;
import com.zed3.R;

import org.yanzi.activity.MainActivity;
import org.yanzi.bean.CarHistoryResultInfo;
import org.yanzi.constant.Constant;
import org.yanzi.fragment.adapter.CarHistoryResultInfoAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class NewsFragment extends BaseFragment {

	/*	private Spinner illeagl_spinner_id;
        private Spinner illeagl_upload_spinner_id;*/
	private Button illeagl_started_date_id;
	private Button illeagl_end_date_id;
	private Button illegal_car_check_history_btn;
	private Button illeagl_spinner_id;
	private Button illeagl_upload_spinner_id;
	//	private ListView illegal_car_check_history_listview;
	private MainActivity mMainActivity ;
	private int year;
	private int month;
	private int day;

	private ListView mListView;
	private CarHistoryResultInfoAdapter nChAdaper;
	private List<CarHistoryResultInfo> nCarBean = new ArrayList<CarHistoryResultInfo>();

	private String type ;
	private String upload;
	private MyDbHelper myDbHelper;
	private CarNumberInfoDao carNumberInfoDao;

	//分页相关数据
	private int currentPage = 1;//当前页
	private int pageSize = 15;//每页数据量
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
		final View newsLayout = inflater.inflate(R.layout.news_layout, container,
				false);
		final CharSequence[] illeagl_item = {"全部","逾期未年审","报废车","黄标车","布控车","违法未处理" };
		final CharSequence[] illeagl_upload_item = {"全部","未上报","已上报" };
		mMainActivity = (MainActivity) getActivity();
		/*illeagl_spinner_id = (Spinner) newsLayout.findViewById(R.id.illeagl_spinner_id);
		illeagl_upload_spinner_id = (Spinner) newsLayout.findViewById(R.id.illeagl_upload_spinner_id);*/
		illeagl_spinner_id = (Button) newsLayout.findViewById(R.id.illeagl_spinner_id);
		illeagl_upload_spinner_id = (Button) newsLayout.findViewById(R.id.illeagl_upload_spinner_id);
		illeagl_started_date_id = (Button) newsLayout.findViewById(R.id.illeagl_started_date_id);
		illeagl_end_date_id = (Button) newsLayout.findViewById(R.id.illeagl_end_date_id);
		illegal_car_check_history_btn = (Button) newsLayout.findViewById(R.id.illegal_car_check_history_btn);
//		illegal_car_check_history_listview = (ListView) newsLayout.findViewById(R.id.illegal_car_check_history_listview);

		/* SpinnerAdapter adapter = new SpinnerAdapter(mMainActivity,
		            android.R.layout.simple_spinner_dropdown_item, illeagl_item);
		 illeagl_spinner_id.setAdapter(adapter);

		 SpinnerAdapter adapter_upload = new SpinnerAdapter(mMainActivity,
				 android.R.layout.simple_spinner_dropdown_item, illeagl_upload_item);
		 illeagl_upload_spinner_id.setAdapter(adapter_upload);*/

		//初始化Calendar日历对象
		Calendar mycalendar=Calendar.getInstance();

		year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
		month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
		day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
		Log.i("-xiaomo-", "当前日期:"+year+"-"+(month+1)+"-"+day); //显示当前的年月日

		myDbHelper = new MyDbHelper(mMainActivity, "db_car_number", 1);
		carNumberInfoDao = new CarNumberInfoDao(myDbHelper.getReadableDatabase());//得到dao

		//初始化一个有processBar的LinearLayout
		linearLayout = new LinearLayout(mMainActivity);
		progressBar = new ProgressBar(mMainActivity);
		linearLayout.addView(progressBar, params);
		linearLayout.setGravity(Gravity.CENTER);

		illeagl_spinner_id.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mMainActivity)
						.setTitle("请选择查询的违法类型")
						.setItems(illeagl_item, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								illeagl_spinner_id.setText(illeagl_item[which]);
								illeagl_spinner_id.setTextColor(Color.BLUE);
							}
						})
						.setPositiveButton("确定", null)
						.setNegativeButton("取消", null)
						.create()
						.show();;
			}
		});

		illeagl_upload_spinner_id.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mMainActivity)
						.setTitle("请选择是否上报")
						.setItems(illeagl_upload_item, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								illeagl_upload_spinner_id.setText(illeagl_item[which]);
								illeagl_upload_spinner_id.setTextColor(Color.BLUE);
							}
						})
						.setPositiveButton("确定", null)
						.setNegativeButton("取消", null)
						.create()
						.show();;
			}
		});



		illeagl_started_date_id.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				//创建DatePickerDialog对象
				DatePickerDialog dpd=new DatePickerDialog(mMainActivity,startedDatelistener,year,month,day);
				dpd.show();//显示DatePickerDialog组件
			}
		});
		illeagl_end_date_id.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				//创建DatePickerDialog对象
				DatePickerDialog dpd=new DatePickerDialog(mMainActivity,endDatelistener,year,month,day);
				dpd.show();//显示DatePickerDialog组件
			}
		});



		illegal_car_check_history_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				type =  illeagl_spinner_id.getText().toString();
				// TextView type =  (TextViewilleagl_spinner_id.getSelectText()).getText()
				upload =  illeagl_upload_spinner_id.getText().toString();
//				Log.i("-xiaomo- ", "- illeagl_upload_spinner_id.-" + tv.getText().toString());
//				Log.i("-xiaomo- ", "- illeagl_started_date_id.-" + illeagl_started_date_id.getText().toString());

				nCarBean = changeCniToChri(carNumberInfoDao.findCarNumberInfo(
						type,
						upload,
						illeagl_started_date_id.getText().toString(),
						illeagl_end_date_id.getText().toString(),
						new PageBean(currentPage, pageSize)));
				allCount = carNumberInfoDao.getCount(
						type,
						upload,
						illeagl_started_date_id.getText().toString(),
						illeagl_end_date_id.getText().toString()
				);
				nChAdaper = new CarHistoryResultInfoAdapter(nCarBean, mMainActivity);
				mListView.setAdapter(nChAdaper);
				mListView.setVisibility(View.VISIBLE);
				if (allCount > pageSize) {
					mListView.addFooterView(linearLayout);//要在listView.setAdapter(adapter);之前添加数据信息
					mListView.setOnScrollListener(new MyOnScrollListener());
				}

				totalPage = (allCount-1) / pageSize +1;

				Log.i("-xiaomo-", "---->"+totalPage);
			}
		});

//        final GlobalVaries globalStr = (GlobalVaries) this.getActivity().getApplication();

		mListView  =  (ListView) newsLayout.findViewById(R.id.illegal_car_check_history_listview);
//        nChAdaper = new CarHistoryResultInfoAdapter(nCarBean, mMainActivity);
//		mListView.setAdapter(nChAdaper);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
			/*	Toast.makeText(mMainActivity, nCarBean.get(position).toString(),
						Toast.LENGTH_SHORT).show();*/
				//TODO 需要增加跳转到对应的id中的数据
//				globalStr.setCarString(nCarBean.get(position).toString());

				CGlobal.chriId = nCarBean.get(position).get_id();

				PopupWindowCarCheckResultInfo bpw = new PopupWindowCarCheckResultInfo(mMainActivity);
				bpw.showAtLocation(newsLayout, Gravity.TOP | Gravity.START, 0, 0);
			}

		});

		return newsLayout;
	}

	private void appendData(){
		carNumberInfoDao = new CarNumberInfoDao(myDbHelper.getReadableDatabase());//得到dao
		List<CarHistoryResultInfo> listCarInfos = changeCniToChri(carNumberInfoDao.findCarNumberInfo(
				type,
				upload,
				illeagl_started_date_id.getText().toString(),
				illeagl_end_date_id.getText().toString(),
				new PageBean(currentPage, pageSize)));

		this.nCarBean.addAll(listCarInfos);
		nChAdaper.notifyDataSetChanged();//通知数据改变
	}

	public class MyOnScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (lastItem == nChAdaper.getCount() //划到当前listView的最底端
					&& currentPage < totalPage //当前页小于总页数 ，不是最后一页
					&& scrollState == OnScrollListener.SCROLL_STATE_IDLE //已经停止划动，不再划动
					) {
				currentPage ++;
				NewsFragment.this.appendData();//内部类调用外部类的方法
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
	/*	for (int i = 0; i < 8; i++) {
			nCarBean.add(new CarHistoryResultInfo());
		}*/

	}




	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_NEWS;
	}

	private DatePickerDialog.OnDateSetListener startedDatelistener=new DatePickerDialog.OnDateSetListener()
	{
		/**params：view：该事件关联的组件
		 * params：myyear：当前选择的年
		 * params：monthOfYear：当前选择的月
		 * params：dayOfMonth：当前选择的日
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
			//修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			year=myyear;
			month=monthOfYear;
			day=dayOfMonth;
			//更新日期
			updateDate();
		}
		//当DatePickerDialog关闭时，更新日期显示
		private void updateDate()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(year).append("-");
			if ((month+1) < 10) {
				sb.append("0").append((month+1)).append("-");
			}else{
				sb.append((month+1)).append("-");
			}
			if(day < 10){
				sb.append("0").append(day);
			}else{
				sb.append(day);
			}
			Log.i("-xiaomo-", sb.toString());
			illeagl_started_date_id.setText(sb.toString());
			illeagl_started_date_id.setTextColor(Color.BLUE);
		}
	};
	private DatePickerDialog.OnDateSetListener endDatelistener=new DatePickerDialog.OnDateSetListener()
	{
		/**params：view：该事件关联的组件
		 * params：myyear：当前选择的年
		 * params：monthOfYear：当前选择的月
		 * params：dayOfMonth：当前选择的日
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
			//修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			year=myyear;
			month=monthOfYear;
			day=dayOfMonth;
			//更新日期
			updateDate();
		}
		//当DatePickerDialog关闭时，更新日期显示
		private void updateDate()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(year).append("-");
			if ((month+1) < 10) {
				sb.append("0").append((month+1)).append("-");
			}else{
				sb.append((month+1)).append("-");
			}
			if(day < 10){
				sb.append("0").append(day);
			}else{
				sb.append(day);
			}
			Log.i("-xiaomo-", sb.toString());
			illeagl_end_date_id.setText(sb.toString());
			illeagl_end_date_id.setTextColor(Color.BLUE);
		}
	};

	private List<CarHistoryResultInfo> changeCniToChri( List<CarNumberInfo> listCni){
		List<CarHistoryResultInfo>  listChri = new LinkedList<CarHistoryResultInfo>();
		CarHistoryResultInfo chri = null;
		if (listCni != null && listCni.size() >=1) {
			for (CarNumberInfo carNumberInfo : listCni) {
				StringBuilder sb = new StringBuilder();
				chri = new CarHistoryResultInfo();
				if (carNumberInfo.getIsYellowCar() == 1) {
					sb.append("黄标车  ");
				}
				if (carNumberInfo.getIsBlackListCar() == 1) {
					sb.append("布控车  ");
				}
				if (carNumberInfo.getIsSeizedCar() == 1) {
					sb.append("查封车  ");
				}
				if (carNumberInfo.getIsCheckOkCar() == 1) {
					sb.append("逾期未年审车  ");
				}
				if (carNumberInfo.getIsScrappedCar() == 1) {
					sb.append("报废车  ");
				}
				if (carNumberInfo.getLegalNumber() >= 1) {
					sb.append("有");
					sb.append(String.valueOf(carNumberInfo.getLegalNumber()));
					sb.append("次违法未处理");
				}
				if (sb.length() > 0) {
					chri.setCompareResult(sb.toString());
				}
				if (carNumberInfo.getIsReported() == 1) {
					chri.setUpload("已上报平台存储");
				}else{
					chri.setUpload("未上报平台存储");
				}
				chri.set_id(carNumberInfo.getCarId());
				chri.setCarNumber(carNumberInfo.getCarNumber());
				chri.setImg(carNumberInfo.getImg());
				chri.setRecordFile(carNumberInfo.getVideo());
				chri.setTime(carNumberInfo.getCreateTime());
//				chri.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).format(carNumberInfo.getCreateTime()));
				listChri.add(chri);
			}
		}
		return listChri;
	}



	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i("-xiaomo-", " onDestroyView ()--myDbHelper.close();");
		if (myDbHelper != null) {
			myDbHelper.close();
		}
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}



}
