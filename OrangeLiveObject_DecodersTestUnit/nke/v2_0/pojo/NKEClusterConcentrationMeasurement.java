package com.orange.lo.decoder.js.nke.v2_0.pojo;
import java.util.List;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class NKEClusterConcentrationMeasurement extends NKEFrameHeader {
//800C ConcentrationMeasurement

	@FieldDescription(description = "Last measured value data for CO2 or COV sensors.")
	public Measure measure;
	
	@Data
	@OutputPojo
	public class Measure {
		@FieldDescription(description = "Measure value")
		public Integer value;
		@FieldDescription(description = "Measure unit: can be ppm for a CO2 sensor, or Indoor Air Quality range of 0 Best to 500 Worst for a VOC sensor.")
		public String unit;
	}
	
	@FieldDescription(description = "Mean value of CO2 or VOC sensor since sensor startup or last Reset statistics cluster specific command.")
	public Measure mean;
	@FieldDescription(description = "Minimal value of CO2 or VOC sensor since sensor startup or last Reset statistics cluster specific command.")
	public Measure minimum;
	@FieldDescription(description = "Maximum value of CO2 or VOC sensor since sensor startup or last Reset statistics cluster specific command.")
	public Measure maximum;
	
	@FieldDescription(description = "Current classification level. Values can be <ul>"
			+ "<li>EXCELLENT</li>"
			+ "<li>GOOD</li>"
			+ "<li>AVERAGE</li>"
			+ "<li>BAD</li>"
			+ "<li>VERY BAD</li>"
			+ "<li>UNHEALTHY</li>"
			+ "</ul>")
	public String currentClassificationLevel;
	
	@FieldDescription(description = "Units managed by the corresponding sensor. Values can be <ul>"
			+ "<li>Unit is undefined</li>"
			+ "<li>Parts per 10</li>"
			+ "<li>Parts per 100</li>"
			+ "<li>Parts per 1000</li>"
			+ "<li>Parts per Million</li>"
			+ "<li>Parts per Billion</li>"
			+ "<li>Parts par Trillion</li>"
			+ "<li>Parts par Quintillion</li>"
			+ "<li>0 to 500 Indicator (IAQ)</li>"
			+ "</ul>")
	public String concentrationUnit;

	@FieldDescription(description = "HMI Display period measurement in seconds.")
	public Period hmiDisplayPeriod;
	
	@Data
	@OutputPojo
	public class Period {
		@FieldDescription(description = "Duration")
		public Integer value;
		@FieldDescription(description = "Time unit")
		public String unit;
	}

	
	@FieldDescription(description = "Minimal/Normal concentration level (ppm or IAQ) Mainly used for CO2. Typical outdoor value is between 400 and 420 ppm.")
	public Measure minNormalLevel;
	
	@FieldDescription(description = "Calibration period (Day) Mainly used for CO2 ABC calibration in days. No Autocalibration if 0.")
	public Period calibrationPeriod;

	@FieldDescription(description = "Current calibration coefficients.")
	public Coefficient calibrationCoefficients;	
	@Data
	@OutputPojo
	public class Coefficient {
		@FieldDescription(description = "Excellent up to.")
		public Integer excellentUpTo;
		@FieldDescription(description = "Good up to.")
		public Integer goodUpTo;
		@FieldDescription(description = "Average up to.")
		public Integer averageUpTo;
		@FieldDescription(description = "Bad up to.")
		public Integer badUpTo;
		@FieldDescription(description = "Very bad up to.")
		public Integer verybadUpTo;
		
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
