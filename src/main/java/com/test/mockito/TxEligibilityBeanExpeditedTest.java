package com.maximus.sdc.webapp.eb.eligibilityTest;

import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.eb.tx.costshare.CostShareDetailsSearchCriteria;
import com.maximus.sdc.bizservices.eb.tx.costshare.CostshareDetailsService;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.model.Case;
import com.maximus.sdc.eb.dm.model.CaseClient;
import com.maximus.sdc.eb.dm.model.CostshareDetails;
import com.maximus.sdc.webapp.eb.eligibility.EligibilityUtil;
import com.maximus.sdc.webapp.eb.eligibility.TxEligibilityBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EligibilityUtil.class, WebUserContextHelper.class })
public class TxEligibilityBeanExpeditedTest {

	@Mock
	private Case selectedCase;

	@Mock
	private CaseClient client;

	@Before
	public void setUp() {
		PowerMockito.mockStatic(EligibilityUtil.class);
		PowerMockito.mockStatic(WebUserContextHelper.class);
	}

	@Test
	public void givenCaseClientsWhenEmptyThenExpectFalseInExpedited() {
		// Case selectedCase = mock(Case.class);

		Set<CaseClient> caseClients = new HashSet<CaseClient>();

		when(EligibilityUtil.getSessionCase()).thenReturn(selectedCase);
		when(selectedCase.getCaseClients()).thenReturn(caseClients);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();

		boolean actualValue = classUnderTest.isExpedited();
		Assert.assertFalse("Expedited for give empty case Client", actualValue);
	}

	@Test
	public void givenWhenProgramCdIsNOTCHIPThenExpectFalseInExpedited() {
		// Case selectedCase = mock(Case.class);
		// CaseClient client = mock(CaseClient.class);

		Set<CaseClient> caseClients = new HashSet<CaseClient>();
		caseClients.add(client);

		when(EligibilityUtil.getSessionCase()).thenReturn(selectedCase);
		when(selectedCase.getCaseClients()).thenReturn(caseClients);
		when(client.getProgramCd()).thenReturn("NOTCHIP");

		TxEligibilityBean classUnderTest = new TxEligibilityBean();

		boolean actualValue = classUnderTest.isExpedited();
		Assert.assertFalse("When program tyi is not CHIP : ", actualValue);
	}

	@Test
	public void givenAllThenExpectTrueInExpedited() {
		// Case selectedCase = mock(Case.class);
		// CaseClient client = mock(CaseClient.class);
		CostshareDetails csDetails = mock(CostshareDetails.class);
		CostshareDetailsService CostShareDetailsservice = mock(CostshareDetailsService.class);

		Set<CaseClient> caseClients = new HashSet<CaseClient>();
		caseClients.add(client);

		List<CostshareDetails> costShareDetails = new ArrayList<CostshareDetails>();
		costShareDetails.add(csDetails);

		Calendar gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_MONTH, 90);

		when(WebUserContextHelper.getUserContext()).thenReturn(null);
		when(EligibilityUtil.getSessionCase()).thenReturn(selectedCase);
		when(selectedCase.getCaseClients()).thenReturn(caseClients);
		when(client.getProgramCd()).thenReturn("CHIP");
		when(selectedCase.getCaseGenericField6Txt()).thenReturn("TA84");
		when(selectedCase.getCaseId()).thenReturn(new Long(10));
		when(CostShareDetailsservice.getCostShareDetails(isNull(UserContext.class), isNull(SqlMapExecutor.class),
				isA(CostShareDetailsSearchCriteria.class))).thenReturn(costShareDetails);
		when(csDetails.getFeeStatus()).thenReturn("NOTPAID");
		when(csDetails.getTiersExpeditedFlag()).thenReturn("Y");
		when(csDetails.getLpdDate()).thenReturn(gc.getTime());

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		classUnderTest.setCostshareDetailsService(CostShareDetailsservice);

		boolean actualValue = classUnderTest.isExpedited();
		Assert.assertTrue("CHIP should be expedited", actualValue);
	}
}
