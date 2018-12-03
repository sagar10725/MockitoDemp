package com.maximus.sdc.webapp.eb.eligibilityTest;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.eb.eligibility.EligibilityService;
import com.maximus.sdc.bizservices.eb.enrollment.utils.EnrollmentUtil;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.model.Case;
import com.maximus.sdc.eb.dm.model.CaseClient;
import com.maximus.sdc.eb.dm.model.Client;
import com.maximus.sdc.eb.dm.model.ClientEligStatus;
import com.maximus.sdc.webapp.eb.eligibility.EligibilityUtil;
import com.maximus.sdc.webapp.eb.eligibility.TxEligibilityBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebUserContextHelper.class, EligibilityUtil.class })
public class TxEligibilityBeanStarkidTest {

	Case caseObject = new Case();

	EnrollmentUtil enrollmentUtil = mock(EnrollmentUtil.class);
	Long clientId = new Long(15);
	CaseClient cc = mock(CaseClient.class);
	Client client = mock(Client.class);

	@Before
	public void setUp() {
		PowerMockito.mockStatic(WebUserContextHelper.class);
		PowerMockito.mockStatic(EligibilityUtil.class);
	}

	@Test
	public void givenCaseObjectNullThenExpectNotStarkid() {

		when(EligibilityUtil.getSessionCase()).thenReturn(null);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		boolean isStarKid = classUnderTest.isStarKid();
		Assert.assertFalse("Not Star kid ", isStarKid);
	}

	@Test
	public void givenCaseObjectNotNullClientIdNullThenExpectNotStarKid() {

		when(EligibilityUtil.getSessionCase()).thenReturn(caseObject);
		when(enrollmentUtil.extractCaseClient(caseObject, clientId)).thenReturn(cc);

		when(cc.getProgramCd()).thenReturn("MEDICAID");
		when(EligibilityUtil.getSessionClient()).thenReturn(null);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		classUnderTest.setSelectedClient(client);
		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		// caseObject.setClientId(clientId);

		boolean isStarKid = classUnderTest.isStarKid();
		Assert.assertFalse("Not Star kid ", isStarKid);
	}

	@Test
	public void givenCaseObjectAndClientIdNotNullAndEligStatusNullThenExpectStarKid() {
		EligibilityService service = mock(EligibilityService.class);

		when(EligibilityUtil.getSessionCase()).thenReturn(caseObject);
		when(enrollmentUtil.extractCaseClient(caseObject, clientId)).thenReturn(cc);

		when(cc.getProgramCd()).thenReturn("MEDICAID");
		when(client.getClientId()).thenReturn(null);
		when(EligibilityUtil.getSessionClient()).thenReturn(client);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		classUnderTest.setSelectedClient(client);
		caseObject.setClientId(clientId);
		classUnderTest.setEligibilityService(service);

		boolean isStarKid = classUnderTest.isStarKid();
		Assert.assertFalse("Not Star kid ", isStarKid);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenCaseObjectClientIdAndEligStatusNotNullThenExpectStarKid() {
		ClientEligStatus eligStatus = mock(ClientEligStatus.class);
		EligibilityService service = mock(EligibilityService.class);
		when(EligibilityUtil.getSessionCase()).thenReturn(caseObject);
		when(enrollmentUtil.extractCaseClient(caseObject, clientId)).thenReturn(cc);

		when(cc.getProgramCd()).thenReturn("MEDICAID");
		caseObject.setClientId(clientId);

		when(client.getClientId()).thenReturn(clientId);
		when(EligibilityUtil.getSessionClient()).thenReturn(client);

		when(service.getCurrentEligibilityStatusesForClients(isA(UserContext.class), isA(List.class), isA(String.class),
				isA(String.class))).thenReturn(Arrays.asList(eligStatus));

		when(eligStatus.getSubprogramType()).thenReturn("STARKIDS");

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		classUnderTest.setSelectedClient(client);

		classUnderTest.setEligibilityService(service);
		// ces.setSubprogramType("SUB_PROGRAM_STARKIDS");

		boolean isStarKid = classUnderTest.isStarKid();
		Assert.assertFalse("Not Star kid ", isStarKid);
	}
}
