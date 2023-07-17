package com.orange.lo.decoder.js.nke.v2_0.pojo;
import java.util.List;

import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class NKEClusterDifferentialPressureMeasurement extends NKEFrameHeader {
// 8008 Differential pressure measurement
	
	@FieldDescription(description = "Differential pressure")
	public DifferentialPressure measure;
	
	@FieldDescription(description = "The Min measured value attribute contains the minimum value that the device is capable to measure.")
	public DifferentialPressure minimum;
	
	@FieldDescription(description = "The Max measured value attribute contains the maximum value that the device is capable to measure.")
	public DifferentialPressure maximum;
	
	@Data
	@OutputPojo
	public class DifferentialPressure {
		public Integer value;
		@FieldDescription(description = "Pa")
		public String unit;
	}
	
	@FieldDescription(description = "The Measurement period attribute contains the measurement period of the value that is contained in the Measured value attribute.")
	public Period measurementPeriod;
	
	@Data
	@OutputPojo
	public class Period {
		public Integer value;
		@FieldDescription(description = "Milliseconds")
		public String unit;
	}
	
	@FieldDescription(description = "The number of samples done for each measurement. The Samples per measurement attribute contains the number of samples that are averaged to get a value contained in the Measurement value attribute.")
	public Integer samplesPerMeasurement;
	
	@FieldDescription(description = "The number of confirmation samples done for each confirmed measurement.")
	public Integer samplesPerConfirmationMeasurement;
	
	@FieldDescription(description = "The Sampling period attribute contains the time between two samples or two confirmation samples used to calculate the Measured value attribute.")
	public Period samplingPeriod;
	
	@FieldDescription(description = "The Mean measured value since last report attribute contains the average of each Measured values measured at the Measurement period. It is reset when reported through a report command.")
	public DifferentialPressure meanMeasuredValueSinceLastReport;
	
	@FieldDescription(description = "The Minimal measured value since last report attribute contains the minimal value of the Measured values measured at the Measurement period. It is reset when reported through a report command.")
	public DifferentialPressure minimalMeasuredValueSinceLastReport;
	
	@FieldDescription(description = "The Maximal measured value since last report attribute contains the maximal value of the Measured values measured at the Measurement period. It is reset when reported through a report command.")
	public DifferentialPressure maximalMeasuredValueSinceLastReport;
	
	
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


