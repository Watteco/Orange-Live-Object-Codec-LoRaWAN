package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEFrameHeader {
	@FieldDescription(description = "Different clusterIDs may share a common endpoint number but two instances of a given clusterID must have different endpoint numbers")	
	public String endpoint;
	@FieldDescription(description = "Command response type")	
	public String commandId;
	@FieldDescription(description = "Cluster")
	public String messageType;
	@FieldDescription(description = "Attribut in cluster")
	public String attributId;
	@FieldDescription(description = "If not Ok, command responses can return error codes values: <ul><li>Malformed Command</li> <li>Unsupported Cluster Command</li> <li>Unsupported General Command</li> <li>Unsupported Attribute</li> <li>Invalid Field</li> <li>Invalid Value</li> <li>Insufficient Space</li> <li>Unreportable Attribute</li> <li>Batch Report: No Free Slot</li> <li>Batch Report: Invalid Tag Size</li> <li>Batch Report: Duplicate Tag Label</li> <li>Batch Report: Label Out Of Range</li> </ul>")
	public String status;
	@FieldDescription(description = "Standard or batch report")
	public String report;
}
