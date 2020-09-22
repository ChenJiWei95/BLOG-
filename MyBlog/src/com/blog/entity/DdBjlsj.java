package com.shop.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dd_bjlsj")
public class DdBjlsj {
	@Id
	private String id;
	@Column(name = "p1")
	private String p1;
	@Column(name = "p2")
	private String p2;
	@Column(name = "p3")
	private String p3;
	@Column(name = "b1")
	private String b1;
	@Column(name = "b2")
	private String b2;
	@Column(name = "b3")
	private String b3;
	@Column(name = "z1")
	private String z1;
	@Column(name = "z2")
	private String z2;
	@Column(name = "z3")
	private String z3;
	@Column(name = "x1")
	private String x1;
	@Column(name = "x2")
	private String x2;
	@Column(name = "x3")
	private String x3;
	@Column(name = "sp")
	private String sp;
	@Column(name = "jg")
	private String jg;
	@Column(name = "zdjg")
	private String zdjg;
	@Column(name = "xdjg")
	private String xdjg;
	@Column(name = "zjg")
	private String zjg;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getB1() {
		return b1;
	}
	public void setB1(String b1) {
		this.b1 = b1;
	}
	public String getB2() {
		return b2;
	}
	public void setB2(String b2) {
		this.b2 = b2;
	}
	public String getB3() {
		return b3;
	}
	public void setB3(String b3) {
		this.b3 = b3;
	}
	public String getZ1() {
		return z1;
	}
	public void setZ1(String z1) {
		this.z1 = z1;
	}
	public String getZ2() {
		return z2;
	}
	public void setZ2(String z2) {
		this.z2 = z2;
	}
	public String getZ3() {
		return z3;
	}
	public void setZ3(String z3) {
		this.z3 = z3;
	}
	public String getX1() {
		return x1;
	}
	public void setX1(String x1) {
		this.x1 = x1;
	}
	public String getX2() {
		return x2;
	}
	public void setX2(String x2) {
		this.x2 = x2;
	}
	public String getX3() {
		return x3;
	}
	public void setX3(String x3) {
		this.x3 = x3;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}
	public String getZdjg() {
		return zdjg;
	}
	public void setZdjg(String zdjg) {
		this.zdjg = zdjg;
	}
	public String getXdjg() {
		return xdjg;
	}
	public void setXdjg(String xdjg) {
		this.xdjg = xdjg;
	}
	public String getZjg() {
		return zjg;
	}
	public void setZjg(String zjg) {
		this.zjg = zjg;
	}
	public String toString(){
		return DdBjlsj.class + "["
		+ " id = " + id + ","
		+ " p1 = " + p1 + ","
		+ " p2 = " + p2 + ","
		+ " p3 = " + p3 + ","
		+ " b1 = " + b1 + ","
		+ " b2 = " + b2 + ","
		+ " b3 = " + b3 + ","
		+ " z1 = " + z1 + ","
		+ " z2 = " + z2 + ","
		+ " z3 = " + z3 + ","
		+ " x1 = " + x1 + ","
		+ " x2 = " + x2 + ","
		+ " x3 = " + x3 + ","
		+ " sp = " + sp + ","
		+ " jg = " + jg + ","
		+ " zdjg = " + zdjg + ","
		+ " xdjg = " + xdjg + ","
		+ " zjg = " + zjg 
		+"]";
	}
}

