package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterMultiMasterSlaveAnswers extends NKEFrameHeader {
//0x8009 (only v3.5.0.4740) Multi Master/Slave answers	

	@FieldDescription(description = "Current Multi answers frame got in the last Master/Slaves exchanges, "
			+ "aggregated, with or without ModBus headers, according to the tag MODBUS_2009_WITHOUT_HEADER "
			+ "associated to the device. When modbus tags MODBUS_8009_EPn_r_name_unit_type are not associated to the device, this field returns the encoded data.")
	public String presentValue;
	
	@FieldDescription(description = "Current Header option of the cluster. "
			+ "True (default value) when all the ModBus answers' headers are kept in the Present value reports. "
			+ "False when the ModBus answers headers are not kept in order to optimize the Present value report frame size.")
	public Boolean headerOption;
	
	

}
