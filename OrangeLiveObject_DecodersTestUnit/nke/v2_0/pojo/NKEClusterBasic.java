package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterBasic extends NKEFrameHeader {
//0000 Basic
	@FieldDescription(description = "Firmware Version")
	public Firmware firmware;
	@FieldDescription(description = "Kernel name")
	public String KernelVersion ;
	@FieldDescription(description = "Manufacturer name")
	public String Manufacturer;
	@FieldDescription(description = "Identify uniquely the full system with all embedded PCBs")
	public String ModelIdentifier;
	@FieldDescription(description = "Date of manufacturer of the device. Production date.")
	public String DateCode;
	@FieldDescription(description = "Information about sensor position ,sematique, GPS, etc...")
	public String LocationDescription;
	@FieldDescription(description = "Application name")
	public String ApplicationName;
	
	
	@Data
	@OutputPojo
	public class Firmware {
		@FieldDescription(description = "Application version number")
		public String version;
		@FieldDescription(description = "RCBuild is representative of the full repository revision used for build.")
		public Integer RCBuild;
	}
	
}
