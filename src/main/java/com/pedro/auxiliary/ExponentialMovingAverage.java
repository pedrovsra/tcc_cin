package com.pedro.auxiliary;

public class ExponentialMovingAverage {
	private double alpha;
	private Double oldValue;

	public ExponentialMovingAverage(double alpha) {
		this.alpha = alpha;
	}

	public double average(double value) {
		if (this.oldValue == null) {
			this.oldValue = value;
			return value;
		}
		double newValue = this.oldValue + this.alpha * (value - this.oldValue);
		this.oldValue = newValue;
		return newValue;
	}
}