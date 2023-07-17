package com.orange.lo.decoder.js.nke.v2_0.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class NKEClusterTICinformationCBE extends NKEFrameHeader {
//0054 TIC-CBE
	
	@FieldDescription(description = "Type de compteur. La valeur peut être: <ul> <li>Unknown</li> <li>Concentrateur teleport</li> <li>Compteur Bleu Electrique Monophasé</li> <li>Compteur Bleu Electrique Monophasé ICC</li> "+
									"<li>Compteur Bleu Electrique Triphasé</li> <li>Compteur Jaune Electronique</li> <li>Compteur Interface Clientelle Emeraude</li> <li>TIC Standard (Linky)</li> </ul> ")	
	public String TIC_MeterType;
	
	@FieldDescription(description = "Avertissement de dépassement I")
	public AvertDepassI avertDepassI;
	@FieldDescription(description = "Adresse compteur")
	public String adresseCompteur;
	@FieldDescription(description = "Option tarif. 4 modes de fonctionnement: <ul> <li>Base</li><li>Heure creuse heure pleine</li><li>Tempo</li><li>Effacement des jours de pointe</li> </ul>")
	public String optionTarif;
	@FieldDescription(description = "Intensité souscrite")
	public IntensiteSouscrite intensiteSouscrite;
	@FieldDescription(description = "Index option base")
	public IndexOptionBase indexOptionBase;	
	@FieldDescription(description = "Index heures creuses")
	public IndexHeuresCreuses indexHeuresCreuses;	
	@FieldDescription(description = "Index heures pleines")
	public IndexHeuresPleines indexHeuresPleines;	
	@FieldDescription(description = "EJP heures normales")
	public EjpHeuresNormales ejpHeuresNormales;
	@FieldDescription(description = "EJP heures pointe mobile")
	public EjpHeuresPointeMobile ejpHeuresPointeMobile;	
	@FieldDescription(description = "Tempo heures creuses J bleus")
	public TempoHeures tempoHeuresCreusesJbleus;
	@FieldDescription(description = "Tempo heures pleines J bleus")
	public TempoHeures tempoHeuresPleinesJbleus;
	@FieldDescription(description = "Tempo heures creuses J blancs")
	public TempoHeures tempoHeuresCreusesJblancs;
	@FieldDescription(description = "Tempo heures pleines J blancs")
	public TempoHeures tempoHeuresPleinesJblancs;
	@FieldDescription(description = "Tempo heures creuses J rouges")
	public TempoHeures tempoHeuresCreusesJrouges;
	@FieldDescription(description = "Tempo heures pleines J rouges")
	public TempoHeures tempoHeuresPleinesJrouges;	
	@FieldDescription(description = "Préavis Effacement Jour Pointe")
	public PreavisEJP preavisEJP;
	@FieldDescription(description = "Index gaz")
	public IndexGaz indexGaz;
	@FieldDescription(description = "Index troisième compteur")
	public IndexTroisiemeCompteur indexTroisiemeCompteur;	
	@FieldDescription(description = "Période Tarifaire en cours. Valeurs possibles : <ul> <li>Toutes les heures</li><li>Heures creuses</li><li>Heures pleines</li><li>Heures pleines jours bleus</li><li>Heures creuses jours bleus</li><li>Heures pleines jours rouges</li><li>Heures creuses jours rouges</li><li>Heures pleines jours blancs</li><li>Heures creuses jours blancs</li><li>Heures normales</li><li>Heures de pointe mobile</li> </ul>")
	public String periodeTarifaireEnCours;
	@FieldDescription(description = " ")
	public String couleurLendemain;
	@FieldDescription(description = "Intensité instantanée")
	public IntensiteInstant intensiteInstant;
	@FieldDescription(description = "Intensité instantanée phase 1")
	public IntensiteInstant intensiteInstantPhase1;
	@FieldDescription(description = "Intensité instantanée phase 2")
	public IntensiteInstant intensiteInstantPhase2;
	@FieldDescription(description = "Intensité instantanée phase 3")
	public IntensiteInstant intensiteInstantPhase3;
	@FieldDescription(description = "Avertissement dépass puiss sous")
	public AvertDepassPuissSous avertDepassPuissSous;	
	@FieldDescription(description = "Intensité maximum appelée")
	public IntensiteMaxAppelee intensiteMaxAppelee;
	@FieldDescription(description = "Intensité maximum appelée phase 1")
	public IntensiteMaxAppelee intensiteMaxAppeleeP1;
	@FieldDescription(description = "Intensité maximum appelée phase 2")
	public IntensiteMaxAppelee intensiteMaxAppeleeP2;
	@FieldDescription(description = "Intensité maximum appelée phase 3")
	public IntensiteMaxAppelee intensiteMaxAppeleeP3;
	@FieldDescription(description = "Puissance maximum")
	public PuissanceMax puissanceMax;
	@FieldDescription(description = "Puissance apparente")
	public PuissanceApp puissanceApp;
	@FieldDescription(description = "Horaire heure pleine/creuse")
	public String horaireHeurePleineCreuse;
	@FieldDescription(description = "Mot d'état du compteur")
	public String motEtatCompteur;
	@FieldDescription(description = "Présence des potentiels")
	public String presencePotentiels;
	
	@Data
    @OutputPojo
	public class AvertDepassI {
		@FieldDescription(description = "A")
		public String unit;
		@FieldDescription(description = "Avertissement de dépassement I Phase 1")
		public Float phase1;
		@FieldDescription(description = "Avertissement de dépassement I Phase 2")
		public Float phase2;
		@FieldDescription(description = "Avertissement de dépassement I Phase 3")
		public Float phase3;
	}
	
	@Data
    @OutputPojo
    public class IntensiteSouscrite {
		@FieldDescription(description = "Valeur intensité souscrite")
		public Integer value;
		@FieldDescription(description = "A")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class IndexOptionBase {
		@FieldDescription(description = "Valeur index option base")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class IndexHeuresCreuses {
		@FieldDescription(description = "Valeur index heures creuses")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class IndexHeuresPleines {
		@FieldDescription(description = "Valeur index heures pleines")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class EjpHeuresNormales {
		@FieldDescription(description = "Valeur EJP heures normales")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class EjpHeuresPointeMobile {
		@FieldDescription(description = "Valeur EJP heures pointe mobile")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class TempoHeures {
		@FieldDescription(description = "Valeur")
		public Float value;
		@FieldDescription(description = "Wh")
		public String unit;
	}		
	
	@Data
    @OutputPojo
    public class PreavisEJP {
		@FieldDescription(description = "Valeur préavis EJP")
		public Float value;
		@FieldDescription(description = "Minutes")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class IndexGaz {
		@FieldDescription(description = "Valeur index gaz")
		public Float value;
		@FieldDescription(description = "dal")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class IndexTroisiemeCompteur {
		@FieldDescription(description = "Valeur index 3ème compteur")
		public Float value;
		@FieldDescription(description = "dal")
		public String unit;
	}
		
	@Data
    @OutputPojo
    public class IntensiteInstant {
		@FieldDescription(description = "Valeur")
		public Float value;
		@FieldDescription(description = "A")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class AvertDepassPuissSous {
		@FieldDescription(description = "Valeur avertissement dépassement puissance sous")
		public Float value;
		@FieldDescription(description = "A")
		public String unit;
	}
		
	@Data
    @OutputPojo
    public class IntensiteMaxAppelee {
		@FieldDescription(description = "Valeur")
		public Float value;
		@FieldDescription(description = "A")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class PuissanceMax {
		@FieldDescription(description = "Valeur puissance maximum")
		public Float value;
		@FieldDescription(description = "W")
		public String unit;
	}
	
	@Data
    @OutputPojo
    public class PuissanceApp {
		@FieldDescription(description = "Valeur puissance apparente")
		public Float value;
		@FieldDescription(description = "VA")
		public String unit;
	}	
	
}
