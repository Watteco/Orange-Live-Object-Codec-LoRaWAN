package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterConcentrationMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterDifferentialPressureMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterIlluminanceMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterOccupancy;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTemperatureMeasurement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

/* Tests clusters : 
0x000F Binary Input
0x400 Illuminance
0x0402 Temperature Measurement
0x406 Occupancy
0x8008 Differential pressure measurements
0x800C Concentration Measurement
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Vaqao 50-70-074 50-70-168", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Clusters:<ul>",
		 "<li>000C AnalogInput</li>", 
	    "<li>000F Binary Input</li>", 	
	    "<li>0400 Illuminance</li>", 
	    "<li>0402 Temperature Measurement</li>", 
	    "<li>0403 Pressure</li>", 
	    "<li>0405 Humidity</li>", 
	    "<li>0406 Occupancy</li>",
	    "<li>8008 Differential Pressure Measurements</li>",		
	    "<li>800C Concentration</li>", 
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeVaqaoTest extends TestBase {
	
	private final String SCRIPT_PATH = "nke/nkePublicV2_10";


    @Test
    public void should_profile() throws JsDecodingException {
        String input = "1101000f040288230000005a";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:count, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0402_cmd01_payload() throws JsDecodingException {
    	 /* payload build for test */
        String input = "1101000f040288230000005a";
        
        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Count");
        assertThat(binaryInput.getStatus()).isEqualTo("Invalid Value");
    }

    @Test
    public void should_decode_000F_BinaryInput_attribut0402_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3107000F88000402";
        
        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("1");
        assertThat(binaryInput.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Count");
        assertThat(binaryInput.getStatus()).isEqualTo("Invalid Value");
        assertThat(binaryInput.getReport()).isEqualTo("Standard report");
    }

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:PresentValue, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0055_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0055001001";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getValue()).isEqualTo("1");
    }
    
    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Polarity", description = "Cluster: Binary input, Attribut:Polarity, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0054_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0054001001";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Polarity");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getPolarity()).isEqualTo("Reversed");
    }
    
    @Test
    @PayloadDescription(name = "ClusterBinaryInput_EdgeSelection", description = "Cluster: Binary input, Attribut:Edge selection, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0400_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0400001801";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Edge Selection");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getEdgeSelection()).isEqualTo("Falling edge");
    }
    
    @Test
    @PayloadDescription(name = "ClusterBinaryInput_DebouncePeriod", description = "Cluster: Binary input, Attribut:Debounce period , Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0401_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f040100210011";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Debounce Period");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getDebouncePeriod().getValue()).isEqualTo(17);
        assertThat(binaryInput.getDebouncePeriod().getUnit()).isEqualTo("milliseconds");
    }

    @Test
    @PayloadDescription(name = "ClusterIlluminanceMeasurement_MeasuredValue", description = "Cluster: Illuminance Measurement, Attribut: MeasuredValue, Command: Report attributes.")
    public void should_decode_0400_IlluminanceMeasurement_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A04000000210aea";
        
        NKEClusterIlluminanceMeasurement illuminanceMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterIlluminanceMeasurement.class);
        assertThat(illuminanceMeasurement.getMessageType()).isEqualTo("Illuminance Measurement");
        assertThat(illuminanceMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(illuminanceMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(illuminanceMeasurement.getAttributId()).isEqualTo("MeasuredValue");
        assertThat(illuminanceMeasurement.getMeasure().getValue()).isEqualTo(10.0f);
        assertThat(illuminanceMeasurement.getMeasure().getUnit()).isEqualTo("lux");
    }
    
    @Test
    @PayloadDescription(name = "ClusterIlluminanceMeasurement_Minimum", description = "Cluster: Illuminance Measurement, Attribut: Minimum, Command: Report attributes.")
    public void should_decode_0400_IlluminanceMeasurement_attribut0001_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A04000001210aea";
        
        NKEClusterIlluminanceMeasurement illuminanceMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterIlluminanceMeasurement.class);
        assertThat(illuminanceMeasurement.getMessageType()).isEqualTo("Illuminance Measurement");
        assertThat(illuminanceMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(illuminanceMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(illuminanceMeasurement.getAttributId()).isEqualTo("MinMeasuredValue");
        assertThat(illuminanceMeasurement.getMinimum().getValue()).isEqualTo(10.0f);
        assertThat(illuminanceMeasurement.getMinimum().getUnit()).isEqualTo("lux");
    }
    
    @Test
    @PayloadDescription(name = "ClusterIlluminanceMeasurement_Maximum", description = "Cluster: Illuminance Measurement, Attribut: Maximum, Command: Report attributes.")
    public void should_decode_0400_IlluminanceMeasurement_attribut0002_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A04000002210aea";
        
        NKEClusterIlluminanceMeasurement illuminanceMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterIlluminanceMeasurement.class);
        assertThat(illuminanceMeasurement.getMessageType()).isEqualTo("Illuminance Measurement");
        assertThat(illuminanceMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(illuminanceMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(illuminanceMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
        assertThat(illuminanceMeasurement.getMaximum().getValue()).isEqualTo(10.0f);
        assertThat(illuminanceMeasurement.getMaximum().getUnit()).isEqualTo("lux");
    }   

    @Test
    @PayloadDescription(name = "ClusterTemperatureMeasurement_MeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MeasuredValue, Command: Report attributes.")
    public void should_decode_0402_TemperatureMeasurement_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A04020000290aea";
        
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MeasuredValue");
        assertThat(temperatureMeasurement.getMeasure().getValue()).isEqualTo(27.94f);
        assertThat(temperatureMeasurement.getMeasure().getUnit()).isEqualTo("°C");
    }

    @Test
    @PayloadDescription(name = "ClusterTemperatureMeasurement_MeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MeasuredValue, Command: Threshold report.")
    public void should_decode_0402_TemperatureMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
    	/* payload build for test */
        String input = "118A040200002901C298D2";
        
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MeasuredValue");
        assertThat(temperatureMeasurement.getMeasure().getValue()).isEqualTo(4.5f);
        assertThat(temperatureMeasurement.getMeasure().getUnit()).isEqualTo("°C");
        assertThat(temperatureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(temperatureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
        assertThat(temperatureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(temperatureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);

    }


    @Test
    @PayloadDescription(name = "ClusterTemperatureMeasurement_MinMeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MinMeasuredValue, Command: Read Attribute Response.")
    public void should_decode_0402_TemperatureMeasurement_attribut0001_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "1101040200010029f060";
        
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MinMeasuredValue");
        assertThat(temperatureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(temperatureMeasurement.getMinimum().getValue()).isEqualTo(-40.0f);
        assertThat(temperatureMeasurement.getMinimum().getUnit()).isEqualTo("°C");
    }

    @Test
    @PayloadDescription(name = "ClusterTemperatureMeasurement_MaxMeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MaxMeasuredValue, Command: Threshold Report.")
    public void should_decode_0402_TemperatureMeasurement_attribut0002_cmd8A_payload() throws JsDecodingException {
        /* real payload */
        String input = "118A0402000229009AA8B2000A00018000010002D3000B00018000020003";
              
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
        assertThat(temperatureMeasurement.getMaximum().getValue()).isEqualTo(1.54f);
        assertThat(temperatureMeasurement.getMaximum().getUnit()).isEqualTo("°C");
        assertThat(temperatureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(temperatureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(temperatureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(temperatureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(temperatureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
        assertThat(temperatureMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(temperatureMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(temperatureMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(temperatureMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
        
        assertThat(temperatureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(temperatureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(temperatureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(temperatureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(temperatureMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
        assertThat(temperatureMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
        assertThat(temperatureMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(temperatureMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(temperatureMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);

    }

    

    @Test
    @PayloadDescription(name = "ClusterOccupancy_OccupancyValue", description = "Cluster: Occupancy, Attribut: OccupancyValue, Command: Report attributes.")
    public void should_decode_0406_Occupancy_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A040600001801";
        
        NKEClusterOccupancy occupancy = formatAndDecode(SCRIPT_PATH, input, NKEClusterOccupancy.class);
        assertThat(occupancy.getMessageType()).isEqualTo("Occupancy");
        assertThat(occupancy.getEndpoint()).isEqualTo("0");
        assertThat(occupancy.getCommandId()).isEqualTo("Report Attributes");
        assertThat(occupancy.getAttributId()).isEqualTo("Occupancy value");
        assertThat(occupancy.getOccupancyValue()).isEqualTo(1);
       
    }
    
    @Test
    @PayloadDescription(name = "ClusterOccupancy_Occupied to Unoccupied delay", description = "Cluster: Occupancy, Attribut: Occupied to Unoccupied delay, Command: Report attributes.")
    public void should_decode_0406_Occupancy_attribut0010_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A04060010210aea";
        
        NKEClusterOccupancy occupancy = formatAndDecode(SCRIPT_PATH, input, NKEClusterOccupancy.class);
        assertThat(occupancy.getMessageType()).isEqualTo("Occupancy");
        assertThat(occupancy.getEndpoint()).isEqualTo("0");
        assertThat(occupancy.getCommandId()).isEqualTo("Report Attributes");
        assertThat(occupancy.getAttributId()).isEqualTo("Occupied to Unoccupied delay");
        assertThat(occupancy.getOccupiedToUnoccupiedDelay().getValue()).isEqualTo(2794);
        assertThat(occupancy.getOccupiedToUnoccupiedDelay().getUnit()).isEqualTo("seconds");
    }
    
    @Test
    @PayloadDescription(name = "ClusterOccupancy_OccupancyType", description = "Cluster: Occupancy, Attribut: OccupancyType, Command: Report attributes.")
    public void should_decode_0406_Occupancy_attribut0001_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110A040600013001";
        
        NKEClusterOccupancy occupancy = formatAndDecode(SCRIPT_PATH, input, NKEClusterOccupancy.class);
        assertThat(occupancy.getMessageType()).isEqualTo("Occupancy");
        assertThat(occupancy.getEndpoint()).isEqualTo("0");
        assertThat(occupancy.getCommandId()).isEqualTo("Report Attributes");
        assertThat(occupancy.getAttributId()).isEqualTo("Occupancy Type");
        assertThat(occupancy.getOccupancyType()).isEqualTo("UltraSonic");
       
    }
   
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "11018008000000291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measured value");
        assertThat(differentialPressureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(differentialPressureMeasurement.getMeasure().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeasure().getValue()).isEqualTo(4096); 
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Threshold Report.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
        /* real payload */
        String input = "118A8008000029009A98B2";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measured value");
        assertThat(differentialPressureMeasurement.getMeasure().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeasure().getValue()).isEqualTo(154); 
        assertThat(differentialPressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
        assertThat(differentialPressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        
        /*
          decoded : {"endpoint":"0","commandId":"Threshold Report","messageType":"Differential Pressure Measurement",
          "attributId":"Measured value","measure":{"value":154,"unit":"Pa"},
          "reportParameters":{"batch":false,"noHeaderPort":false,"secured":false,"securedIfAlarm":true,"causeRequest":"short","newModeConfiguration":true},
          "criterions":[
          {"criteriaSlotDescriptor":{"criterionIndex":2,"mode":"threshold","onFall":true,"onExceed":false,"alarm":true}}]}


         */
    }
    
    @Test
    
 //   @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Threshold Report.")

    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_cr_short2_payload2() throws JsDecodingException {
        /* real payload */
        String input = "118A8008000029009A98B2D3";
    
    NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
    assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
    assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
    assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measured value");
    assertThat(differentialPressureMeasurement.getMeasure().getUnit()).isEqualTo("Pa");
    assertThat(differentialPressureMeasurement.getMeasure().getValue()).isEqualTo(154); 
    assertThat(differentialPressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
    assertThat(differentialPressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
    assertThat(differentialPressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
    assertThat(differentialPressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
    assertThat(differentialPressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
    assertThat(differentialPressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
    assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
    assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
    assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
    assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
    assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
    assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
    assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
    assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
    assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
    
    /*
    decoded : {"endpoint":"0","commandId":"Threshold Report","messageType":"Differential Pressure Measurement",
    "attributId":"Measured value","measure":{"value":154,"unit":"Pa"},
    "reportParameters":{"batch":false,"noHeaderPort":false,"secured":false,"securedIfAlarm":true,"causeRequest":"short","newModeConfiguration":true},
    "criterions":[
    {"criteriaSlotDescriptor":{"criterionIndex":2,"mode":"threshold","onFall":true,"onExceed":false,"alarm":true}},
    {"criteriaSlotDescriptor":{"criterionIndex":3,"mode":"threshold","onFall":false,"onExceed":true,"alarm":true}}]}

*/
    
}
        
        @Test
        @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Threshold Report.")
        public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_cr_long_payload2() throws JsDecodingException {
        /* real payload */
        String input = "118A8008000029009AA8B2000A00018000010002";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measured value");
        assertThat(differentialPressureMeasurement.getMeasure().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeasure().getValue()).isEqualTo(154); 
        assertThat(differentialPressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(differentialPressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        
        /*
        decoded : {"endpoint":"0","commandId":"Threshold Report","messageType":"Differential Pressure Measurement",
        "attributId":"Measured value","measure":{"value":154,"unit":"Pa"},
        "reportParameters":{"batch":false,"noHeaderPort":false,"secured":false,"securedIfAlarm":true,"causeRequest":"long","newModeConfiguration":true},
        "criterions":[
        {"criteriaSlotDescriptor":{"criterionIndex":2,"mode":"threshold","onFall":true,"onExceed":false,"alarm":true},
        "value":10,
        "gap":1,
        "numberOfOccurances":0,"occurencesHigh":1,"occurencesLow":2}]}


         */
        
    }
    
        
        @Test
        @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Threshold Report.")
        public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_cr_long2_payload2() throws JsDecodingException {
        /* real payload */
        String input = "118A8008000029009AA8B2000A00018000010002D3000B00018000020003";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measured value");
        assertThat(differentialPressureMeasurement.getMeasure().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeasure().getValue()).isEqualTo(154); 
        assertThat(differentialPressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(differentialPressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        
        /*
        decoded : {"endpoint":"0","commandId":"Threshold Report","messageType":"Differential Pressure Measurement",
        "attributId":"Measured value","measure":{"value":154,"unit":"Pa"},
        "reportParameters":{"batch":false,"noHeaderPort":false,"secured":false,"securedIfAlarm":true,"causeRequest":"long","newModeConfiguration":true},
        "criterions":[
        {"criteriaSlotDescriptor":{"criterionIndex":2,"mode":"threshold","onFall":true,"onExceed":false,"alarm":true},
        "value":10,
        "gap":1,
        "numberOfOccurances":0,"occurencesHigh":1,"occurencesLow":2
        },
        {"criteriaSlotDescriptor":{"criterionIndex":3,"mode":"threshold","onFall":false,"onExceed":true,"alarm":true},
        "value":11,
        "gap":1,
        "numberOfOccurances":0,"occurencesHigh":2,"occurencesLow":3
        }
        ]}


         */
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MinMeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Min measured value, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0001_cmd01_payload() throws JsDecodingException {
        
        String input = "11018008000100291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Min measured value");
        assertThat(differentialPressureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(differentialPressureMeasurement.getMinimum().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMinimum().getValue()).isEqualTo(4096); 
        

    }
    
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MaxMeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Max measured value, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0002_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A80080002291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Max measured value");
        assertThat(differentialPressureMeasurement.getMaximum().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMaximum().getValue()).isEqualTo(4096); 
    }
    
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasurementPeriod", description = "Cluster: Differential pressure measurements, Attribut:Measurement period, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0003_cmd0A_payload() throws JsDecodingException {
       
        String input = "110A800800032300001000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Measurement period");
        assertThat(differentialPressureMeasurement.getMeasurementPeriod().getUnit()).isEqualTo("ms");
        assertThat(differentialPressureMeasurement.getMeasurementPeriod().getValue()).isEqualTo(4096); 
    }
      
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_SamplesPerMeasurement", description = "Cluster: Differential pressure measurements, Attribut:Samples per measurement, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0004_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A80080004211000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Samples per measurement");
        assertThat(differentialPressureMeasurement.getSamplesPerMeasurement()).isEqualTo(4096); 
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_SamplesPerConfirmationMeasurement", description = "Cluster: Differential pressure measurements, Attribut:Samples per confirmation measurement, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0005_cmd0A_payload() throws JsDecodingException {
       
        String input = "110A80080005211000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Samples per confirmation measurement");
        assertThat(differentialPressureMeasurement.getSamplesPerConfirmationMeasurement()).isEqualTo(4096); 
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_SamplingPeriod", description = "Cluster: Differential pressure measurements, Attribut:Sampling period, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0006_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800800062300001000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Sampling period");
        assertThat(differentialPressureMeasurement.getSamplingPeriod().getUnit()).isEqualTo("ms");
        assertThat(differentialPressureMeasurement.getSamplingPeriod().getValue()).isEqualTo(4096); 
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeanMeasuredValueSinceLastReport", description = "Cluster: Differential pressure measurements, Attribut:Mean measured value since last report, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0100_cmd01_payload() throws JsDecodingException {
        
        String input = "11018008010000291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Mean measured value since last report");
        assertThat(differentialPressureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(differentialPressureMeasurement.getMeanMeasuredValueSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeanMeasuredValueSinceLastReport().getValue()).isEqualTo(4096); 
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MinimalMeasuredValueSinceLastReport", description = "Cluster: Differential pressure measurements, Attribut:Minimal measured value since last report, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0101_cmd01_payload() throws JsDecodingException {
        
        String input = "11018008010100291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Minimal measured value since last report");
        assertThat(differentialPressureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(differentialPressureMeasurement.getMinimalMeasuredValueSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMinimalMeasuredValueSinceLastReport().getValue()).isEqualTo(4096); 
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MaximalMeasuredValueSinceLastReport", description = "Cluster: Differential pressure measurements, Attribut:Maximal measured value since last report, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0102_cmd01_payload() throws JsDecodingException {
        
        String input = "11018008010200291000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Maximal measured value since last report");
        assertThat(differentialPressureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(differentialPressureMeasurement.getMaximalMeasuredValueSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMaximalMeasuredValueSinceLastReport().getValue()).isEqualTo(4096); 
    }
 
    

    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MeasuredValue", description = "Cluster: ConcentrationMeasurement, Attribut: measuredValue, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0000_cmd0A_payload() throws JsDecodingException {
 
        String input = "110A800C0000210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Measured value");
        assertThat(concentrationMeasurement.getMeasure().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getMeasure().getUnit()).isEqualTo("ppm or IAQ");
        
    }
    
    @Test
    public void should_decode_800C_ConcentrationMeasurement_attribut0000_cmd8A_cr_short2_payload2() throws JsDecodingException {
       
        String input = "118A800C000021009A98B2D3";
    
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
	    assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Threshold Report");
	    assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Measured value");
	    assertThat(concentrationMeasurement.getMeasure().getUnit()).isEqualTo("ppm or IAQ");
	    assertThat(concentrationMeasurement.getMeasure().getValue()).isEqualTo(154); 
	    assertThat(concentrationMeasurement.getReportParameters().getBatch()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getSecured()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
	    assertThat(concentrationMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
    
}
        
    
    
    
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MeasuredValue", description = "Cluster: ConcentrationMeasurement, Attribut:Measured value, Command: Threshold Report.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0000_cmd8A_cr_long2_payload2() throws JsDecodingException {
   
	    String input = "118A800C000021009AA8B2000A00018000010002D3000B00018000020003";
	    
	    NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
	    assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Threshold Report");
	    assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Measured value");
	    assertThat(concentrationMeasurement.getMeasure().getUnit()).isEqualTo("ppm or IAQ");
	    assertThat(concentrationMeasurement.getMeasure().getValue()).isEqualTo(154); 
	    assertThat(concentrationMeasurement.getReportParameters().getBatch()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getSecured()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getReportParameters().getCauseRequest()).isEqualTo("long");
	    assertThat(concentrationMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
	    assertThat(concentrationMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
	    
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
	    assertThat(concentrationMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);

    } 
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_ConcentrationLevelClassification", description = "Cluster: ConcentrationMeasurement, Attribut: Concentration level classification, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0001_cmd0A_payload() throws JsDecodingException {
 
        String input = "110A800C00012003";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Concentration level classification");   
        assertThat(concentrationMeasurement.getCurrentClassificationLevel()).isEqualTo("BAD");

    }
    
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MeanMeasuredValue", description = "Cluster: ConcentrationMeasurement, Attribut: meanMeasuredValue, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0100_cmd0A_payload() throws JsDecodingException {
 
        String input = "110A800C0100210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Mean measured value");
        assertThat(concentrationMeasurement.getMean().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getMean().getUnit()).isEqualTo("ppm or IAQ");
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MinimumMeasuredValue", description = "Cluster: ConcentrationMeasurement, Attribut: minimumMeasuredValue, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0101_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800C0101210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Minimum measured value");
        assertThat(concentrationMeasurement.getMinimum().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getMinimum().getUnit()).isEqualTo("ppm or IAQ");
    }
    
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MaximumMeasuredValue", description = "Cluster: ConcentrationMeasurement, Attribut: maximumMeasuredValue, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut0102_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800C0102210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Maximum measured value");
        assertThat(concentrationMeasurement.getMaximum().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getMaximum().getUnit()).isEqualTo("ppm or IAQ");      
    }
    
   
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_ConcentrationUnit", description = "Cluster: ConcentrationMeasurement, Attribut: concentrationUnit, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut8004_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800C8004200c";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Concentration unit");
        assertThat(concentrationMeasurement.getConcentrationUnit()).isEqualTo("Parts par Trillion");
      

    }
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_MinimalNormalConcentrationLevel", description = "Cluster: ConcentrationMeasurement, Attribut: Minimal Normal concentration level, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut8008_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800C8008210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Minimal/Normal concentration level");
        assertThat(concentrationMeasurement.getMinNormalLevel().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getMinNormalLevel().getUnit()).isEqualTo("ppm or IAQ");            
        
    }
    
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_CalibrationPeriod", description = "Cluster: ConcentrationMeasurement, Attribut: Calibration period, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut8009_cmd0A_payload() throws JsDecodingException {
        
        String input = "110A800C800920ea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Calibration period");
        assertThat(concentrationMeasurement.getCalibrationPeriod().getValue()).isEqualTo(234);
        assertThat(concentrationMeasurement.getCalibrationPeriod().getUnit()).isEqualTo("days");                 

    }
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_ClassificationLevels", description = "Cluster: ConcentrationMeasurement, Attribut: Classification levels, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut8010_cmd0A_payload() throws JsDecodingException {
       
        String input = "110A800C8010410A00ff010010005000ffff";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("Classification levels");
        assertThat(concentrationMeasurement.getCalibrationCoefficients().getExcellentUpTo()).isEqualTo(255);
        assertThat(concentrationMeasurement.getCalibrationCoefficients().getGoodUpTo()).isEqualTo(256);
        assertThat(concentrationMeasurement.getCalibrationCoefficients().getAverageUpTo()).isEqualTo(4096);
        assertThat(concentrationMeasurement.getCalibrationCoefficients().getBadUpTo()).isEqualTo(20480);
        assertThat(concentrationMeasurement.getCalibrationCoefficients().getVerybadUpTo()).isEqualTo(65535);  
 
    }
    
    @Test
    @PayloadDescription(name = "ClusterConcentrationMeasurement_HMI_display_period", description = "Cluster: ConcentrationMeasurement, Attribut: HMI display period, Command: Report Attributes.")
    public void should_decode_800C_ConcentrationMeasurement_attribut8020_cmd0A_payload() throws JsDecodingException {
        
    	String input = "110A800C8020210aea";
        
        NKEClusterConcentrationMeasurement concentrationMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterConcentrationMeasurement.class);
        assertThat(concentrationMeasurement.getMessageType()).isEqualTo("Concentration Measurement");
        assertThat(concentrationMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(concentrationMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(concentrationMeasurement.getAttributId()).isEqualTo("HMI display period");
        assertThat(concentrationMeasurement.getHmiDisplayPeriod().getValue()).isEqualTo(2794);
        assertThat(concentrationMeasurement.getHmiDisplayPeriod().getUnit()).isEqualTo("Seconds");        
        
    }
    

    
    
    
}
