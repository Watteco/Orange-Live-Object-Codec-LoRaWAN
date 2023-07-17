package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterMultiStateOutput extends NKEFrameHeader {
//0x0013 Multistate Output
	
	@FieldDescription(description = "Number of states.")
	public int numberOfStates;
	
	@FieldDescription(description = "Application type. Values can be défault or Pilot wire.")
	public String applicationType;

	@FieldDescription(description = "Current present value.")
	public int presentValue;

}
