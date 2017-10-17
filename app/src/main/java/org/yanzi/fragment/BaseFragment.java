package org.yanzi.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.text.TextUtils;

import org.yanzi.constant.Constant;



public class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";
	protected FragmentManager mFragmentManager = null;
	protected FragmentTransaction mFragmentTransaction = null;

	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "onAttach...");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate...");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView...");
//		View v = inflater.inflate(R.layout.messages_layout, container, false);
		
		return 	super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated...");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i(TAG, "onStart...");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, "onResume...");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i(TAG, "onPause...");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "onStop...");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, "onDestroyView...");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy...");
		super.onDestroy();
	}*/

	public static BaseFragment newInstance(Context context,String tag){
		BaseFragment baseFragment =  null;
		if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_CONTACTS)){
			baseFragment = new ContactsFragment();
		}else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_MESSAGE)){
//			baseFragment = new MessageFragment();
			baseFragment = new PageCarCheckFragment();
//			baseFragment = new PageIndexFragment(); //修改为当前的首页内容
		}else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_NEWS)){
			baseFragment = new NewsFragment();
		}else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_SETTING)){
			baseFragment = new SettingFragment();
//		}else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_PAGE_CAR_CHECK)){
//			baseFragment = new PageCarCheckFragment();
		}

		return baseFragment;

	}

}
