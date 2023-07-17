package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterSenso extends NKEFrameHeader {
//8003 Senso
	
	@FieldDescription(description = "Sensor status")
	public SensorStatus sensorStatus;
	
	@Data
	@OutputPojo
	public class SensorStatus {
		@FieldDescription(description = "Leak status")
		public String leak;
		@FieldDescription(description = "BackWaterLevel1 status")
		public String backWaterLevel1;
		@FieldDescription(description = "BackWaterLevel2 status")
		public String backWaterLevel2;
		@FieldDescription(description = "BackWaterLevel3 status")
		public String backWaterLevel3;
		@FieldDescription(description = "Fraud status")
		public String fraud;
		@FieldDescription(description = "Battery status")
		public String battery;
		@FieldDescription(description = "Installation status")
		public String installation;
		@FieldDescription(description = "Freeze status")
		public String freeze;
		
	}
	
	@FieldDescription(description = "CountDown1 Threshold (default value=10)")
	public int countdown1threshold;
	
	@FieldDescription(description = "CountDown2 Threshold (default value=100)")
	public int countdown2threshold;
	
	@FieldDescription(description = "CountDown3 Threshold (default value=1000)")
	public int countdown3threshold;
	
	@FieldDescription(description = "Installation rotation")
	public int installationRotation;
	
	@FieldDescription(description = "Volume rotation")
	public int volumeRotation;
	
	@FieldDescription(description = "TemperatureMeterFreeze")
	public Temperature temperatureMeterFreeze;
	
	@FieldDescription(description = "temperatureMinTxOff")
	public Temperature temperatureMinTxOff;
	
	@FieldDescription(description = "Volume threshold")
	public Volume volumeThreshold;
	
	@FieldDescription(description = "Period calculate average leak flow")
	public Period periodCalculateAverageLeakFlow;
	
	@FieldDescription(description = "Period observation leak flow")
	public Period periodObservationLeakFlow;
	

	
	@Data
	@OutputPojo
	public class Period {
		@FieldDescription(description = "Duration")
		public int value ;
		@FieldDescription(description = "Period unit")
		public String unit;
	}
	
	@Data
	@OutputPojo
	public class Temperature {
		@FieldDescription(description = "Temperature value")
		public int value ;
		@FieldDescription(description = "Temperature unit")
		public String unit;
	}
	
	@Data
	@OutputPojo
	public class Volume {
		@FieldDescription(description = "Volume value")
		public int value ;
		@FieldDescription(description = "Volume unit")
		public String unit;
	}
	
}
