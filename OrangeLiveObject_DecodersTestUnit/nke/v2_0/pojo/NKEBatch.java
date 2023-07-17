package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;
import java.util.List;

@Data
@OutputPojo
public class NKEBatch {
	@FieldDescription(description = "Batch counter")
	public Integer batchCounter;
	@FieldDescription(description = "Payload timestamp")
	public String batchTimestamp;
	@FieldDescription(description = "List of temperatures, field build from tag BATCH_temperatures_°C_L0_R10_T7_D100")
	public BatchFloatList temperatures;
	/*
	@FieldDescription(description = "List of temperatures, field build from tag BATCH_temperatures-1_°C_L0_R10_T7_D100")
	public BatchFloatList temperatures-1;	
	@FieldDescription(description = "List of temperatures, field build from tag BATCH_temperatures-2_°C_L0_R10_T7_D100")
	public BatchFloatList temperatures-2;
	*/
	@FieldDescription(description = "List of index1, field build from tag BATCH_index_NoUnit_L6_R1_T10")
	public BatchIntList index;
	@FieldDescription(description = "List of index1, field build from tag BATCH_index1_/_L0_R1_T10_D1")
	public BatchIntList index1;
	@FieldDescription(description = "List of index2, field build from tag BATCH_index2_/_L0_R1_T10_D1")
	public BatchIntList index2;
	@FieldDescription(description = "List of index3, field build from tag BATCH_index3_/_L0_R1_T10_D1")
	public BatchIntList index3;
	
	@FieldDescription(description = "List of CO2, field build from tag BATCH_CO2_ppm_L3_R10_T6")
	public BatchIntList CO2;
	@FieldDescription(description = "List of COV, field build from tag BATCH_COV_NoUnit_L4_R10_T6")
	public BatchIntList COV;
	
	@FieldDescription(description = "List of illuminances, field build from tag BATCH_illuminances_%_L2_R1_T12")
	public BatchFloatList illuminances;
	@FieldDescription(description = "List of relativeHumidities, field build from tag BATCH_relativeHumidities_%RH_L1_R100_T6_D100")
	public BatchFloatList relativeHumidities;
	@FieldDescription(description = "List of humidities, field build from tag BATCH_humidities_%_L1_R500_T6_D100")
	public BatchFloatList humidities;
	@FieldDescription(description = "List of batteryLevels, field build from tag BATCH_batteryLevels_V_L2_R1_T6_D1000")
	public BatchFloatList batteryLevels;
	@FieldDescription(description = "List of openCaseAlarms, field build from tag BATCH_openCaseAlarms_NoUnit_L3_R1_T1")
	public BatchIntList openCaseAlarms;
	@FieldDescription(description = "List of rechargeableBattery levels, field build from tag BATCH_rechargeableBattery_Volts_L3_R600_T6_D1000")
	public BatchFloatList rechargeableBattery;
	@FieldDescription(description = "List of disposableBattery levels, field build from tag BATCH_disposableBattery_Volts_L4_R600_T6_D1000")
	public BatchFloatList disposableBattery;
	@FieldDescription(description = "List of TIC pa1 values, field build from tag BATCH_pa1_kW_L0_R1_T6")
	public BatchIntList	pa1;
	
	@FieldDescription(description = "List of OpenClose values, field build from tag BATCH_OpenClose_NoUnit_L0_R1_T1")
	public BatchOpenClose openClose;
	
	@FieldDescription(description = "Mean differential pressure since last report values, field build from tag BATCH_meanDifferentialPressureSinceLastReport_Pa_L0_R1_T7")
	public BatchDifferentialPressure meanDifferentialPressureSinceLastReport;
	
	@FieldDescription(description = "Minimal differential pressure since last report values, field build from tag BATCH_minimalDifferentialPressureSinceLastReport_Pa_L1_R1_T7")
	public BatchDifferentialPressure minimalDifferentialPressureSinceLastReport;

	@FieldDescription(description = "Maximal differential pressure since last report values, field build from tag BATCH_maximalDifferentialPressureSinceLastReport_Pa_L2_R1_T7")
	public BatchDifferentialPressure maximalDifferentialPressureSinceLastReport;
	
	@FieldDescription(description = "Differential pressure values, field build from tag BATCH_differentialPressure_Pa_L5_R1_T10")
	public BatchIntList differentialPressures;
	
	@FieldDescription(description = "Differential pressure values, field build from tag BATCH_states_NoUnit_L7_R1_T10")
	public BatchIntList states;
	
	@FieldDescription(description = "List of angles, field build from tag BATCH_Angle_Deg_L1_R1_T12")
	public BatchFloatList angles;
	
	@FieldDescription(description = "List of chocks, field build from tag BATCH_ChockMaxAccelerations_mg_L0_R1_T6")
	public BatchIntList chockMaxAccelerations;
	
	@FieldDescription(description = "Message error that can indicate an error in tag configuration. Values can be: <ul>"
			+ "<li>defined batch tags don't match the payload</li> "
			+ "<li>divide not numeric in tag</li> "
			+ "<li>empty dataMessage</li> "
			+ "<li>empty decoding</li> "
			+ "<li>empty payload</li> "
			+ "<li>incomplete payload</li> "
			+ "<li>internal error</li> "
			+ "<li>invalid type in tag</li> "
			+ "<li>label missing in tag</li> "
			+ "<li>label not numeric in tag</li> "
			+ "<li>resolution missing in tag</li> "
			+ "<li>resolution not numeric in tag</li> "
			+ "<li>type not numeric in tag</li> "
			+ "<li>type missing in tag</li> "
			+ "<li>wrong type in tag</li> </ul>")
	public String error;
	
	@Data
	@OutputPojo
	public class IntValue {
		@FieldDescription(description = "Timestamp of measure")
		public String timestamp;
		@FieldDescription(description = "Measure value")
		public Integer value;
	}
	
	@Data
	@OutputPojo
	public class FloatValue {
		@FieldDescription(description = "Timestamp of measure")
		public String timestamp;
		@FieldDescription(description = "Measure value")
		public Float value;
	}	
	
	@Data
	@OutputPojo
	public class BatchFloatList {
		@FieldDescription(description = "Unit of values in list")
		public String unit;
		@FieldDescription(description = "List of values")
		public List<FloatValue> values = null;		
	}
	
	@Data
	@OutputPojo
	public class BatchIntList {
		@FieldDescription(description = "Unit of values in list")
		public String unit;
		@FieldDescription(description = "List of values")
		public List<IntValue> values = null;		
	}
	
	@Data
	@OutputPojo
	public class BatchOpenClose {
		@FieldDescription(description = "List of values")
		public List<IntValue> values = null;	
	}
	
	@Data
	@OutputPojo
	public class BatchDifferentialPressure{
		@FieldDescription(description = "Pa")
		public String unit;
		@FieldDescription(description = "List of values")
		public List<IntValue> values = null;	
	}
	
}
