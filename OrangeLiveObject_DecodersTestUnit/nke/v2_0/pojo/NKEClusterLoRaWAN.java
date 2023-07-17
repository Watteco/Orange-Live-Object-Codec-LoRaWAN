package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterLoRaWAN extends NKEFrameHeader {
//8004 Lorawan
	
	@FieldDescription(description = "Message type for Data Up.")
	public String messageTypeConfiguration;
	@FieldDescription(description = "Number of retry for Confirmed Data Up")
	public Integer retryConfirmedNumber;
	@FieldDescription(description = "Period in minutes without receiving any frame after what a new association is done (default: 4 days)")
	public PeriodNoFrameAfterNewAssoc periodNoFrameAfterNewAssoc;
	@FieldDescription(description = "Number of consecutive failure on a Confirmed Data Up frame after what a new association is done. (default : 100)")
	public Integer consecutiveFailureAfterNewAssoc;
	@FieldDescription(description = "Parameters of DataRate")
	public String startDataRate;
	@FieldDescription(description = "Configured ABP_DevAddr")
	public String ABP_DevAddress;
	@FieldDescription(description = "OTA_AppEUI")
	public String OTA_AppEUI;
	
	@Data
	@OutputPojo
	public class PeriodNoFrameAfterNewAssoc {
		public Integer value;
		@FieldDescription(description = "Minutes")
		public String unit;
	}
	
}
