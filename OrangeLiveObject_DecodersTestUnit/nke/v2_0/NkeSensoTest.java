package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterSenso;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterVolumeMeter;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Senso 50-70-011", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "1.0",
                "</h5>",
                "Clusters:<ul>",
                "<li>8002 Volume meter</li>",
                "<li>8003 Senso</li>",
                "</ul>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeSensoTest extends TestBase {

    private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "310A800200002B11113344";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }

    @Test
    @PayloadDescription(name = "ClusterSensoVolumeMeter", description = "Cluster: SensoVolumeMeter, Attribut: volume, Command: Report Attributes.")
    public void should_decode_8002_SensoVolumeMeter_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310A800200002B11113344";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("1");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("Volume");
        assertThat(clusterVolumeMeter.getVolumeIndex()).isEqualTo(286339908);
    }

    @Test
    public void should_decode_8002_SensoVolumeMeter_attribut0000_cmd8A_short_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "118A800200002B1111334498B2D3";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("0");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Threshold Report");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("Volume");
        assertThat(clusterVolumeMeter.getVolumeIndex()).isEqualTo(286339908);
        assertThat(clusterVolumeMeter.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getReportParameters().getCauseRequest()).isEqualTo("short");
        assertThat(clusterVolumeMeter.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);

    }

    @Test
    public void should_decode_8002_SensoVolumeMeter_attribut0000_cmd8A_long_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "118A800200002B11113344A8B24201e575000000018000010002D34201e5763F8000008000020003";


        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("0");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Threshold Report");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("Volume");
        assertThat(clusterVolumeMeter.getVolumeIndex()).isEqualTo(286339908);
        assertThat(clusterVolumeMeter.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(clusterVolumeMeter.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getValue()).isEqualTo(1107420533);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(clusterVolumeMeter.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getValue()).isEqualTo(1107420534);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getGap()).isEqualTo(1065353216);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(clusterVolumeMeter.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);

    }

    @Test
    @PayloadDescription(name = "ClusterSensoVolumeMeter", description = "Cluster: SensoVolumeMeter, Attribut: VolumeDisplayMode, Command: Report Attributes.")
    public void should_decode_8002_SensoVolumeMeter_attribut0001_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180020001002001";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("1");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("VolumeDisplayMode");
        assertThat(clusterVolumeMeter.getStatus()).isEqualTo("Ok");
        assertThat(clusterVolumeMeter.getVolumeUnit()).isEqualTo("Liter");
    }

    @Test
    @PayloadDescription(name = "ClusterSensoVolumeMeter", description = "Cluster: SensoVolumeMeter, Attribut: MinFlow, Command: Report Attributes.")
    public void should_decode_8002_SensoVolumeMeter_attribut0002_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180020002002810";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("1");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("MinFlow");
        assertThat(clusterVolumeMeter.getStatus()).isEqualTo("Ok");
        assertThat(clusterVolumeMeter.getMinimumFlow()).isEqualTo(16);
    }

    @Test
    @PayloadDescription(name = "ClusterSensoVolumeMeter", description = "Cluster: SensoVolumeMeter, Attribut: MaxFlow, Command: Report Attributes.")
    public void should_decode_8002_SensoVolumeMeter_attribut0003_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3101800200030028FF";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("1");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("MaxFlow");
        assertThat(clusterVolumeMeter.getStatus()).isEqualTo("Ok");
        assertThat(clusterVolumeMeter.getMaximumFlow()).isEqualTo(255);
    }

    @Test
    @PayloadDescription(name = "ClusterSensoVolumeMeter", description = "Cluster: SensoVolumeMeter, Attribut: FlowDisplayMode, Command: Report Attributes.")
    public void should_decode_8002_SensoVolumeMeter_attribut0004_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180020004002000";

        NKEClusterVolumeMeter clusterVolumeMeter = formatAndDecode(SCRIPT_PATH, input, NKEClusterVolumeMeter.class);
        assertThat(clusterVolumeMeter.getMessageType()).isEqualTo("Senso Volume Meter");
        assertThat(clusterVolumeMeter.getEndpoint()).isEqualTo("1");
        assertThat(clusterVolumeMeter.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterVolumeMeter.getAttributId()).isEqualTo("FlowDisplayMode");
        assertThat(clusterVolumeMeter.getStatus()).isEqualTo("Ok");
        assertThat(clusterVolumeMeter.getFlowDisplayMode()).isEqualTo("Max: Liter/min, Min: dLiter/Hour");
    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: Senso, Attribut: Status, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310A800300001803";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterSenso.getAttributId()).isEqualTo("Status");
        assertThat(clusterSenso.getSensorStatus().getLeak()).isEqualTo("Ok");
        assertThat(clusterSenso.getSensorStatus().getBackWaterLevel1()).isEqualTo("Ok");
        assertThat(clusterSenso.getSensorStatus().getBackWaterLevel2()).isEqualTo("Nok");
        assertThat(clusterSenso.getSensorStatus().getBackWaterLevel2()).isEqualTo("Nok");
        assertThat(clusterSenso.getSensorStatus().getFraud()).isEqualTo("Nok");
        assertThat(clusterSenso.getSensorStatus().getBattery()).isEqualTo("Nok");
        assertThat(clusterSenso.getSensorStatus().getInstallation()).isEqualTo("Nok");
        assertThat(clusterSenso.getSensorStatus().getFreeze()).isEqualTo("Nok");

    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: CountDownThresholds, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0001_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180030001004106111122223333";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("CountDownThresholds");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getCountdown1threshold()).isEqualTo(4369);
        assertThat(clusterSenso.getCountdown2threshold()).isEqualTo(8738);
        assertThat(clusterSenso.getCountdown3threshold()).isEqualTo(13107);

    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: InstallationRotation, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0002_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180030002002011";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("InstallationRotation");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getInstallationRotation()).isEqualTo(17);
    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: VolumeRotation, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0003_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "31018003000300210011";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("VolumeRotation");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getVolumeRotation()).isEqualTo(17);
    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: TemperatureMeterFreeze, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0004_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180030004002811";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("TemperatureMeterFreeze");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getTemperatureMeterFreeze().getValue()).isEqualTo(17);
        assertThat(clusterSenso.getTemperatureMeterFreeze().getUnit()).isEqualTo("°C");
    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: TemperatureMinTxOff, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0005_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "310180030005002811";

        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("TemperatureMinTxOff");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getTemperatureMinTxOff().getValue()).isEqualTo(17);
        assertThat(clusterSenso.getTemperatureMinTxOff().getUnit()).isEqualTo("°C");
    }

    @Test
    @PayloadDescription(name = "ClusterSenso", description = "Cluster: SensoVolumeMeter, Attribut: ParametersLeakFlow, Command: Report Attributes.")
    public void should_decode_8003_Senso_attribut0006_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3101800300060041050200200011";


        NKEClusterSenso clusterSenso = formatAndDecode(SCRIPT_PATH, input, NKEClusterSenso.class);
        assertThat(clusterSenso.getMessageType()).isEqualTo("Senso");
        assertThat(clusterSenso.getEndpoint()).isEqualTo("1");
        assertThat(clusterSenso.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterSenso.getAttributId()).isEqualTo("ParametersLeakFlow");
        assertThat(clusterSenso.getStatus()).isEqualTo("Ok");
        assertThat(clusterSenso.getVolumeThreshold().getValue()).isEqualTo(2);
        assertThat(clusterSenso.getVolumeThreshold().getUnit()).isEqualTo("Leter");
        assertThat(clusterSenso.getPeriodCalculateAverageLeakFlow().getUnit()).isEqualTo("minutes");
        assertThat(clusterSenso.getPeriodCalculateAverageLeakFlow().getValue()).isEqualTo(32);
        assertThat(clusterSenso.getPeriodObservationLeakFlow().getUnit()).isEqualTo("hours");
        assertThat(clusterSenso.getPeriodObservationLeakFlow().getValue()).isEqualTo(17);
    }


}
