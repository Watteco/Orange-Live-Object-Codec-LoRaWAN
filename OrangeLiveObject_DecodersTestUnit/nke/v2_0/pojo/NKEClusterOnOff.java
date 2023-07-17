package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterOnOff extends NKEFrameHeader {
// 0006 ON/OFF
	
	@FieldDescription(description = "OnOFF relay state. Values can be ON or OFF")
	public String relayState;
}
