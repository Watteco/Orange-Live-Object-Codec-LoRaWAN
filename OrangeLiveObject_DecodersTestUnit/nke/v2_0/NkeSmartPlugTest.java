package com.orange.lo.decoder.js.nke.v2_0;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterOnOff;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterSimpleMetering;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterPowerQuality;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Smart Plug 50-70-003", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
version = { "1.0" ,
		"</h5>",
		"Clusters:<ul>",
		"<li>0006 ON/OFF</li>",
	    "<li>0052 Simple Metering Like</li>", 
	    "<li>8052 Power Quality</li>", 						    
	    "</ul>",
	    "<h5>"}, 
encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeSmartPlugTest extends TestBase {

	private final String SCRIPT_PATH = "nke/nkePublicV2_8";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "310A000600001000";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

    @Test
    @PayloadDescription(name = "ClusterOnOff_OnOff", description = "Cluster: OnOff, Attribut: onOff relay state, Command: Report Attributes.")
    public void should_decode_0006_onoff_attribut0000_cmd0A_payload() throws JsDecodingException {
    	/* payload build for test */
        String input = "310A000600001000";
        
        NKEClusterOnOff clusterOnoff = formatAndDecode(SCRIPT_PATH, input, NKEClusterOnOff.class);
        assertThat(clusterOnoff.getMessageType()).isEqualTo("On/Off");
        assertThat(clusterOnoff.getEndpoint()).isEqualTo("1");
        assertThat(clusterOnoff.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterOnoff.getAttributId()).isEqualTo("OnOFF relay state");
        assertThat(clusterOnoff.getRelayState()).isEqualTo("OFF");
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterSimpleMeteringLike_CurrentCalibration", description = "Cluster: Simple Metering-Like, Attribut: current Calibration, Command: Read Attribute Response, current calibration coefficient.")
    public void should_decode_0052_SimpleMetering_attribut8000_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "11010052800000410910EEEEDDDDCCCCBBBB";

        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("0");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current calibration coefficient");
        assertThat(simpleMetering.getStatus()).isEqualTo("Ok");
        assertThat(simpleMetering.getCurrentE2Pot()).isEqualTo(16);
        assertThat(simpleMetering.getActivePowerMultiplier()).isEqualTo(-4370.0f);
        assertThat(simpleMetering.getActivePowerDivisor()).isEqualTo(-8739.0f);
        assertThat(simpleMetering.getReactivePowerMultiplier()).isEqualTo(-13108.0f);
        assertThat(simpleMetering.getReactivePowerDivisor()).isEqualTo(-17477.0f);
    }

    @Test
    public void should_decode_0052_SimpleMetering_attribut0000_cmd07_payload() throws JsDecodingException {
        /* real payload */
        String input = "3107005200000000";
        
        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("1");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current metering");
        assertThat(simpleMetering.getStatus()).isEqualTo("Ok");
        assertThat(simpleMetering.getReport()).isEqualTo("Standard report");
    }

    @Test
    @PayloadDescription(name = "ClusterSimpleMeteringLike_CurrentMetering", description = "Cluster: Simple Metering-Like, Attribut: current metering, Command: Report Attributes. Give the consumption of the remote SmartPlug.")
    public void should_decode_0052_SimpleMetering_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* real payload */
        String input = "310a00520000410c000047ffffbe0063002fffd6";
        
        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("1");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Report Attributes");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current metering");
        assertThat(simpleMetering.getActiveEnergySum().getValue()).isEqualTo(71.0f);
        assertThat(simpleMetering.getActiveEnergySum().getUnit()).isEqualTo("Wh");
        assertThat(simpleMetering.getReactiveEnergySum().getValue()).isEqualTo(-66.0f);
        assertThat(simpleMetering.getReactiveEnergySum().getUnit()).isEqualTo("VARh");
        assertThat(simpleMetering.getActivePower().getValue()).isEqualTo(47.0f);
        assertThat(simpleMetering.getActivePower().getUnit()).isEqualTo("W");
        assertThat(simpleMetering.getReactivePower().getValue()).isEqualTo(-42.0f);
        assertThat(simpleMetering.getReactivePower().getUnit()).isEqualTo("VAR");
    }
    
    @Test
    @PayloadDescription(name = "ClusterPowerQuality_Current", description = "Cluster: PowerQuality, Attribut: current , Command: Report Attributes.")
    public void should_decode_8052_PowerQuality_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* real payload */
        String input = "310a80520000411822221111FFFF000100000003001100002222000100020003";
        
        NKEClusterPowerQuality powerQuality = formatAndDecode(SCRIPT_PATH, input, NKEClusterPowerQuality.class);
        assertThat(powerQuality.getMessageType()).isEqualTo("Power Quality");
        assertThat(powerQuality.getEndpoint()).isEqualTo("1");
        assertThat(powerQuality.getCommandId()).isEqualTo("Report Attributes");
        assertThat(powerQuality.getAttributId()).isEqualTo("Current");
        assertThat(powerQuality.getCurrent().getFreq().getUnit()).isEqualTo("Hz");
        assertThat(powerQuality.getCurrent().getFreq().getFreq()).isEqualTo(30.97f);
        assertThat(powerQuality.getCurrent().getFreq().getFreqmin()).isEqualTo(26.601f);
        assertThat(powerQuality.getCurrent().getFreq().getFreqmax()).isEqualTo(87.767f);
        assertThat(powerQuality.getCurrent().getVrms().getUnit()).isEqualTo("V");
        assertThat(powerQuality.getCurrent().getVrms().getVrms()).isEqualTo(0.1f);
        assertThat(powerQuality.getCurrent().getVrms().getVrmsmin()).isEqualTo(0f);
        assertThat(powerQuality.getCurrent().getVrms().getVrmsmax()).isEqualTo(0.3f);
        assertThat(powerQuality.getCurrent().getVpeak().getUnit()).isEqualTo("V");
        assertThat(powerQuality.getCurrent().getVpeak().getVpeak()).isEqualTo(1.7f);
        assertThat(powerQuality.getCurrent().getVpeak().getVpeakmin()).isEqualTo(0f);
        assertThat(powerQuality.getCurrent().getVpeak().getVpeakmax()).isEqualTo(873.8f);
        assertThat(powerQuality.getCurrent().getOverVoltageNumber()).isEqualTo(1);
        assertThat(powerQuality.getCurrent().getSagNumber()).isEqualTo(2);
        assertThat(powerQuality.getCurrent().getBrownoutNumber()).isEqualTo(3);
        
    }
    
    @Test
    @PayloadDescription(name = "ClusterPowerQuality_SagCycleThreshold", description = "Cluster: PowerQuality, Attribut: sag cycle threshold , Command: Read Attribute Response.")
    public void should_decode_8052_PowerQuality_attribut0001_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "310180520001002010";
        
        NKEClusterPowerQuality powerQuality = formatAndDecode(SCRIPT_PATH, input, NKEClusterPowerQuality.class);
        assertThat(powerQuality.getMessageType()).isEqualTo("Power Quality");
        assertThat(powerQuality.getEndpoint()).isEqualTo("1");
        assertThat(powerQuality.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(powerQuality.getAttributId()).isEqualTo("Sag Cycle Threshold");
        assertThat(powerQuality.getStatus()).isEqualTo("Ok");
        assertThat(powerQuality.getSagCycleThreshold()).isEqualTo(16);

    } 
    
    @Test
    @PayloadDescription(name = "ClusterPowerQuality_SagVoltageThreshold", description = "Cluster: PowerQuality, Attribut: sag voltage threshold , Command: Read Attribute Response.")
    public void should_decode_8052_PowerQuality_attribut0002_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "31018052000200212222";
        
        NKEClusterPowerQuality powerQuality = formatAndDecode(SCRIPT_PATH, input, NKEClusterPowerQuality.class);
        assertThat(powerQuality.getMessageType()).isEqualTo("Power Quality");
        assertThat(powerQuality.getEndpoint()).isEqualTo("1");
        assertThat(powerQuality.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(powerQuality.getAttributId()).isEqualTo("Sag Voltage Threshold");
        assertThat(powerQuality.getStatus()).isEqualTo("Ok");
        assertThat(powerQuality.getSagVoltageThreshold().getUnit()).isEqualTo("V");
        assertThat(powerQuality.getSagVoltageThreshold().getValue()).isEqualTo(873.8f);

    } 
  
    @Test
    @PayloadDescription(name = "ClusterPowerQuality_OverVoltageThreshold", description = "Cluster: PowerQuality, Attribut: over voltage threshold , Command: Read Attribute Response.")
    public void should_decode_8052_PowerQuality_attribut0003_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "31018052000300212222";
        
        NKEClusterPowerQuality powerQuality = formatAndDecode(SCRIPT_PATH, input, NKEClusterPowerQuality.class);
        assertThat(powerQuality.getMessageType()).isEqualTo("Power Quality");
        assertThat(powerQuality.getEndpoint()).isEqualTo("1");
        assertThat(powerQuality.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(powerQuality.getAttributId()).isEqualTo("Over Voltage Threshold");
        assertThat(powerQuality.getStatus()).isEqualTo("Ok");
        assertThat(powerQuality.getOverVoltageThreshold().getUnit()).isEqualTo("V");
        assertThat(powerQuality.getOverVoltageThreshold().getValue()).isEqualTo(873.8f);

    } 
  
    
    
    
}
