package org.yanzi.fragment.adapter;

import java.util.List;

import org.yanzi.bean.CarHistoryResultInfo;
import org.yanzi.bean.MessageInfo;
import org.yanzi.util.BitmapThumb;

import com.zed3.R;
import com.zed3.R.color;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarHistoryResultInfoAdapter   extends BaseAdapter {

	private List<CarHistoryResultInfo> mListCarHisInfo = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public CarHistoryResultInfoAdapter(
			List<CarHistoryResultInfo> mListCarHisInfo, Context mContext) {
		super();
		this.mListCarHisInfo = mListCarHisInfo;
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mListCarHisInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mListCarHisInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.page_car_history_item, null);
//		TextView idMsg = (TextView)v.findViewById(R.id.page_id_msg_item);
//		idMsg.setText(String.valueOf(mListCarHisInfo.get(position).getCarNumber()));
		TextView car_info_car_number = (TextView)v.findViewById(R.id.car_info_car_number);
		TextView car_info_create_time  = (TextView)v.findViewById(R.id.car_info_create_time);
		TextView car_info_compare_result  = (TextView)v.findViewById(R.id.car_info_compare_result);
		TextView car_info_has_video  = (TextView)v.findViewById(R.id.car_info_has_video);
		TextView car_info_has_upload  = (TextView)v.findViewById(R.id.car_info_has_upload);
		car_info_car_number.setText(mListCarHisInfo.get(position).getCarNumber());
		car_info_create_time.setText(mListCarHisInfo.get(position).getTime());
		if (mListCarHisInfo.get(position).getCompareResult() == null) {
			car_info_compare_result.setText("正常车辆");
			car_info_compare_result.setTextColor(color.color_black);
		}else{
			car_info_compare_result.setText(mListCarHisInfo.get(position).getCompareResult());
		}
		if (mListCarHisInfo.get(position).getRecordFile() == null) {
			car_info_has_video.setText("无");
		}else{
			car_info_has_video.setText(mListCarHisInfo.get(position).getRecordFile());
		}
		if ("已上报平台存储".equals(mListCarHisInfo.get(position).getUpload())) {
			car_info_has_upload.setTextColor(Color.BLACK);
		}
		car_info_has_upload.setText(mListCarHisInfo.get(position).getUpload());
		ImageView img = (ImageView) v.findViewById(R.id.car_info_pic_id);
		img.setImageBitmap(BitmapThumb.extractMiniThumb(BitmapFactory.decodeFile(mListCarHisInfo.get(position).getImg())
//				decodeResource(mContext.getResources(), R.drawable.car_info_pic)
				, 120, 160, true));
		return v;
	}

}
