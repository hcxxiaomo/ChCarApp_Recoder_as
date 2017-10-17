package org.yanzi.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.carOCR.activity.ScanActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.szOCR.general.CGlobal;
import com.xiaomo.db.dao.CarNumberInfoDao;
import com.xiaomo.db.model.CarNumberInfo;
import com.xiaomo.util.MyDbHelper;
import com.xiaomo.util.RestClient;
import com.zed3.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PopupWindowCarCheckResultInfoScan extends PopupWindow {

	private View contentView;
	private Long serverCarId;
	private Activity context;
	private Button ok;
	//	    private Button compare_butto_ok;
	private Button result_scan_car_upload_image;
	private Button show_car_owner_info_btn;
	private TextView show_car_owner_info_txt;
	private TextView result_scan_car_number;
	private TextView result_scan_car_carType;
	private TextView result_scan_car_color;
	private TextView result_scan_car_maker;
	private TextView result_scan_car_type;
	private TextView result_scan_car_vin;
	private TextView result_scan_car_engine;
	private TextView result_scan_car_legal;
	private TextView result_scan_car_cctime;
	//	    private TextView result_scan_car_serverId;
	private ImageView image;

	private MyDbHelper myDbHelper;
	private CarNumberInfoDao carNumberInfoDao;

	private SharedPreferences sp;


	public PopupWindowCarCheckResultInfoScan(final Activity context){
		this.context = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.page_car_check_result_info_scan, null);
		//int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(contentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
//	         this.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(android.R.style.Animation_Dialog);

		show_car_owner_info_txt =  (TextView) contentView.findViewById(R.id.show_car_owner_info_txt);
		result_scan_car_number =  (TextView) contentView.findViewById(R.id.result_scan_car_number);
		result_scan_car_color =  (TextView) contentView.findViewById(R.id.result_scan_car_color);
		result_scan_car_carType =  (TextView) contentView.findViewById(R.id.result_scan_car_carType);
		result_scan_car_maker =  (TextView) contentView.findViewById(R.id.result_scan_car_maker);
		result_scan_car_type =  (TextView) contentView.findViewById(R.id.result_scan_car_type);
		result_scan_car_vin =  (TextView) contentView.findViewById(R.id.result_scan_car_vin);
		result_scan_car_engine =  (TextView) contentView.findViewById(R.id.result_scan_car_engine);
		result_scan_car_legal =  (TextView) contentView.findViewById(R.id.result_scan_car_legal);
		result_scan_car_cctime =  (TextView) contentView.findViewById(R.id.result_scan_car_cctime);

		result_scan_car_upload_image = (Button) contentView.findViewById(R.id.result_scan_car_upload_image);
		ok = (Button) contentView.findViewById(R.id.compare_button_ok);
		show_car_owner_info_btn = (Button) contentView.findViewById(R.id.show_car_owner_info_btn);

//	        car_info_pic_image_id = (ImageView) contentView.findViewById(R.id.car_info_pic_image_id);
//	        final GlobalVaries globalStr = (GlobalVaries) context.getApplication();
//	        Log.i("-xiaomo-", globalStr.getCarString());
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupWindowCarCheckResultInfoScan.this.dismiss();
				//下面这两行代码是允许查车时调用的代码
				((ScanActivity)context).m_bShowPopupResult = false;
				((ScanActivity)context).m_scanHandler.sendEmptyMessage(R.id.restart_preview);
			}
		});

		show_car_owner_info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show_car_owner_info_txt.setVisibility(View.VISIBLE);
			}
		});

		result_scan_car_upload_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//上报服务器为isRepored
				RequestParams params = new RequestParams();
				try {
					File file = new File(CGlobal.carPath);
					params.put("image", file);
	     			/*	  Bitmap bitmap = BitmapFactory.decodeFile(CGlobal.carPath);
		                     Log.d("-xiaomo-", "bitmap width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
		                     //convert to byte array
		                     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		                     byte[] bytes = baos.toByteArray();

		                     //base64 encode
		                     byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
		                     String encodeString = new String(encode,"utf-8");
							params.put("imageStr", encodeString);*/
				} catch (NullPointerException e) {
					Toast.makeText(context, "图片不存在，操作停止！", Toast.LENGTH_LONG).show();
					Log.e("-xiaomo-", "FileNotFoundException e",e);
					return ;
				} catch (FileNotFoundException e) {
					Toast.makeText(context, "图片不存在，操作停止！", Toast.LENGTH_LONG).show();
					Log.e("-xiaomo-", "FileNotFoundException e",e);
					return ;
				}
				params.put("serverCarId", String.valueOf(serverCarId));
				RestClient.post("carplatenumber/carNumber/uploadImageNew", params, new JsonHttpResponseHandler(){


					public void onSuccess(int statusCode, Header[] headers,
										  JSONObject response) {
						Log.i("-xiaomo-", "uploadImage-success:"+response);
						if (response.optInt("upload") == 1) {//上传成功-- 数据库状态修改
							carNumberInfoDao.updateIsReported(serverCarId);
							Toast.makeText(context, "图片上传平台存储成功！", Toast.LENGTH_LONG).show();
							result_scan_car_upload_image.setClickable(false);
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
										  String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
					}

				});
			}
		});

		//图片缩略图
		image=(ImageView)contentView.findViewById(R.id.car_info_pic_image_id);
		image.setImageBitmap(CGlobal.myEngine.getRecgBitmap());
		image.setOnClickListener(new OnClickListener() { // 点击放大
			public void onClick(View paramView) {
				LayoutInflater inflater = LayoutInflater.from(context);
				View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
				final AlertDialog dialog = new AlertDialog.Builder(context).create();
				ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
				img.setImageBitmap(CGlobal.myEngine.getRecgBitmap());
				//imageDownloader.download("图片地址",img); // 这个是加载网络图片的，可以是自己的图片设置方法
				dialog.setView(imgEntryView); // 自定义dialog
				dialog.show();
				// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
				imgEntryView.setOnClickListener(new OnClickListener() {
					public void onClick(View paramView) {
						dialog.cancel();
					}
				});
			}
		});

		myDbHelper = new MyDbHelper(context, "db_car_number", 1);
		carNumberInfoDao = new CarNumberInfoDao(myDbHelper.getWritableDatabase());//得到dao

		sp = context.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);

		//联网开始查询数据信息
		RequestParams params = new RequestParams();
		params.put("typeHpzl", CGlobal.carType);
		Log.i("-xiaomo-", "CGlobal.carType = "+CGlobal.carType);
		params.put("carNumberHpzm", CGlobal.g_RecogResult.m_szRecogTxt[0]);
//			params.put("create_time",  DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(new Date()));
		params.put("reportPoliceId", sp.getString("policeId", "001"));
		params.put("reportPoliceName", sp.getString("name", "姓名"));
		params.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(new Date()));
//			Log.i("-xiaomo-", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(new Date()));
		RestClient.post("carplatenumber/carNumber/getCarInfo", params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {
				Log.i("-xiaomo-", response.toString());
				CarNumberInfo cni = new CarNumberInfo();
				//识别时间：2017-04-23 12:12:12    比对时间：2017-04-23 12:13:15
				/*	try {
						cni.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).parse(response.optString("createTime")));
						cni.setCompareTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).parse(response.optString("compareTime")));
					} catch (ParseException e) {
						cni.setCreateTime(new Date());
						cni.setCompareTime(new Date());
					}*/
				cni.setCreateTime(response.optString("createTime"));
				cni.setCompareTime(response.optString("compareTime"));
				cni.setCarColor(response.optString("colorCsys"));
				cni.setCarNumber(CGlobal.g_RecogResult.m_szRecogTxt[0]);
				cni.setEngineNo(response.optString("engineNumberFdjh"));
				cni.setImg(CGlobal.carPath);
				cni.setMaker(response.optString("brandClpp1"));
				cni.setType(response.optString("typeClxh"));
				cni.setVin(response.optString("vinClsbdh"));
				cni.setCarType(response.optString("carType"));

				result_scan_car_cctime.setText("识别时间："+ response.optString("createTime") + "    比对时间："+response.optString("compareTime"));
//					show_car_owner_info_txt.setText(response.toString());
				result_scan_car_number.setText(CGlobal.g_RecogResult.m_szRecogTxt[0]);
				result_scan_car_color.setText(response.optString("colorCsys"));
				result_scan_car_maker.setText(response.optString("brandClpp1"));
				result_scan_car_type.setText(response.optString("typeClxh"));
				result_scan_car_carType.setText(response.optString("carType"));
				result_scan_car_vin.setText(response.optString("vinClsbdh"));
				result_scan_car_engine.setText(response.optString("engineNumberFdjh"));
				serverCarId = response.optLong("serverCarId");
				boolean is_leage = false;//正常车辆
				if (response.optInt("isYellowCar") == 1) {
					result_scan_car_legal.append("黄标车  ");
					cni.setIsBlackListCar(1);
					is_leage = true;
				}
				if (response.optInt("isBlackListCar") == 1) {
					result_scan_car_legal.append("布控车  ");
					cni.setIsBlackListCar(1);
					is_leage = true;
				}
				if (response.optInt("isSeizedCar") == 1) {
					result_scan_car_legal.append("查封车  ");
					cni.setIsSeizedCar(1);
					is_leage = true;
				}
				if (response.optInt("isCheckOkCar") == 1) {
					result_scan_car_legal.append("逾期未年审车  ");
					cni.setIsCheckOkCar(1);
					is_leage = true;
				}
				if (response.optInt("isScrappedCar") == 1) {
					result_scan_car_legal.append("报废车  ");
					is_leage = true;
				}
				if (response.optInt("legalNumber") >= 1) {
					result_scan_car_legal.append("有");
					result_scan_car_legal.append(String.valueOf(response.optInt("legalNumber")));
					result_scan_car_legal.append("次违法未处理");
					cni.setIsLegalCar(1);
					cni.setLegalNumber(response.optInt("legalNumber"));
					is_leage = true;
				}
				if (is_leage) {
					result_scan_car_legal.setTextColor(Color.RED);
				}else{
					result_scan_car_legal.append("正常车辆");
					result_scan_car_legal.setTextColor(Color.BLACK);
				}
				//车主信息部分
				cni.setOwner(response.optString("ownerSyr"));
				cni.setOwnerId(response.optString("ownerId"));
				StringBuilder sb = new StringBuilder();
				sb.append("车主姓名：")
						.append(response.optString("ownerSyr"))
						.append("\n身份证号：")
						.append(response.optString("ownerId"))
				;
				show_car_owner_info_txt.setText(sb.toString());
				cni.setServerCarId(response.optLong("serverCarId"));
				cni.setIsReported(0);
				Log.i("-xiaomo-", cni.toString());

				//把数据保存到数据库中去
				carNumberInfoDao.insertCarNumber(cni, sp.getString("policeId", "001"), sp.getString("name", "姓名"));
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}


		});
		Log.i("-xiaomo-", "end--RestClient.post");
	}



	@Override
	public void dismiss() {
		super.dismiss();
		//下面这两行代码是允许查车时调用的代码
		((ScanActivity)context).m_bShowPopupResult = false;
		((ScanActivity)context).m_scanHandler.sendEmptyMessage(R.id.restart_preview);
		Log.i("-xiaomo-", "end--dismiss");
		//关闭数据库
		if (myDbHelper != null) {
			myDbHelper.close();
		}
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAsDropDown(parent, parent.getLayoutParams().width , 0);
		} else {
			this.dismiss();
		}
	}

}
