package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterConfiguration extends NKEFrameHeader {
//0050 Configuration
	
	@FieldDescription(description = "Current configuration mode. Values can be : <ul><li>Not conf mode</li> <li>Conf mode RX (Allways awake)</li> <li>Conf mode NS (NS each 3 sec)</li> <li>reserved</li></ul>")
	public String mode;
	@FieldDescription(description = "Remaining duration in configuration mode in seconds")
	public RemainingDuration remainingDuration;
	@FieldDescription(description = "Current power mode. Values can be : <ul><li>ON when idle</li> <li>Periodically ON</li> <li>ON on user event</li><li>reserved</li></ul>")
	public String currentPowerMode;
	@FieldDescription(description = "Power sources of the sensor.")
	public PowerSources powerSources;
	@FieldDescription(description = "Current power source")
	public String currentPowerSource;
	
	@Data
    @OutputPojo
	public class RemainingDuration {
		@FieldDescription(description = "Remaining duration in configuration mode in seconds")	
		public Float value;
		@FieldDescription(description = "seconds")	
		public String unit;	
	}
	
	@Data
    @OutputPojo
	public class PowerSources {	
		@FieldDescription(description = "Power Source type = Constant Or External Power")
		public ConstOrExtPower constOrExtPower;
		@FieldDescription(description = "Power Source type = Rechargeable Battery")
		public RechargBattery rechargBattery;
		@FieldDescription(description = "Power Source type = Diposable Battery")
		public DiposBattery diposBattery;
		@FieldDescription(description = "Power Source type = Solar Harvesting")
		public SolHarvesting solHarvesting;
		@FieldDescription(description = "Power Source type = TIC Harvesting")
		public TicHarvesting ticHarvesting;	
	}

	@Data
    @OutputPojo
	public class ConstOrExtPower {	
		@FieldDescription(description = "True if constant or external power is an available power source of the sensor")
		public Boolean available;
		@FieldDescription(description = "Constant or external power voltage level")
		public Float value;
		@FieldDescription(description = "Volts")
		public String unit;	
	}
	
	@Data
    @OutputPojo
	public class DiposBattery {	
		@FieldDescription(description = "True if diposable battery is an available power source of the sensor")
		public Boolean available;
		@FieldDescription(description = "Diposable battery voltage level")
		public Float value;
		@FieldDescription(description = "Volts")
		public String unit;	
	}
	
	@Data
    @OutputPojo
	public class RechargBattery {
		@FieldDescription(description = "True if rechargeable battery is an available power source of the sensor")
		public Boolean available;
		@FieldDescription(description = "Rechargeable battery voltage level")
		public Float value;
		@FieldDescription(description = "Volts")
		public String unit;	
	}
	
	@Data
    @OutputPojo
	public class SolHarvesting {	
		@FieldDescription(description = "True if solar harvesting is an available power source of the sensor")
		public Boolean available;
		@FieldDescription(description = "If a sensor is “Solar harvesting” capable, voltage from solar cells is technically not measurable, hence reading is not significative and should always be 0.")
		public Float value;
		@FieldDescription(description = "Volts")
		public String unit;	
	}
	
	@Data
    @OutputPojo
	public class TicHarvesting {	
		@FieldDescription(description = "True if TIC harvesting is an available power source of the sensor")
		public Boolean available;
		@FieldDescription(description = "TIC harvesting voltage level")
		public Float value;
		@FieldDescription(description = "Volts")
		public String unit;	
	}
	
}