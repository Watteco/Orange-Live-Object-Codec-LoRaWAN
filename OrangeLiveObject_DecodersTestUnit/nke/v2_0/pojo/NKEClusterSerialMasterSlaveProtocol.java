package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterSerialMasterSlaveProtocol extends NKEFrameHeader {
// 0x8007 Serial Master/Slave protocol
	
	@FieldDescription(description = "Request Frame. For ModBus Application, the CRC is not in the Frame and is calculated by the sensor.")
	public String requestFrame;

	@FieldDescription(description = "Answer Frame. For ModBus Application, the CRC has already been checked and is not in the Answer. "
			+ "When modbus tags MODBUS_8007_EPn_r_name_unit_type are not associated to the device, this field returns the encoded data.")
	public String answerFrame;
	
	@FieldDescription(description = "Application Type of the Cluster.")
	public String applicationType;

}
