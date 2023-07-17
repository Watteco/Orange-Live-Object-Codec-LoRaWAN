package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterSimpleMetering extends NKEFrameHeader {
//0052 Simple Metering Like
	
	@FieldDescription(description = "Summation of the active energy in W.h")
	public EnergySummation activeEnergySum;
	@FieldDescription(description = "Summation of the reactive energy in VAR.h")
	public EnergySummation reactiveEnergySum;
	@FieldDescription(description = "Number of sample")
	public Float sampleNumber;
	@FieldDescription(description = "Active power in W")
	public ActivePower activePower;
	@FieldDescription(description = "Reactive power in VAR")
	public ReactivePower reactivePower;
	@FieldDescription(description = "Current e2Pot value")
	public Float currentE2Pot;
	@FieldDescription(description = "Active power multiplier")
	public Float activePowerMultiplier;
	@FieldDescription(description = "Reactive power multiplier")
	public Float reactivePowerMultiplier;
	@FieldDescription(description = "Active power divisor")
	public Float activePowerDivisor;
	@FieldDescription(description = "Reactive power divisor")
	public Float reactivePowerDivisor;
	

	@Data
    @OutputPojo
	public class EnergySummation {
		public Float value;
		public String unit;
	}
	
	@Data
    @OutputPojo
	public class ActivePower {
		@FieldDescription(description = "Active power")
		public Float value;
		@FieldDescription(description = "W")
		public String unit;
	}

	@Data
    @OutputPojo
	public class ReactivePower {
		@FieldDescription(description = "Reactive power")
		public Float value;
		@FieldDescription(description = "VAR")
		public String unit;
	}	
	
}
