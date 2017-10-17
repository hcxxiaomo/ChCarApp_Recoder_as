package com.xiaomo.db.model;

public class CarLicense {

	private int id;
	private String ownerName;
	private String sex;
	private int age;
	private String nativePlace;
	private String idNumber;
	private String telephone;
	private String mobilephone;
	private String email;
	private String qq;
	private String wechat;
	private String address;
	private String carNumber;
	private String carColor;
	private String framNumber;
	private String illegalType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getFramNumber() {
		return framNumber;
	}
	public void setFramNumber(String framNumber) {
		this.framNumber = framNumber;
	}
	public String getIllegalType() {
		return illegalType;
	}
	public void setIllegalType(String illegalType) {
		this.illegalType = illegalType;
	}
	public CarLicense(String ownerName, String sex, int age,
					  String nativePlace, String idNumber, String telephone,
					  String mobilephone, String email, String qq, String wechat,
					  String address, String carNumber, String carColor,
					  String framNumber, String illegalType) {
		super();
		this.ownerName = ownerName;
		this.sex = sex;
		this.age = age;
		this.nativePlace = nativePlace;
		this.idNumber = idNumber;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.qq = qq;
		this.wechat = wechat;
		this.address = address;
		this.carNumber = carNumber;
		this.carColor = carColor;
		this.framNumber = framNumber;
		this.illegalType = illegalType;
	}
	public CarLicense() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CarLicense(String ownerName, String carNumber, String illegalType) {
		super();
		this.ownerName = ownerName;
		this.carNumber = carNumber;
		this.illegalType = illegalType;
		this.sex = "男";
		this.nativePlace = "广东 深圳";
		this.idNumber = "44030319751217**50";
		this.telephone = "0755-6666666";
		this.mobilephone = "13666666666";
		this.email = "test@qq.com";
		this.qq = "1213111";
		this.wechat = "weixingTest";
		this.address = "广东省深圳市罗湖区爱国路华深大厦";
		this.carColor = "蓝色";
		this.framNumber = "0232";
	}
	@Override
	public String toString() {
		return "CarLicense [id=" + id + ", ownerName=" + ownerName + ", sex="
				+ sex + ", age=" + age + ", nativePlace=" + nativePlace
				+ ", idNumber=" + idNumber + ", telephone=" + telephone
				+ ", mobilephone=" + mobilephone + ", email=" + email + ", qq="
				+ qq + ", wechat=" + wechat + ", address=" + address
				+ ", carNumber=" + carNumber + ", carColor=" + carColor
				+ ", framNumber=" + framNumber + ", illegalType=" + illegalType
				+ "]";
	}



}
