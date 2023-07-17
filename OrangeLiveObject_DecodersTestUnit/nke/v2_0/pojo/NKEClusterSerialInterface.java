package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterSerialInterface extends NKEFrameHeader {
//0x8006 Serial Interface
	
	@FieldDescription(description = "Speed used for the serial interface")
	public Speed speed;
	@FieldDescription(description = "Data bit value used in the Serial interface : 7 or 8")
	public Integer dataBits;
	@FieldDescription(description = "Current parity used in the serial interface : None, Odd or Even")
	public String parity;
	@FieldDescription(description = "Stop bit value for used in the serial interface : 1 or 2")
	public Integer stopBits;
	
	@Data
	@OutputPojo
	public class Speed {
		@FieldDescription(description = "Speed Value")
		public Integer value;
		@FieldDescription(description = "bits/s")
		public String unit;
	}
}
