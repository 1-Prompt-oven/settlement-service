package com.promptoven.settlementservice.application.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KoreaTaxRateTable {

	@Value("${korea-tax.regional}")
	public double koreaRegionalTaxRate;

	@Value("${korea-tax.individual.other-business-border}")
	public long koreaIndividualBusinessBorder;
	@Value("${korea-tax.individual.business-rate}")
	public double koreaIndividualBusinessRate;
	@Value("${korea-tax.individual.other-rate}")
	public double koreaIndividualOtherRate;
	@Value("${korea-tax.individual.other-ignore-border}")
	public long koreaOtherIgnoreBorder;

	@Value("${korea-tax.cooperation.level0.border}")
	public long koreaCooperationLevel0Border;
	@Value("${korea-tax.cooperation.level0.rate}")
	public double koreaCooperationLevel0Rate;
	@Value("${korea-tax.cooperation.level0.exclude}")
	public long koreaCooperationLevel0Exclude;
	@Value("${korea-tax.cooperation.level0.include}")
	public long koreaCooperationLevel0Include;

	@Value("${korea-tax.cooperation.level1.border}")
	public long koreaCooperationLevel1Border;
	@Value("${korea-tax.cooperation.level1.rate}")
	public double koreaCooperationLevel1Rate;
	@Value("${korea-tax.cooperation.level1.exclude}")
	public long koreaCooperationLevel1Exclude;
	@Value("${korea-tax.cooperation.level1.include}")
	public long koreaCooperationLevel1Include;

	@Value("${korea-tax.cooperation.level2.border}")
	public long koreaCooperationLevel2Border;
	@Value("${korea-tax.cooperation.level2.rate}")
	public double koreaCooperationLevel2Rate;
	@Value("${korea-tax.cooperation.level2.exclude}")
	public long koreaCooperationLevel2Exclude;
	@Value("${korea-tax.cooperation.level2.include}")
	public long koreaCooperationLevel2Include;

	@Value("${korea-tax.cooperation.level3.border}")
	public long koreaCooperationLevel3Border;
	@Value("${korea-tax.cooperation.level3.rate}")
	public double koreaCooperationLevel3Rate;
	@Value("${korea-tax.cooperation.level3.exclude}")
	public long koreaCooperationLevel3Exclude;
	@Value("${korea-tax.cooperation.level3.include}")
	public long koreaCooperationLevel3Include;
}
