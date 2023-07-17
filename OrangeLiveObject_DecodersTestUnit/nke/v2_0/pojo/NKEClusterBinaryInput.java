package com.orange.lo.decoder.js.nke.v2_0.pojo;
import java.util.List;

import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class NKEClusterBinaryInput extends NKEFrameHeader {
// 000F Binary Input
	
	@FieldDescription(description = "Current value of the counter")
	public Long counterCurrentValue;
	@FieldDescription(description = "Present binary input value of the sensor : 0 or 1")
	public String value;
	@FieldDescription(description = "Current polarity of the remote sensor : Normal or Reversed")
	public String polarity;
	@FieldDescription(description = "Current Edge selection. Values can be values: <ul><li>Falling edge</li> <li>Rising edge</li><li>Both rising and falling</li><li>Polling</li></ul>")
	public String edgeSelection;
	@FieldDescription(description = "Current debounce period in milliseconds")
	public Debounce debouncePeriod;
	
	@Data
	@OutputPojo
	public class Debounce {
		@FieldDescription(description = "Current debounce period")
		public Integer value;
		@FieldDescription(description = "milliseconds")
		public String unit;
	}

	//command 8A
	
	@FieldDescription(description = "Report parameters")
	public ReportParameters reportParameters;
	
	@Data
	@OutputPojo
	public class ReportParameters {
		@FieldDescription(description = "True if batch")
		public Boolean batch;
		@FieldDescription(description = "True if no header port (report is sent on specific port defined as first byte of next parameters)")
		public Boolean noHeaderPort;
		@FieldDescription(description = "True if secured (force confirmed with repeat)")
		public Boolean secured;
		@FieldDescription(description = "True if secured if alarm (force confirmed with repeat, only if one of fired criteria is an alarm)")
		public Boolean securedIfAlarm;
		@FieldDescription(description = "Cause request. Values can be : no, short (in the report, a short cause will be send), long (in the report, a long cause will be send) or reserved.")
		public String causeRequest;
		@FieldDescription(description = "False if use the old format")
		public Boolean newModeConfiguration;
	}
	
	@FieldDescription(description = "Criteria slot descriptor")
	public List<Criterion> criterions;
	
	@Data
	@OutputPojo
	public class Criterion {
		@FieldDescription(description = "Criteria slot descriptor")
		public CriteriaSlotDescriptor criteriaSlotDescriptor;
		@FieldDescription(description = "The indexes are defined by documentation on each attibute.")
		public Integer fieldIndex;
		@FieldDescription(description = "Value.")
		public Integer value;
		@FieldDescription(description = "Gap.")
		public Integer gap;
		@FieldDescription(description = "Number of occurences of consecutive exceeds.")
		public Integer numberOfOccurances;
		@FieldDescription(description = "Number of consecutive occurences to trig a report on Exceed.")
		public Integer occurencesHigh;
		@FieldDescription(description = "Number of consecutive occurrence to trig on Fallen.")
		public Integer occurencesLow;
	}
	
	@Data
	@OutputPojo
	public class CriteriaSlotDescriptor {
		@FieldDescription(description = "Criterion Index. 0 to 6, qualifying the 7 available criterion slots. number of slots is defined by each devices.")
		public Integer criterionIndex;
		@FieldDescription(description = "Mode. Values can be : <ul>"
				+ "<li>unused (to remove the specified slot. All other fields are not present)</li>"
				+ "<li>delta (the following bytes defined delta value on which a report will be sent)</li>"
				+ "<li>threshold (the following bytes defined threshold value on which a report will be sent)</li>"
				+ "<li>threshold with actions (the following bytes defined threshold value on which a report will be sent and actions done )</li></ul>")
		public String mode;
		@FieldDescription(description = "True if on fall")
		public Boolean onFall;
		@FieldDescription(description = "True if on exceed")
		public Boolean onExceed;
		@FieldDescription(description = "True if alarm. (The criterion is an alarm. the report will force Alarm ReportType 0x8A instead of 0x0A)")
		public Boolean alarm;
	}			
			
	
	
}
