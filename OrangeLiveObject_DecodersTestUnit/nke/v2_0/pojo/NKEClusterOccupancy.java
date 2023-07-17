package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class NKEClusterOccupancy extends NKEFrameHeader {
// 0006 ON/OFF
	
	@FieldDescription(description = "Current state of presence detection. Value can be 0 or 1.")
	public Integer occupancyValue;
	
	@FieldDescription(description = "This delay (seconds) is used to specify after how long time without move the sensor will decide that the room is unocupied.")
	public Period occupiedToUnoccupiedDelay;
		
	@Data
	@OutputPojo
	public class Period {
		@FieldDescription(description = "Unit duration")
		public String unit;
		@FieldDescription(description = "Duration")
		public Integer value;
	}
	
	@FieldDescription(description = "The occupancy type attribute informs about the current kind of application of the cluster. "
			+ "This should be set according to the sensor type or sensor usage. "
			+ "Values can be : Passive infrared, UltraSonic or Passive Infrared and Ultrasonic")
	public String occupancyType; 
	
	
}
