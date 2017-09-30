package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

public class BalanceCollect implements Serializable {

    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 3377948815770116312L;
	
	private DecimalFormat df= new DecimalFormat(".00");
	
	private double earn;
	
	private double earnNofee;
	
	private double bonus;
	
	@SuppressWarnings("unused")
	private double fee;
	
	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public double getEarn() {
		return earn;
	}

	public void setEarn(double earn) {
		this.earn = earn;
	}

	public double getEarnNofee() {
		return earnNofee;
	}

	public void setEarnNofee(double earnNofee) {
		this.earnNofee = earnNofee;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	public double getFee() {
		return Double.valueOf(df.format(earn + bonus - earnNofee));
	}

	public void setFee(double fee) {
		this.fee = fee;
	}
}
