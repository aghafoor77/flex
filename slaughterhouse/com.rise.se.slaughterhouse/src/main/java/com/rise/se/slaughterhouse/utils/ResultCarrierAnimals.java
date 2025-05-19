package com.rise.se.slaughterhouse.utils;

import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseData;

public class ResultCarrierAnimals {
	private RSlaughterhouseData rSlaughterhouseData;
	private RCarrierSlaughterhouseData rCarrierSlaughterhouseData;
	private RAnimalSlaughterhouseData rAnimalSlaughterhouseData;
	public RSlaughterhouseData getrSlaughterhouseData() {
		return rSlaughterhouseData;
	}
	public void setrSlaughterhouseData(RSlaughterhouseData rSlaughterhouseData) {
		this.rSlaughterhouseData = rSlaughterhouseData;
	}
	public RCarrierSlaughterhouseData getrCarrierSlaughterhouseData() {
		return rCarrierSlaughterhouseData;
	}
	public void setrCarrierSlaughterhouseData(RCarrierSlaughterhouseData rCarrierSlaughterhouseData) {
		this.rCarrierSlaughterhouseData = rCarrierSlaughterhouseData;
	}
	public RAnimalSlaughterhouseData getrAnimalSlaughterhouseData() {
		return rAnimalSlaughterhouseData;
	}
	public void setrAnimalSlaughterhouseData(RAnimalSlaughterhouseData rAnimalSlaughterhouseData) {
		this.rAnimalSlaughterhouseData = rAnimalSlaughterhouseData;
	}
}
