package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTemperatureMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterAnalogInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterRelativeHumidityMeasurement;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) THr 50-70-007 and THr Plus 50-70-047", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Clusters:<ul>",
		"<li>0405 Relative Humidity Measurements</li>",
	    "<li>0402 Temperature Measurement</li>", 
	    "<li>000c Analog Input</li>", 						    
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = false)  
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeThrTest  extends TestBase {
	
	private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "310a000c0055394201e577";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

		/*
	    @Test
	    @Ignore //fonctions récursives volontairement laissées dans le script
	    public void should_check_script() throws JsDecodingException {
	        checkScript(SCRIPT_PATH);
	    }
		*/ 
	    
	    @Test
	    public void should_decode_bad_payload() throws JsDecodingException {    
	        formatAndDecode(SCRIPT_PATH, "0", Object.class);
	    }
	    
	    
	    @Test
	    @PayloadDescription(name = "ClusterAnalogInput_PresentValue", description = "Cluster: Analog input, Attribut: PresentValue, Command: Report Attributes.")
	    public void should_decode_000C_AnalogInput_attribut0055_cmd0A_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "310a000c0055394201e577";

	        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
	        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
	        assertThat(analogInput.getEndpoint()).isEqualTo("1");
	        assertThat(analogInput.getCommandId()).isEqualTo("Report Attributes");
	        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
	        assertThat(analogInput.getValue()).isEqualTo(32.474087f);
	    }

	    @Test
	    public void should_decode_000C_AnalogInput_cmd07_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "3107000c80";

	        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
	        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
	        assertThat(analogInput.getEndpoint()).isEqualTo("1");
	        assertThat(analogInput.getCommandId()).isEqualTo("Configure Reporting Response");
	        assertThat(analogInput.getStatus()).isEqualTo("Malformed Command");
	    }
	    
	    @Test
	    @PayloadDescription(name = "ClusterAnalogInput_ApplicationType", description = "Cluster: Analog input, Attribut:ApplicationType, Command: Read Attribute Response.")
	    public void should_decode_000C_AnalogInput_attribut0100_cmd01_payload() throws JsDecodingException {
	        /* payload build for test */
	        String input = "3101000C0100002300050000";

	        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
	        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
	        assertThat(analogInput.getEndpoint()).isEqualTo("1");
	        assertThat(analogInput.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(analogInput.getAttributId()).isEqualTo("ApplicationType");
	        assertThat(analogInput.getStatus()).isEqualTo("Ok");
	        assertThat(analogInput.getApplicationType()).isEqualTo("Carbon Dioxide AI application");
	        assertThat(analogInput.getApplicationUnit()).isEqualTo("PPM");
	    }



	    @Test
	    @PayloadDescription(name = "ClusterTemperatureMeasurement_MeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MeasuredValue, Command: Read Attribute Response.")
	    public void should_decode_0402_TemperatureMeasurement_attribut0000_cmd01_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "11010402000000290aea";
	        
	        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
	        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
	        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MeasuredValue");
	        assertThat(temperatureMeasurement.getStatus()).isEqualTo("Ok");
	        assertThat(temperatureMeasurement.getMeasure().getValue()).isEqualTo(27.94f);
	        assertThat(temperatureMeasurement.getMeasure().getUnit()).isEqualTo("°C");
	    }

	    @Test
	    @PayloadDescription(name = "ClusterTemperatureMeasurement_MeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MeasuredValue, Command: ZCL-like threshold report.")
	    public void should_decode_0402_TemperatureMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
	        /* payload from documentation */
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
	        assertThat(temperatureMeasurement.getMinimum().getValue()).isEqualTo(-40);
	        assertThat(temperatureMeasurement.getMinimum().getUnit()).isEqualTo("°C");
	    }
    
	    @Test
	    @PayloadDescription(name = "ClusterTemperatureMeasurement_MaxMeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MaxMeasuredValue, Command: Read Attribute Response.")
	    public void should_decode_0402_TemperatureMeasurement_attribut0002_cmd01_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "11010402000200292ee0";
	        
	        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
	        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
	        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
	        assertThat(temperatureMeasurement.getStatus()).isEqualTo("Ok");
	        assertThat(temperatureMeasurement.getMaximum().getValue()).isEqualTo(120);
	        assertThat(temperatureMeasurement.getMaximum().getUnit()).isEqualTo("°C");
	    }
		  
	    @Test
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0000_cmd01_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "11010405000000211346";
	        
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MeasuredValue");
	        assertThat(humidityMeasurement.getStatus()).isEqualTo("Ok");
	        assertThat(humidityMeasurement.getMeasure().getValue()).isEqualTo(49.34f);
	        assertThat(humidityMeasurement.getMeasure().getUnit()).isEqualTo("%");
	    }
	    
	    @Test
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
	        /* payload from documentation */
	        String input = "118A040500002101C298D2";
	        
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Threshold Report");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MeasuredValue");
	        assertThat(humidityMeasurement.getMeasure().getValue()).isEqualTo(4.5f);
	        assertThat(humidityMeasurement.getMeasure().getUnit()).isEqualTo("%");
	        assertThat(humidityMeasurement.getReportParameters().getBatch()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getSecured()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
	        assertThat(humidityMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
	        assertThat(humidityMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
	        assertThat(humidityMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);

	    }
	    
	    
	    @Test
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0000_cmd8A_cr_long2_payload2() throws JsDecodingException {
	   
		    String input = "118A0405000021009AA8B2000A00018000010002D3000B00018000020003";
		    
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Threshold Report");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MeasuredValue");
	        assertThat(humidityMeasurement.getMeasure().getValue()).isEqualTo(1.54f);
	        assertThat(humidityMeasurement.getMeasure().getUnit()).isEqualTo("%");
	        assertThat(humidityMeasurement.getReportParameters().getBatch()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getSecured()).isEqualTo(false);
	        assertThat(humidityMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
	        assertThat(humidityMeasurement.getReportParameters().getCauseRequest()).isEqualTo("long");
	        assertThat(humidityMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
	        
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
	        assertThat(humidityMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
		    assertThat(humidityMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
		    assertThat(humidityMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
		    assertThat(humidityMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
		    assertThat(humidityMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
		    assertThat(humidityMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
		    
		    assertThat(humidityMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
		    assertThat(humidityMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
		    assertThat(humidityMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
		    assertThat(humidityMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
		    assertThat(humidityMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
		    assertThat(humidityMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
		    assertThat(humidityMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
		    assertThat(humidityMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
		    assertThat(humidityMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
		    assertThat(humidityMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);
		    
	    } 
	    
	    
	    @Test
	    @PayloadDescription(name = "ClusterRelativeHumidityMeasurement_MeasuredValue", description = "Cluster: Relative Humidity Measurement, Attribut: MeasuredValue, Command: Report attributes.")
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0000_cmd0A_payload() throws JsDecodingException {
	        /* payload build for test */
	        String input = "110A04050000211346";
	        
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Report Attributes");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MeasuredValue");
	        assertThat(humidityMeasurement.getMeasure().getValue()).isEqualTo(49.34f);
	        assertThat(humidityMeasurement.getMeasure().getUnit()).isEqualTo("%");
	    }
	    
	    @Test
	    @PayloadDescription(name = "ClusterRelativeHumidityMeasurement_MinMeasuredValue", description = "Cluster: Relative Humidity Measurement, Attribut: MinMeasuredValue, Command: Read Attribute Response.")
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0001_cmd01_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "11010405000100210000";
	        
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MinMeasuredValue");
	        assertThat(humidityMeasurement.getStatus()).isEqualTo("Ok");
	        assertThat(humidityMeasurement.getMinimum().getValue()).isEqualTo(0);
	        assertThat(humidityMeasurement.getMinimum().getUnit()).isEqualTo("%");
	    }
	    
	    @Test
	    @PayloadDescription(name = "ClusterRelativeHumidityMeasurement_MaxMeasuredValue", description = "Cluster: Relative Humidity Measurement, Attribut: MaxMeasuredValue, Command: Read Attribute Response.")
	    public void should_decode_0405_RelativeHumidityMeasurement_attribut0002_cmd01_payload() throws JsDecodingException {
	        /* real payload */
	        String input = "11010405000200212710";
	        
	        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
	        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
	        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
	        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
	        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
	        assertThat(humidityMeasurement.getStatus()).isEqualTo("Ok");
	        assertThat(humidityMeasurement.getMaximum().getValue()).isEqualTo(100);
	        assertThat(humidityMeasurement.getMaximum().getUnit()).isEqualTo("%");
	    }

}
