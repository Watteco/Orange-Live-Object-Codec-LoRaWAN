package com.orange.lo.decoder.js.nke.v2_0.pojo;
import java.util.List;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class NKEClusterAnalogInput extends NKEFrameHeader {
//0x000C Analog Input
	
	@FieldDescription(description = "Analog Input value of the sensor")
	public Float value;
	@FieldDescription(description = "ApplicationType of the remote sensor: carbon dioxide AI application or group analog input")
	public String applicationType;
	@FieldDescription(description = "According to the application type, unit value is PPM, mA or mV")
	public String applicationUnit;
	@FieldDescription(description = "The power duration attribute allows to change the duration of the power output. Only available on v3.5.2. Unit value is in ms")
	public PowerDuration powerDuration;
	@FieldDescription(description = "The Chock measurement parameters (Mode, Sampling Freq, Range, Threshold). Only available on v3.5.2. for Inclino sensor")
	public ChockParameters chockParameters;
	
	@Data
	@OutputPojo
	public class PowerDuration {
		@FieldDescription(description = "Power duration unit is millisecond")
		public String unit;
		@FieldDescription(description = "Power duration value")
		public Integer value;
	}
	
	@Data
	@OutputPojo
	public class ChockParameters {
		@FieldDescription(description = "Mode of chocks detection (idle, chock or click)")
		public String mode;
		@FieldDescription(description = "Sampling frequency")
		public Frequency freq;	
		@FieldDescription(description = "Range")
		public Range range;	
		@FieldDescription(description = "Threshold")
		public Threshold threshold;	
	}
	
	@Data
	@OutputPojo
	public class Frequency {
		@FieldDescription(description = "Monitoring frequency unit")
		public String unit;
		@FieldDescription(description = "Monitoring frequency value")
		public Integer value;
	}
	
	@Data
	@OutputPojo
	public class Range {
		@FieldDescription(description = "Monitoring range unit")
		public String unit;
		@FieldDescription(description = "Monitoring range value")
		public Integer value;
	}
	
	@Data
	@OutputPojo
	public class Threshold {
		@FieldDescription(description = "Threshold unit")
		public String unit;
		@FieldDescription(description = "Threshold value")
		public Integer value;
	}
	
	//command 8A signalant une alarme sur l'attribut value ('0055') de type Float
	
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
		@FieldDescription(description = "Value.")
		public Float value;
		@FieldDescription(description = "Gap.")
		public Float gap;
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
