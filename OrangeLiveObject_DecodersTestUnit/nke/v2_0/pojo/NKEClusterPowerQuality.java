package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterPowerQuality extends NKEFrameHeader {
//8052 Power Quality
	
	@FieldDescription(description = "Volume index of the Senso sensor")
	public Current current;
	
	@FieldDescription(description = "Sag half cycle threshold")
	public int sagCycleThreshold;
	
	@FieldDescription(description = "Sag Voltage Threshold")
	public Voltage sagVoltageThreshold;
	
	@FieldDescription(description = "Peak over Voltage Threshold")
	public Voltage overVoltageThreshold;
	
	@Data
	@OutputPojo
	public class Current {
		@FieldDescription(description = "Freq")
		public Freq freq;
		@FieldDescription(description = "Vrms")
		public Vrms vrms;
		@FieldDescription(description = "Vpeak")
		public Vpeak vpeak;
		@FieldDescription(description = "Voltage number")
		public int overVoltageNumber; 
		@FieldDescription(description = "Sag number")
		public int sagNumber;
		@FieldDescription(description = "Brownout number")
		public int brownoutNumber;
	}
	
	@Data
	@OutputPojo
	public class Freq {
		@FieldDescription(description = "Hz")
		public String unit;
		@FieldDescription(description = "Freq")
		public float freq;
		@FieldDescription(description = "Minimum freq")
		public float freqmin;
		@FieldDescription(description = "Maximum freq")
		public float freqmax;
	}
	
	@Data
	@OutputPojo
	public class Vrms {
		@FieldDescription(description = "V")
		public String unit;
		@FieldDescription(description = "Vrms")
		public float vrms;
		@FieldDescription(description = "Minimum vrms")
		public float vrmsmin;
		@FieldDescription(description = "Maximum vrms")
		public float vrmsmax;
	}
	
	@Data
	@OutputPojo
	public class Vpeak {
		@FieldDescription(description = "V")
		public String unit;
		@FieldDescription(description = "Vpeak")
		public float vpeak;
		@FieldDescription(description = "Minimum vpeak")
		public float vpeakmin;
		@FieldDescription(description = "Maximum vpeak")
		public float vpeakmax;
	}

	@Data
	@OutputPojo
	public class Voltage {
		@FieldDescription(description = "Volts")
		public String unit;
		public float value;
		
	}
}
