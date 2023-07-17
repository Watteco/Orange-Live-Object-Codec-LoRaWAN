package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterIlluminanceMeasurement extends NKEFrameHeader {
// O400  Illuminance Measurement
	
	@FieldDescription(description = "Measured Value in Lux")
	public Measure measure;
	
	@FieldDescription(description = "Minimum value that the remote sensor is capable to measure.")
	public Measure minimum;
	
	@FieldDescription(description = "Maximum value that the remote sensor is capable to measure.")
	public Measure maximum;
	
	@Data
	@OutputPojo
	public class Measure {
		@FieldDescription(description = "lux")
		public String unit;
		@FieldDescription(description = "Duration")
		public Float value;
	}

	
	
}
