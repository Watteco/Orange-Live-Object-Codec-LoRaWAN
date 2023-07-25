package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterRelativeHumidityMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTemperatureMeasurement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) TH and Temperature (indoor) 50-70-053 50-70-085", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Clusters:<ul>",
		"<li>0405 Relative Humidity Measurements</li>",
	    "<li>0402 Temperature Measurement</li>", 
	    "<li>000F Binary Input</li>", 						    
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeThAndTemperatureIndoorTest extends TestBase {
	
	private final String SCRIPT_PATH = "nke/nkePublicV2_10";
 
    @Test
    public void should_profile() throws JsDecodingException {
        String input = "11010405000000211346";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
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
        String input = "11010405000100210001";
        
        NKEClusterRelativeHumidityMeasurement humidityMeasurement = formatAndDecode(SCRIPT_PATH, input, NKEClusterRelativeHumidityMeasurement.class);
        assertThat(humidityMeasurement.getMessageType()).isEqualTo("Relative Humidity Measurement");
        assertThat(humidityMeasurement.getEndpoint()).isEqualTo("0");
        assertThat(humidityMeasurement.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(humidityMeasurement.getAttributId()).isEqualTo("MinMeasuredValue");
        assertThat(humidityMeasurement.getStatus()).isEqualTo("Ok");
        assertThat(humidityMeasurement.getMinimum().getValue()).isEqualTo(0.01f);
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

}
