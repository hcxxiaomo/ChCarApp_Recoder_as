package org.yanzi.constant;

import android.app.Application;

public class GlobalVaries extends Application {

	private String carString;

	public String getCarString() {
		return carString;
	}

	public void setCarString(String carString) {
		this.carString = carString;
	}

	@Override
	public void onCreate() {
		carString = "";
		super.onCreate();
	}
	
	
	
}
