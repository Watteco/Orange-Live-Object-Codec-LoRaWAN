package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBasic;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterConcentrationMeasurement;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterConfiguration;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterLoRaWAN;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/* Tests clusters communs à tous les capteurs */
@DeviceDescription(name = "All Watteco (NKE-Watteco) sensors", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "1.0",
                "</h5>",
                "Common clusters:<ul>",
                "<li>0050 Configuration</li>",
                "<li>0000 Basic</li>",
                "<li>8004 Lorawan</li>",
                "</ul>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class NkeTest extends TestBase {

    private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "110100000002000d030400000dd2";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }

    /*
     * @Test
     * 
     * @Ignore //fonctions récursives volontairement laissées dans le script
     * public void should_check_script() throws JsDecodingException {
     * checkScript(SCRIPT_PATH);
     * }
     */

    @Test
    public void should_decode_bad_payload() throws JsDecodingException {
        formatAndDecode(SCRIPT_PATH, "08", Object.class);
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_FirmwareVersion", description = "Cluster: Basic, Attribut: Firmware Version, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0002_cmd07_payload() throws JsDecodingException {
        /* real payload */
        String input = "110100000002000d030400000dd2";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Firmware Version");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getFirmware().getVersion()).isEqualTo("3.4.0");
        assertThat(clusterBasic.getFirmware().getRCBuild()).isEqualTo(3538);
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_KernelVersion", description = "Cluster: Basic, Attribut: Kernel Version, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0003_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100000003004203343332";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Kernel Version");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getKernelVersion()).isEqualTo("432");
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_Manufacturer", description = "Cluster: Basic, Attribut: Manufacturer, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0004_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100000004004206414243444546";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Manufacturer");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getManufacturer()).isEqualTo("ABCDEF");
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_ModelIdentifier", description = "Cluster: Basic, Attribut: Model identifier, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0005_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100000005004206414243444546";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Model Identifier");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getModelIdentifier()).isEqualTo("ABCDEF");
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_DateCode", description = "Cluster: Basic, Attribut: Date Code, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0006_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000000060042083134303432303138";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Date Code");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getDateCode()).isEqualTo("14042018");
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_DateCode", description = "Cluster: Basic, Attribut: Location Description, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut0010_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100000010004203434343";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Location Description");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getLocationDescription()).isEqualTo("CCC");
    }

    @Test
    @PayloadDescription(name = "ClusterBasic_ApplicationName", description = "Cluster: Basic, Attribut: Application Name, Command: Read Attribute Response.")
    public void should_decode_0000_basic_attribut8001_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100008001004206414243444546";

        NKEClusterBasic clusterBasic = formatAndDecode(SCRIPT_PATH, input, NKEClusterBasic.class);
        assertThat(clusterBasic.getMessageType()).isEqualTo("Basic");
        assertThat(clusterBasic.getEndpoint()).isEqualTo("0");
        assertThat(clusterBasic.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBasic.getAttributId()).isEqualTo("Application Name");
        assertThat(clusterBasic.getStatus()).isEqualTo("Ok");
        assertThat(clusterBasic.getApplicationName()).isEqualTo("ABCDEF");
    }

    @Test
    public void should_decode_0050_configuration_attribut0005_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110100500005000A010001";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Current configuration mode status");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Ok");
        assertThat(clusterConfiguration.getMode()).isEqualTo("Configuration mode RX (Always awake)");
        assertThat(clusterConfiguration.getRemainingDuration().getValue()).isEqualTo(1);
        assertThat(clusterConfiguration.getRemainingDuration().getUnit()).isEqualTo("seconds");
    }

    @Test
    @PayloadDescription(name = "ClusterConfiguration_CurrentConfigurationModeStatus", description = "Cluster: Configuration, Attribut: Current configuration mode status, Command: Report Attributes.")
    public void should_decode_0050_configuration_attribut0005_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310A005000050A020012";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("1");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Current configuration mode status");
        assertThat(clusterConfiguration.getMode()).isEqualTo("Configuration mode NS (NS each 3 seconds)");
        assertThat(clusterConfiguration.getRemainingDuration().getValue()).isEqualTo(18);
        assertThat(clusterConfiguration.getRemainingDuration().getUnit()).isEqualTo("seconds");
    }

    @Test
    public void should_decode_0050_configuration_attribut0005_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1107005000000005";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Current configuration mode status");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Ok");
        assertThat(clusterConfiguration.getReport()).isEqualTo("Standard report");
    }

    @Test
    public void should_decode_0050_configuration_attribut0006_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "110100500006004109030E0BF40000030002";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Node power descriptor");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Ok");
        assertThat(clusterConfiguration.getCurrentPowerMode()).isEqualTo("Reserved");
        assertThat(clusterConfiguration.getPowerSources().getConstOrExtPower().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getRechargBattery().getAvailable()).isTrue();
        assertThat(clusterConfiguration.getPowerSources().getRechargBattery().getValue()).isEqualTo(3.06f);
        assertThat(clusterConfiguration.getPowerSources().getRechargBattery().getUnit()).isEqualTo("V");
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getAvailable()).isTrue();
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getValue()).isEqualTo(0.0f);
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getUnit()).isEqualTo("V");
        assertThat(clusterConfiguration.getPowerSources().getSolHarvesting().getAvailable()).isTrue();
        assertThat(clusterConfiguration.getPowerSources().getSolHarvesting().getValue()).isEqualTo(0.768f);
        assertThat(clusterConfiguration.getPowerSources().getSolHarvesting().getUnit()).isEqualTo("V");
        assertThat(clusterConfiguration.getPowerSources().getTicHarvesting().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getCurrentPowerSource()).isEqualTo("Rechargeable Battery");
    }

    @Test
    @PayloadDescription(name = "ClusterConfiguration_NodePowerDescriptor", description = "Cluster: Configuration, Attribut: Node power descriptor, Command: Report Attributes.")
    public void should_decode_0050_configuration_attribut0006_cmd0A_payload() throws JsDecodingException {
        /* real payload */
        String input = "110a00500006410500010cf501";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Node power descriptor");
        assertThat(clusterConfiguration.getCurrentPowerMode()).isEqualTo("ON when idle");
        assertThat(clusterConfiguration.getPowerSources().getConstOrExtPower().getAvailable()).isTrue();
        assertThat(clusterConfiguration.getPowerSources().getConstOrExtPower().getValue()).isEqualTo(3.317f);
        assertThat(clusterConfiguration.getPowerSources().getConstOrExtPower().getUnit()).isEqualTo("V");
        assertThat(clusterConfiguration.getPowerSources().getRechargBattery().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getSolHarvesting().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getTicHarvesting().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getCurrentPowerSource()).isEqualTo("Constant (Main) Or External Power");
    }
    
    @Test
    @PayloadDescription(name = "ClusterConfiguration_NodePowerDescriptor", description = "Cluster: Configuration, Attribut: Node power descriptor, Command: Report Attributes.")
    public void should_decode_0050_configuration_attribut0006_cmd8A_payload() throws JsDecodingException {
        /* real payload */
        String input = "118a00500006410501040c540498d0";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Threshold Report");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Node power descriptor");
        assertThat(clusterConfiguration.getCurrentPowerMode()).isEqualTo("Periodically ON");
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getAvailable()).isTrue();
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getValue()).isEqualTo(3.156f);
        assertThat(clusterConfiguration.getPowerSources().getDiposBattery().getUnit()).isEqualTo("V");
        assertThat(clusterConfiguration.getPowerSources().getRechargBattery().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getConstOrExtPower().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getSolHarvesting().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getPowerSources().getTicHarvesting().getAvailable()).isFalse();
        assertThat(clusterConfiguration.getCurrentPowerSource()).isEqualTo("Diposable Battery");
    }

    @Test
    public void should_decode_0050_configuration_attribut0006_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3107005081000006";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("1");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(clusterConfiguration.getAttributId()).isEqualTo("Node power descriptor");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Unsupported Cluster Command");
        assertThat(clusterConfiguration.getReport()).isEqualTo("Standard report");
    }

    @Test
    @PayloadDescription(name = "ClusterConfiguration_ReadAttributeResponseInError", description = "Cluster: Configuration, inexisting attribut, Command: Read attribute response.")
    public void should_decode_0050_configuration_attribut0106_cmd01_error_payload() throws JsDecodingException {
        /* real payload */
        String input = "11010050010686";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("0");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Unsupported Attribute");
    }

    @Test
    @PayloadDescription(name = "ClusterConfiguration_ConfigureReportingResponseInError", description = "Cluster: Configuration, Command: Configure Reporting Response : Malformed Command")
    public void should_decode_000c_analogInput_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3107005080";

        NKEClusterConfiguration clusterConfiguration = formatAndDecode(SCRIPT_PATH, input, NKEClusterConfiguration.class);
        assertThat(clusterConfiguration.getMessageType()).isEqualTo("Configuration");
        assertThat(clusterConfiguration.getEndpoint()).isEqualTo("1");
        assertThat(clusterConfiguration.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(clusterConfiguration.getStatus()).isEqualTo("Malformed Command");
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_MessageType", description = "Cluster: LoRaWAN, Attribut: MessageType , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0000_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110180040000000801";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("MessageType");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getMessageTypeConfiguration()).isEqualTo("All frames Confirmed (default)");
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_RetryConfirmed", description = "Cluster: LoRaWAN, Attribut: RetryConfirmed , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0001_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110180040001002003";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("RetryConfirmed");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getRetryConfirmedNumber()).isEqualTo(3);
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_ReAssociation", description = "Cluster: LoRaWAN, Attribut: ReAssociation , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0002_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "11018004000200410400FF0070";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("ReAssociation");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getPeriodNoFrameAfterNewAssoc().getValue()).isEqualTo(255);
        assertThat(clusterLorawan.getPeriodNoFrameAfterNewAssoc().getUnit()).isEqualTo("Minutes");
        assertThat(clusterLorawan.getConsecutiveFailureAfterNewAssoc()).isEqualTo(112);
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_DataRate", description = "Cluster: LoRaWAN, Attribut: DataRate , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0003_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101800400030041020101";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("DataRate");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getStartDataRate()).isEqualTo("from 1 Datarate (0 to 5 for EU)");

    }

    @Test
    public void should_decode_8004_lorawan_attribut0003_cmd01_payload2() throws JsDecodingException {
        /* real payload */
        String input = "11018004000300410201ff";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("DataRate");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getStartDataRate()).isEqualTo("from the maximum (default)");
    }

    @Test
    public void should_decode_8004_lorawan_attribut0004_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "110180040004004104ffffffff";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("ABP_DevAddr");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getABP_DevAddress()).isEqualTo("non défini");
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_ABP_DevAddr", description = "Cluster: LoRaWAN, Attribut: ABP_DevAddr , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0004_cmd01_payload2() throws JsDecodingException {
        /* real payload */
        String input = "1101800400040041041F7C6511";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("ABP_DevAddr");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getABP_DevAddress()).isEqualTo("1F7C6511");
    }

    @Test
    @PayloadDescription(name = "ClusterLoRaWAN_OTA_AppEUI", description = "Cluster: LoRaWAN, Attribut: OTA_AppEUI , Command: Read Attribute Response.")
    public void should_decode_8004_lorawan_attribut0005_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "11018004000500410870b3d5e75f600000";

        NKEClusterLoRaWAN clusterLorawan = formatAndDecode(SCRIPT_PATH, input, NKEClusterLoRaWAN.class);
        assertThat(clusterLorawan.getMessageType()).isEqualTo("LoRaWAN");
        assertThat(clusterLorawan.getEndpoint()).isEqualTo("0");
        assertThat(clusterLorawan.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterLorawan.getAttributId()).isEqualTo("OTA_AppEUI");
        assertThat(clusterLorawan.getStatus()).isEqualTo("Ok");
        assertThat(clusterLorawan.getOTA_AppEUI()).isEqualTo("70B3D5E75F600000");

    }

    @Test
    @PayloadDescription(name = "ClusterBasic_FirmwareVersion", description = "Cluster: Basic, Attribut: Firmware Version, Command: Read Attribute Response.")
    public void should_profile_0000_basic_attribut0002_cmd07_payload() throws JsDecodingException {
        /* real payload */
        String input = "110100000002000d030400000dd2";

        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }
}