package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterMultiStateOutput;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

/* Tests clusters  :                 
0x0013 Multistate Output
 * 
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Pilote Wire LoRa Remote 50-70-027", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
					version = { "1.0" ,
							"</h5>",
							"Cluster:<ul>",
							"<li>0013 Multistate Output</li>",						    
						    "</ul>",
						    "<h5>"}, 
					encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NkePiloteWireLoRaRemoteTest extends TestBase {
	private final String SCRIPT_PATH = "nke/nkePublicV2_8";
	

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "11010013004A002011";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	
	
    @Test
    @PayloadDescription(name = "ClusterMultiStateOutput_NumberOfStates", description = "Cluster: ClusterMultiStateOutput, Attribut: NumberOfStates, Command: Read Attribute Response.")
    public void should_decode_0013_ClusterMultiStateOutput_attribut004A_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "11010013004A002011";
        
        NKEClusterMultiStateOutput multiStateOutput = formatAndDecode(SCRIPT_PATH, input, NKEClusterMultiStateOutput.class);
        assertThat(multiStateOutput.getMessageType()).isEqualTo("MultiState Output");
        assertThat(multiStateOutput.getEndpoint()).isEqualTo("0");
        assertThat(multiStateOutput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(multiStateOutput.getAttributId()).isEqualTo("NumberOfStates");
        assertThat(multiStateOutput.getStatus()).isEqualTo("Ok");
        assertThat(multiStateOutput.getNumberOfStates()).isEqualTo(17);
        
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterMultiStateOutput_ApplicationType", description = "Cluster: ClusterMultiStateOutput, Attribut: ApplicationType, Command: Read Attribute Response.")
    public void should_decode_0013_ClusterMultiStateOutput_attribut0100_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "11010013010000230E002000";
        
        NKEClusterMultiStateOutput multiStateOutput = formatAndDecode(SCRIPT_PATH, input, NKEClusterMultiStateOutput.class);
        assertThat(multiStateOutput.getMessageType()).isEqualTo("MultiState Output");
        assertThat(multiStateOutput.getEndpoint()).isEqualTo("0");
        assertThat(multiStateOutput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(multiStateOutput.getAttributId()).isEqualTo("ApplicationType");
        assertThat(multiStateOutput.getStatus()).isEqualTo("Ok");
        assertThat(multiStateOutput.getApplicationType()).isEqualTo("IPSensor-PilotWire implements : Pilot wire");       
    }
    
    @Test
    @PayloadDescription(name = "ClusterMultiStateOutput_PresentValue", description = "Cluster: ClusterMultiStateOutput, Attribut: PresentValue, Command: Read Attribute Response.")
    public void should_decode_0013_ClusterMultiStateOutput_attribut0055_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100130055002011";
        
        NKEClusterMultiStateOutput multiStateOutput = formatAndDecode(SCRIPT_PATH, input, NKEClusterMultiStateOutput.class);
        assertThat(multiStateOutput.getMessageType()).isEqualTo("MultiState Output");
        assertThat(multiStateOutput.getEndpoint()).isEqualTo("0");
        assertThat(multiStateOutput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(multiStateOutput.getAttributId()).isEqualTo("PresentValue");
        assertThat(multiStateOutput.getStatus()).isEqualTo("Ok");
        assertThat(multiStateOutput.getPresentValue()).isEqualTo(17);       
    }
    

}
