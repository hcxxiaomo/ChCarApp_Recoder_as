package com.xiaomo.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiaomo.db.model.CarLicense;

public class CarLicenseDao {

	
	private SQLiteDatabase db = null;

	public CarLicenseDao(SQLiteDatabase db) {
		super();
		this.db = db;
	}
	
	public void addCarLicense(CarLicense carLicense){
		String sql = "insert into t_car_license values ( null, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ? )";
		Object[] args = new Object[]{
				carLicense.getOwnerName(),
				carLicense.getSex(),
				carLicense.getAge(),
				carLicense.getNativePlace(),
				carLicense.getIdNumber(),
				carLicense.getTelephone(),
				carLicense.getMobilephone(),
				carLicense.getEmail(),
				carLicense.getQq(),
				carLicense.getWechat(),
				carLicense.getAddress(),
				carLicense.getCarNumber(),
				carLicense.getCarColor(),
				carLicense.getFramNumber(),
				carLicense.getIllegalType()
		};
		db.execSQL(sql,args);
		Log.i("---xiaomo---", "insert-->" + carLicense.toString());
		db.close();
	}
	
	public CarLicense getCarLicenseByCarNumber(String carNumber){
		String sql = "select id,owner_name,sex,age,native_place,id_number,telephone,mobilephone,email,qq,wechat,address,car_number,car_color,fram_number,illegal_type from t_car_license where car_number = ? ";
		String[] args = new String[]{
			carNumber
		};
		CarLicense carLicense = null;
		Cursor result =  db.rawQuery(sql, args);
		for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
			 carLicense = new CarLicense(result.getString(1), result.getString(2), result.getInt(3), 
					 result.getString(4), result.getString(5), result.getString(6), 
					 result.getString(7), result.getString(8), result.getString(9), 
					 result.getString(10), result.getString(11), result.getString(12), 
					 result.getString(13), result.getString(14), result.getString(15));
		}
		if (carLicense != null) {
			Log.i("---xiaomo---",  "get-->" + carLicense.toString());
		}
		db.close();
		return carLicense;
	}
	
	public List<String> getAllCarLicense(){
		List<String> all = new ArrayList<String>();
		String sql = "select owner_name,car_number,t_car_license from t_car_license";
		Cursor result =  db.rawQuery(sql, null);
		for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
			all.add(result.getInt(0)+","+result.getString(1)+","+result.getInt(2));
		}
		db.close();
		return all;
	}
}
