package com.carOCR.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.szOCR.general.CGlobal;
import com.zed3.R;

import org.yanzi.fragment.PopupWindowCarCheckResultInfoScan;


public class PopupRecogResult implements View.OnClickListener
{

	public View parent;
	public PopupWindow mPopupWindow;

	public Button m_btnOk;
	public Button m_btn_car_number_start_compare;
	public Button m_btnCacel;
	public EditText m_txtRecogData;
	public EditText m_txtType;
	public ImageView m_RecogImageView;
	public Context con;

	//显示结果部分
	private TextView popup_silenct_canNumber;
	private TextView txt_szocr_result;
	private LinearLayout resut_list_for_show;
	private  EditText m_txtOnwerName;
	private  EditText m_txtSex;
	private  EditText m_txtAge;
	private  EditText m_txtNativePlace;
	private  EditText m_txtIdNumber;
	private  EditText m_txtTelephone;
	private  EditText m_txtMobilePhone;
	private  EditText m_txtEmail;
	private  EditText m_txtQQ;
	private  EditText m_txtWechat;
	private  EditText m_txtAddress;
	private  EditText m_txtFramNumber;
	private  EditText m_txtIllegalType;

	//数据库处理相关部分
//	private static final String DB_NAME = "t_car_license.db";
//	private MyDbHelper helper  = null;
//	private CarLicenseDao carLicenseDao = null;

	public PopupRecogResult(Context paramContext)
	{
		con = paramContext;
		parent = ((LayoutInflater)paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_silenct_recogresult, null,true);
//		m_btnOk = (Button) this.parent.findViewById(R.id.btnSignUp);
//		m_btn_car_number_start_compare = (Button) this.parent.findViewById(R.id.car_number_start_compare);
//		m_btnCacel = (Button) this.parent.findViewById(R.id.btnCancel);
//		m_txtRecogData = (EditText)this.parent.findViewById(R.id.txtItemRecogData);
//		m_txtType = (EditText)this.parent.findViewById(R.id.txtItemType);
//		m_RecogImageView = (ImageView)this.parent.findViewById(R.id.imageCar);
		mPopupWindow = new PopupWindow(this.parent,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.setOutsideTouchable(false);

		popup_silenct_canNumber =  (TextView) this.parent.findViewById(R.id.popup_silenct_canNumber);

//		m_btnOk.setOnClickListener(this);
//		m_btnCacel.setOnClickListener(this);
//		m_btn_car_number_start_compare.setOnClickListener(this);

		//显示结果部分
//		txt_szocr_result = (TextView) this.parent.findViewById(R.id.txt_szocr_result);
//		m_txtOnwerName = (EditText)this.parent.findViewById(R.id.ids_owner_name);
//		m_txtSex = (EditText)this.parent.findViewById(R.id.ids_sex);
//		m_txtAge = (EditText)this.parent.findViewById(R.id.ids_age);
//		m_txtNativePlace = (EditText)this.parent.findViewById(R.id.ids_native_place);
//		m_txtIdNumber = (EditText)this.parent.findViewById(R.id.ids_id_name);
//		m_txtTelephone = (EditText)this.parent.findViewById(R.id.ids_telephone);
//		m_txtMobilePhone = (EditText)this.parent.findViewById(R.id.ids_mobilephone);
//		m_txtEmail = (EditText)this.parent.findViewById(R.id.ids_email);
//		m_txtQQ = (EditText)this.parent.findViewById(R.id.ids_qq);
//		m_txtWechat = (EditText)this.parent.findViewById(R.id.ids_wechat);
//		m_txtAddress = (EditText)this.parent.findViewById(R.id.ids_address);
//		m_txtFramNumber = (EditText)this.parent.findViewById(R.id.ids_fram_number);
//		m_txtIllegalType = (EditText)this.parent.findViewById(R.id.ids_illegal_type);
//		resut_list_for_show = (LinearLayout)this.parent.findViewById(R.id.resut_list_for_show);

//		helper = new  MyDbHelper(con, DB_NAME, 1);
	}
	public void showAtLocation(View pView,int left,int top)
	{
		this.mPopupWindow.showAtLocation(pView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL , left, top);



		popup_silenct_canNumber.setText(CGlobal.g_RecogResult.m_szRecogTxt[0]);
//		m_txtType.setText(CGlobal.g_RecogResult.m_nRecogColor[0]);
//		m_RecogImageView.setImageBitmap(CGlobal.myEngine.getRecgBitmap());
		//m_txtLogin.setText("Device Sn:" + Utility.getMacAddress(con));
//		carLicenseDao = new CarLicenseDao(helper.getReadableDatabase());
//		 CarLicense carLicense =  carLicenseDao.getCarLicenseByCarNumber(String.valueOf(CGlobal.g_RecogResult.m_szRecogTxt[0]));
//		if (carLicense == null) {//没有违章记录下显示的效果
//			txt_szocr_result.setText("该车没有违章记录!");
//			txt_szocr_result.setTextColor(Color.BLUE);
//			resut_list_for_show.setVisibility(View.GONE);
//			CGlobal.g_mediaPlayerInformation =   MediaPlayer.create(con, R.raw.legal);
//			CGlobal.g_mediaPlayerInformation.start();
//		}else{
//
		/*	txt_szocr_result.setText("该车有违章记录!");
			txt_szocr_result.setTextColor(Color.RED);
			resut_list_for_show.setVisibility(View.VISIBLE);
			m_txtOnwerName.setText(carLicense.getOwnerName());
			m_txtIllegalType.setText(carLicense.getIllegalType());
			m_txtSex.setText(carLicense.getSex());
			m_txtAge.setText(String.valueOf(carLicense.getAge()));
			m_txtNativePlace.setText(carLicense.getNativePlace());
			m_txtIdNumber.setText(carLicense.getIdNumber());
			m_txtTelephone.setText(carLicense.getTelephone());
			m_txtMobilePhone.setText(carLicense.getMobilephone());
			m_txtEmail.setText(carLicense.getEmail());
			m_txtQQ.setText(carLicense.getQq());
			m_txtWechat.setText(carLicense.getWechat());
			m_txtAddress.setText(carLicense.getAddress());
			m_txtFramNumber.setText(carLicense.getFramNumber());*/
//			CGlobal.g_mediaPlayerInformation =   MediaPlayer.create(con, R.raw.illegal);
//			CGlobal.g_mediaPlayerInformation.start();
//		try {
//			Thread.sleep(800);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		}
	}
	public void hide()
	{
		this.mPopupWindow.dismiss();
	}
	public boolean isVisible()
	{
		return this.mPopupWindow.isShowing();
	}
	@Override
	public void onClick(View v)
	{
		Log.i("-xiaomo-", "PopupRecogResult.onClick"+v.getId());
		switch(v.getId())
		{
			case R.id.btnOk:
				hide();
				Log.i("-xiaomo-", "R.id.btnOk");
				break;
			case R.id.btnCancel:
				hide();
				Log.i("-xiaomo-", "R.id.btnCancel");
				break;
			case R.id.car_number_start_compare:
				//TODO 连接后台并把查询的数据返回到PopupWindowCarCheckResultInfoScan.java中
				PopupWindowCarCheckResultInfoScan pCarResultInfo = new PopupWindowCarCheckResultInfoScan((Activity)con);
				hide();
				pCarResultInfo.showAtLocation(parent, Gravity.TOP | Gravity.START, 0, 0);
				//保存图片到本地去
				CGlobal.carPath =  CGlobal.SaveRecogBitmap("", CGlobal.myEngine.getRecgBitmap());
				((ScanActivity)con).m_scanHandler.sendEmptyMessage(R.id.restart_preview);

//				Log.i("-xiaomo-", ((ScanActivity)con).toString());
				Log.i("-xiaomo-", "car_number_start_compare");
				break;
		}
	}



}