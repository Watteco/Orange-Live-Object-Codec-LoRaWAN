package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterMultiBinaryInputs extends NKEFrameHeader {
//8005 Multi Binary Inputs
	
	@FieldDescription(description = "inputs binary values")
	public BinaryValues binaryValues;
	
	@Data
	@OutputPojo
	public class BinaryValues {
		@FieldDescription(description = "input 1 value")
		public Boolean input1Value;
		@FieldDescription(description = "input 2 value")
		public Boolean input2Value;
		@FieldDescription(description = "input 3 value")
		public Boolean input3Value;
		@FieldDescription(description = "input 4 value")
		public Boolean input4Value;
		@FieldDescription(description = "input 5 value")
		public Boolean input5Value;
		@FieldDescription(description = "input 6 value")
		public Boolean input6Value;
		@FieldDescription(description = "input 7 value")
		public Boolean input7Value;
		@FieldDescription(description = "input 8 value")
		public Boolean input8Value;
		@FieldDescription(description = "input 9 value")
		public Boolean input9Value;
		@FieldDescription(description = "input 10 value")
		public Boolean input10Value;
	}

}