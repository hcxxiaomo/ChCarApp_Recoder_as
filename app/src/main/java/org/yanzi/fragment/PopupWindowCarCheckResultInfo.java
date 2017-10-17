package org.yanzi.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
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

public class PopupWindowCarCheckResultInfo extends PopupWindow {

	private View contentView;
	private String telStr="";
	private Long serverCarId;

	private Button ok;
	private Button show_car_owner_info_btn;
	private Button show_car_need_upload_btn;
	private TextView show_car_owner_info_txt;

	private TextView history_result_time;
	private TextView history_result_number;
	private TextView history_result_color;
	private TextView history_result_maker;
	private TextView history_result_type;
	private TextView history_result_vin;
	private TextView history_result_engine;
	private TextView history_result_str;
	private TextView history_result_carType;
	private TextView show_car_had_upload_txt;
	private TextView show_car_record_has;

	private MyDbHelper myDbHelper;
	private CarNumberInfoDao carNumberInfoDao;

	public PopupWindowCarCheckResultInfo(final Activity context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.page_car_check_result_info, null);
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
		history_result_time =  (TextView) contentView.findViewById(R.id.history_result_time);
		history_result_number =  (TextView) contentView.findViewById(R.id.history_result_number);
		history_result_color =  (TextView) contentView.findViewById(R.id.history_result_color);
		history_result_maker =  (TextView) contentView.findViewById(R.id.history_result_maker);
		history_result_type =  (TextView) contentView.findViewById(R.id.history_result_type);
		history_result_vin =  (TextView) contentView.findViewById(R.id.history_result_vin);
		history_result_engine =  (TextView) contentView.findViewById(R.id.history_result_engine);
		history_result_str =  (TextView) contentView.findViewById(R.id.history_result_str);
		history_result_carType =  (TextView) contentView.findViewById(R.id.history_result_carType);
		show_car_had_upload_txt =  (TextView) contentView.findViewById(R.id.show_car_had_upload_txt);
		show_car_record_has =  (TextView) contentView.findViewById(R.id.show_car_record_has);

		ok = (Button) contentView.findViewById(R.id.compare_button_ok);
		show_car_owner_info_btn = (Button) contentView.findViewById(R.id.show_car_owner_info_btn);
		show_car_need_upload_btn = (Button) contentView.findViewById(R.id.show_car_need_upload_btn);

		myDbHelper = new MyDbHelper(context, "db_car_number", 1);
		carNumberInfoDao = new CarNumberInfoDao(myDbHelper.getReadableDatabase());//得到dao

//	        final GlobalVaries globalStr = (GlobalVaries) context.getApplication();
//	        Log.i("-xiaomo-", globalStr.getCarString());
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupWindowCarCheckResultInfo.this.dismiss();
			}
		});

		show_car_owner_info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show_car_owner_info_txt.setVisibility(View.VISIBLE);
			}
		});

		//从数据库中查询到数据
		final CarNumberInfo ci =  carNumberInfoDao.query(CGlobal.chriId);
		//将数据库查询到的信息解析成数据信息
		history_result_time.setText("识别时间："+ ci.getCreateTime() + "    比对时间："+ci.getCompareTime());
//			show_car_owner_info_txt.setText(response.toString());
		history_result_number.setText(ci.getCarNumber());
		history_result_color.setText(ci.getCarColor());
		history_result_maker.setText(ci.getMaker());
		history_result_type.setText(ci.getType());
		history_result_vin.setText(ci.getVin());
		history_result_engine.setText(ci.getEngineNo());
		history_result_carType.setText(ci.getCarType());
		serverCarId = ci.getServerCarId();
		boolean is_leage = false;//正常车辆
		if (ci.getIsYellowCar() == 1) {
			history_result_str.append("黄标车  ");
			is_leage = true;
		}
		if (ci.getIsBlackListCar() == 1) {
			history_result_str.append("布控车  ");
			is_leage = true;
		}
		if (ci.getIsSeizedCar() == 1) {
			history_result_str.append("查封车  ");
			is_leage = true;
		}
		if (ci.getIsCheckOkCar() == 1) {
			history_result_str.append("逾期未年审车  ");
			is_leage = true;
		}
		if (ci.getIsScrappedCar() == 1) {
			history_result_str.append("报废车  ");
			is_leage = true;
		}
		if (ci.getLegalNumber() >= 1) {
			history_result_str.append("有");
			history_result_str.append(String.valueOf(ci.getLegalNumber()));
			history_result_str.append("次违法未处理");
			is_leage = true;
		}
		if (is_leage) {
			history_result_str.setTextColor(Color.RED);
		}else{
			history_result_str.append("正常车辆");
			history_result_str.setTextColor(Color.BLACK);
		}
		if (ci.getIsReported() == 1) {
			show_car_had_upload_txt.setVisibility(View.VISIBLE);
			show_car_need_upload_btn.setVisibility(View.GONE);
		}else{
			show_car_need_upload_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {//上报服务器为isRepored
					RequestParams params = new RequestParams();
					try {
						File file = new File(ci.getImg());
//			     				FileInputStream fin = new FileInputStream(file);
//			     				 byte[] bytes  = new byte[fin.available()];
						//将文件内容写入字节数组，提供测试的case
//			     		         fin.read(bytes);
						params.put("image", file);
//								byte[] result = Base64.encode(bytes, Base64.DEFAULT);
						//decode to bitmap
			                    /* Bitmap bitmap = BitmapFactory.decodeFile(ci.getImg());
			                     Log.d("-xiaomo-", "bitmap width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
			                     //convert to byte array
			                     ByteArrayOutputStream baos = new ByteArrayOutputStream();
			                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			                     byte[] bytes = baos.toByteArray();

			                     //base64 encode
			                     byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
<<<<<<< HEAD
			                     String encodeString = new String(encode);
								params.put("imageStr", encodeString);
=======
			                     String encodeString = new String(encode,"utf-8");
								params.put("imageStr", encodeString);*/
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
								show_car_need_upload_btn.setClickable(false);
							}
						}


						@Override
						public void onFailure(int statusCode, Header[] headers,
											  String responseString, Throwable throwable) {
							super.onFailure(statusCode, headers, responseString, throwable);
						}

					});
				}});
		}
		if (ci.getVideo() != null){
			show_car_record_has.setText(R.string.compare_record_play);
			show_car_record_has.setTextColor(Color.RED);
		}
		//车主信息部分
		StringBuilder sb = new StringBuilder();
		sb.append("车主姓名：")
				.append(ci.getOwner())
				.append("\n身份证号：")
				.append(ci.getOwnerId())
		;
		show_car_owner_info_txt.setText(sb.toString());

		//图片缩略图
		ImageView image=(ImageView)contentView.findViewById(R.id.car_info_pic_image_id);
		image.setImageBitmap(BitmapFactory.decodeFile(ci.getImg()));
		image.setOnClickListener(new OnClickListener() { // 点击放大
			public void onClick(View paramView) {
				LayoutInflater inflater = LayoutInflater.from(context);
				View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
				final AlertDialog dialog = new AlertDialog.Builder(context).create();
				ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
				img.setImageBitmap(BitmapFactory.decodeFile(ci.getImg()));
				//	        imageDownloader.download("图片地址",img); // 这个是加载网络图片的，可以是自己的图片设置方法
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


	}



	@Override
	public void dismiss() {
		super.dismiss();
		if (myDbHelper != null) {
			myDbHelper.close();
			Log.i("-xiaomo-", "PopupWindowCarCheckResultInfo--myDbHelper.close();");
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
