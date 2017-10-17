package org.yanzi.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.zed3.R;

import org.yanzi.bean.MessageInfo;

public class PopupWindowNoticeInfo extends PopupWindow {

	private View contentView;
//	    private String telStr="";

	private TextView notice_title;
	private TextView notice_sender;
	private TextView notice_content;
	private Button ok;

	public PopupWindowNoticeInfo(final Activity context,MessageInfo massageInfo){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.page_notice_detail_info, null);
//	        int h = context.getWindowManager().getDefaultDisplay().getHeight();
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
		ok = (Button) contentView.findViewById(R.id.notice_button_ok);

		notice_title = (TextView) contentView.findViewById(R.id.notice_title);
		notice_sender = (TextView) contentView.findViewById(R.id.notice_sender);
		notice_content = (TextView) contentView.findViewById(R.id.notice_content);
//	        final GlobalVaries globalStr = (GlobalVaries) context.getApplication();
//	        Log.i("-xiaomo-", globalStr.getCarString());
		notice_content.setText(massageInfo.getContent());
		notice_title.setText(massageInfo.getTitle());

		notice_sender.setText("发布人："+massageInfo.getAuthor()
				+"   职称："+massageInfo.getPosition()+" \n  发布时间："+massageInfo.getTime()+"  部门：" +massageInfo.getDepart());

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupWindowNoticeInfo.this.dismiss();
			}
		});
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
