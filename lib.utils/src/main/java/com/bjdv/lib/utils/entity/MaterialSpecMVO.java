package com.bjdv.lib.utils.entity;

import java.io.Serializable;

public class MaterialSpecMVO extends MaterialSpecSVO implements Serializable {
   
	private int amount=0;
	private int flagAmount = 0;
	
	public int getFlagAmount() {
		return flagAmount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int newValue) {
		this.amount = newValue;
		flagAmount = 1;
	}
}
