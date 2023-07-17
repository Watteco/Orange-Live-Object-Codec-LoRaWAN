package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterDifferentialPressureMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTemperatureMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterPressureMeasurement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

/* Tests clusters : 
0x000F Binary Input
0x0402 Temperature Measurement
0x8008 Differential pressure measurements
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Ventilo 50-70-166", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Clusters:<ul>",
	    "<li>000F Binary Input</li>", 	
	    "<li>0402 Temperature Measurement</li>", 
	    "<li>0403 Pressure Measurement</li>", 
	    "<li>8008 Differential Pressure Measurements</li>",					    
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeVentiloTest extends TestBase {
	
	private final String SCRIPT_PATH = "nke/nkePublicV2_8";


    @Test
    public void should_profile() throws JsDecodingException {
        String input = "1101000f040288230000005a";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:count, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0402_cmd01_payload() throws JsDecodingException {
        /* real payload */
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
        String input = "11010402000200290640";
        
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
        assertThat(temperatureMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(temperatureMeasurement.getMaximum().getValue()).isEqualTo(16);
        assertThat(temperatureMeasurement.getMaximum().getUnit()).isEqualTo("°C");
    }
   
    
    @Test
    @PayloadDescription(name = "ClusterTemperatureMeasurement_MaxMeasuredValue", description = "Cluster: Temperature Measurement, Attribut: MaxMeasuredValue, Command: Threshold Report.")
    public void should_decode_0402_TemperatureMeasurement_attribut0002_cmd8A_payload() throws JsDecodingException {
        /* real payload */
        String input = "118A040200022901C298D2";
        
        NKEClusterTemperatureMeasurement temperatureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterTemperatureMeasurement.class);
        assertThat(temperatureMeasurement.getMessageType()).isEqualTo("Temperature Measurement");
        assertThat(temperatureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(temperatureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(temperatureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
        assertThat(temperatureMeasurement.getMaximum().getValue()).isEqualTo(4.5f);
        assertThat(temperatureMeasurement.getMaximum().getUnit()).isEqualTo("°C");
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
@PayloadDescription(name = "ClusterPressureMeasurement_MeasuredValue", description = "Cluster: Pressure Measurement, Attribut: MeasuredValue, Command: Report attributes.")
public void should_decode_0403_PressureMeasurement_attribut0000_cmd0A_payload() throws JsDecodingException {
    /* payload build for test */
    String input = "110A04030000290aea";
    
    NKEClusterPressureMeasurement pressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterPressureMeasurement.class);
    assertThat(pressureMeasurement.getMessageType()).isEqualTo("Pressure Measurement");
    assertThat(pressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(pressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
    assertThat(pressureMeasurement.getAttributId()).isEqualTo("MeasuredValue");
    assertThat(pressureMeasurement.getMeasure().getValue()).isEqualTo(2794);
    assertThat(pressureMeasurement.getMeasure().getUnit()).isEqualTo("hPa");
}

@Test
@PayloadDescription(name = "ClusterPressureMeasurement_MeasuredValue", description = "Cluster: Pressure Measurement, Attribut: MeasuredValue, Command: ZCL-like threshold report.")
public void should_decode_0403_PressureMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
	/* payload build for test */
    String input = "118A040300002901C298D2";
    
    NKEClusterPressureMeasurement pressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterPressureMeasurement.class);
    assertThat(pressureMeasurement.getMessageType()).isEqualTo("Pressure Measurement");
    assertThat(pressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(pressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
    assertThat(pressureMeasurement.getAttributId()).isEqualTo("MeasuredValue");
    assertThat(pressureMeasurement.getMeasure().getValue()).isEqualTo(450);
    assertThat(pressureMeasurement.getMeasure().getUnit()).isEqualTo("hPa");
    assertThat(pressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
    assertThat(pressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
    assertThat(pressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    assertThat(pressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);

}


@Test
@PayloadDescription(name = "ClusterPressureMeasurement_MinMeasuredValue", description = "Cluster: Pressure Measurement, Attribut: MinMeasuredValue, Command: Read Attribute Response.")
public void should_decode_0403_PressureMeasurement_attribut0001_cmd01_payload() throws JsDecodingException {
	/* payload build for test */
    String input = "1101040300010029f060";
    
    NKEClusterPressureMeasurement pressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterPressureMeasurement.class);
    assertThat(pressureMeasurement.getMessageType()).isEqualTo("Pressure Measurement");
    assertThat(pressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(pressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
    assertThat(pressureMeasurement.getAttributId()).isEqualTo("MinMeasuredValue");
    assertThat(pressureMeasurement.getStatus()).isEqualTo("Ok");
    assertThat(pressureMeasurement.getMinimum().getValue()).isEqualTo(-4000);
    assertThat(pressureMeasurement.getMinimum().getUnit()).isEqualTo("hPa");
}

@Test
@PayloadDescription(name = "ClusterPressureMeasurement_MaxMeasuredValue", description = "Cluster: Pressure Measurement, Attribut: MaxMeasuredValue, Command: Read Attribute Response.")
public void should_decode_0403_PressureMeasurement_attribut0002_cmd01_payload() throws JsDecodingException {
	/* payload build for test */
    String input = "11010403000200290640";
    
    NKEClusterPressureMeasurement pressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterPressureMeasurement.class);
    assertThat(pressureMeasurement.getMessageType()).isEqualTo("Pressure Measurement");
    assertThat(pressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(pressureMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
    assertThat(pressureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
    assertThat(pressureMeasurement.getStatus()).isEqualTo("Ok");
    assertThat(pressureMeasurement.getMaximum().getValue()).isEqualTo(1600);
    assertThat(pressureMeasurement.getMaximum().getUnit()).isEqualTo("hPa");
}


@Test
@PayloadDescription(name = "ClusterPressureMeasurement_MaxMeasuredValue", description = "Cluster: Pressure Measurement, Attribut: MaxMeasuredValue, Command: Threshold Report.")
public void should_decode_0403_PressureMeasurement_attribut0002_cmd8A_payload() throws JsDecodingException {
	/* payload build for test */
    String input = "118A040300022901C298D2";
    
    NKEClusterPressureMeasurement pressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterPressureMeasurement.class);
    assertThat(pressureMeasurement.getMessageType()).isEqualTo("Pressure Measurement");
    assertThat(pressureMeasurement.getEndpoint()).isEqualTo("0");
    assertThat(pressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
    assertThat(pressureMeasurement.getAttributId()).isEqualTo("MaxMeasuredValue");
    assertThat(pressureMeasurement.getMaximum().getValue()).isEqualTo(450);
    assertThat(pressureMeasurement.getMaximum().getUnit()).isEqualTo("hPa");
    assertThat(pressureMeasurement.getReportParameters().getBatch()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getNoHeaderPort()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getSecured()).isEqualTo(false);
    assertThat(pressureMeasurement.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
    assertThat(pressureMeasurement.getReportParameters().getCauseRequest()).isEqualTo("short");
    assertThat(pressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    assertThat(pressureMeasurement.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
    assertThat(pressureMeasurement.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
    
}


    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd01_payload() throws JsDecodingException {
    	/* payload build for test */
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
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_payload() throws JsDecodingException {
    	/* payload build for test */
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
    }
    
    @Test
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_cr_short2_payload2() throws JsDecodingException {
    	/* payload build for test */
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
    }
        
    @Test
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
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);       
    }
    
        
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Measured value, Command: Threshold Report.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0000_cmd8A_cr_long2_payload2() throws JsDecodingException {
    	/* payload build for test */
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
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);       
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);
              
    }
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MinMeasuredValue", description = "Cluster: Differential pressure measurements, Attribut:Min measured value, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0001_cmd01_payload() throws JsDecodingException {
    	/* payload build for test */
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
    	/* payload build for test */
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
    	/* payload build for test */
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
    	/* payload build for test */
        String input = "110A80080004211000";
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Report Attributes");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Samples per measurement");
        assertThat(differentialPressureMeasurement.getSamplesPerMeasurement()).isEqualTo(4096);        
    }
    
    @Test
     public void should_decode_8008_DifferentialPressureMeasurement_attribut0004_cmd8A_payload() throws JsDecodingException {
    	/* payload build for test */
        String input = "118A80080004211000A8B2000A00018000010002D3000B00018000020003";
                        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Samples per measurement");
        assertThat(differentialPressureMeasurement.getSamplesPerMeasurement()).isEqualTo(4096);   
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
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);       
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);
    }
   
    
    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_SamplesPerConfirmationMeasurement", description = "Cluster: Differential pressure measurements, Attribut:Samples per confirmation measurement, Command: Report Attributes.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0005_cmd0A_payload() throws JsDecodingException {
    	/* payload build for test */
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
    	/* payload build for test */
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
    	/* payload build for test */
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
   
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0100_cmd8A_payload() throws JsDecodingException {
    	/* payload build for test */
        String input = "118A80080100291000A8B2000A00018000010002D3000B00018000020003";
                        
        
        NKEClusterDifferentialPressureMeasurement differentialPressureMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterDifferentialPressureMeasurement.class);
        assertThat(differentialPressureMeasurement.getMessageType()).isEqualTo("Differential Pressure Measurement");
        assertThat(differentialPressureMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(differentialPressureMeasurement.getCommandId()).isEqualTo("Threshold Report");
        assertThat(differentialPressureMeasurement.getAttributId()).isEqualTo("Mean measured value since last report");
        assertThat(differentialPressureMeasurement.getMeanMeasuredValueSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(differentialPressureMeasurement.getMeanMeasuredValueSinceLastReport().getValue()).isEqualTo(4096); 
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
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getValue()).isEqualTo(10);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);       
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getValue()).isEqualTo(11);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getGap()).isEqualTo(1);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(differentialPressureMeasurement.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);
    }

    
    @Test
    @PayloadDescription(name = "ClusterDifferentialPressureMeasurement_MinimalMeasuredValueSinceLastReport", description = "Cluster: Differential pressure measurements, Attribut:Minimal measured value since last report, Command: Read Attribute Response.")
    public void should_decode_8008_DifferentialPressureMeasurement_attribut0101_cmd01_payload() throws JsDecodingException {
    	/* payload build for test */
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
    	/* payload build for test */
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
    
    
}
