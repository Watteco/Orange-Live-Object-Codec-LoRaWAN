package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTemperatureMeasurement;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Remote temperature 50-70-043", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Cluster:<ul>",
		"<li>0x0402 Temperature Measurement</li>",				    
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeRemoteTemperatureTest extends TestBase {
	
	private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "11010402000000290aea";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
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
	
}
