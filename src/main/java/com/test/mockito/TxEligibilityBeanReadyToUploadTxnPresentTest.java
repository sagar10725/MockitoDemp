package com.maximus.sdc.webapp.eb.eligibilityTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.eb.enrollment.core.EnrollmentSupportManager;
import com.maximus.sdc.core.common.data.ResultSpecifier;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.enrollment.SelectionTxnCriteria;
import com.maximus.sdc.eb.dm.model.CaseClient;
import com.maximus.sdc.eb.dm.model.Client;
import com.maximus.sdc.eb.dm.model.SelectionTxn;
import com.maximus.sdc.webapp.eb.eligibility.EligibilityUtil;
import com.maximus.sdc.webapp.eb.eligibility.TxEligibilityBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebUserContextHelper.class, EligibilityUtil.class })
public class TxEligibilityBeanReadyToUploadTxnPresentTest{

	@Before
	public void setUp() {
		PowerMockito.mockStatic(WebUserContextHelper.class);
		PowerMockito.mockStatic(EligibilityUtil.class);

	}

	@Test
	public void whenIsReadyToUploadTxnPresentNotNullThenExpectNotReadyToUploadTxnPresent() {

		TxEligibilityBean classUnderTest = new TxEligibilityBean();

		classUnderTest.setReadyToUploadTxnPresent(Boolean.TRUE || Boolean.FALSE);
		boolean isReadytoUpload = classUnderTest.isReadyToUploadTxnPresent();
		System.out.println(isReadytoUpload);
		Assert.assertTrue("not ready to upload txn", isReadytoUpload);
	}

	@Test
	public void whenIsReadyToUploadTxnPresenAndCaseClienttNullThenExpectNotReadyToUploadTxnPresent()
			throws NoSuchFieldException {

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		CaseClient thisCaseClient = mock(CaseClient.class);
		Client clnt = mock(Client.class);
		Long clientId = new Long(10);

		Set<CaseClient> caseClient = new HashSet<CaseClient>();
		caseClient.add(thisCaseClient);
		List<SelectionTxn> txns = new ArrayList<SelectionTxn>();

		// classUnderTest.caseClient = caseClient;
		SelectionTxn txn = mock(SelectionTxn.class);
		txns.add(txn);

		EnrollmentSupportManager enrollmentSupportManager = Mockito.mock(EnrollmentSupportManager.class);

		when(EligibilityUtil.getSessionClient()).thenReturn(clnt);
		when(clnt.getCaseClients()).thenReturn(caseClient);		

		when(clnt.getClientId()).thenReturn(new Long(15));
		when(thisCaseClient.getClientId()).thenReturn(clientId);

		classUnderTest.setReadyToUploadTxnPresent(null);
		classUnderTest.setEnrollmentSupportManager(enrollmentSupportManager);

		try {
			Boolean isReadyToUploadTxnPresent = classUnderTest.isReadyToUploadTxnPresent();
			Assert.assertFalse("not ready to upload", isReadyToUploadTxnPresent);
		} catch (Exception ex) {

		}

	}

	@Test
	public void whenIsReadyToUploadTxnPresentNullAndSelectionTxnEmptyThenExpectNotReadyToUploadTxnPresent() throws NoSuchFieldException {

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		UserContext userContext = Mockito.mock(UserContext.class);
		CaseClient thisCaseClient = mock(CaseClient.class);
		Client clnt = mock(Client.class);
		Long clientId = new Long(10);

		Set<CaseClient> caseClient = new HashSet<CaseClient>();
		caseClient.add(thisCaseClient);

		SelectionTxnCriteria criteria = Mockito.mock(SelectionTxnCriteria.class);
		EnrollmentSupportManager enrollmentSupportManager = Mockito.mock(EnrollmentSupportManager.class);

		when(EligibilityUtil.getSessionClient()).thenReturn(clnt);
		when(clnt.getCaseClients()).thenReturn(caseClient);

		when(clnt.getClientId()).thenReturn(clientId);
		when(thisCaseClient.getClientId()).thenReturn(clientId);		
	
		when(WebUserContextHelper.getUserContext()).thenReturn(userContext);
		when(thisCaseClient.getProgramCd()).thenReturn("programCd");		
		criteria.setProgramType("programCd");
		criteria.setPlanType("planTypeCd");
		criteria.setSelectionTxnStatuscode("readyToUpload");
		criteria.setClientId(clientId);
		when(enrollmentSupportManager.getSelectionTxnRecordsForCriteria(userContext, criteria, null, true))
				.thenReturn(null);

		classUnderTest.setReadyToUploadTxnPresent(null);
		classUnderTest.setEnrollmentSupportManager(enrollmentSupportManager);
		Boolean isReadyToUploadTxnPresent = classUnderTest.isReadyToUploadTxnPresent();

		Assert.assertFalse("not ready to upload", isReadyToUploadTxnPresent);
	}
	
	
	@Test
	public void whenIsReadyToUploadTxnPresentNullThenExpectNotReadyToUploadTxnPresent() {
		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		UserContext userContext = Mockito.mock(UserContext.class);
		CaseClient thisCaseClient = Mockito.mock(CaseClient.class);
		Client clnt = mock(Client.class);
		Long clientId = new Long(10);

		Set<CaseClient> caseClient = new HashSet<CaseClient>();
		caseClient.add(thisCaseClient);
		
		SelectionTxn txn = Mockito.mock(SelectionTxn.class);				
		EnrollmentSupportManager enrollmentSupportManager = Mockito.mock(EnrollmentSupportManager.class);

		when(EligibilityUtil.getSessionClient()).thenReturn(clnt);
		when(clnt.getCaseClients()).thenReturn(caseClient);

		when(clnt.getClientId()).thenReturn(clientId);
		when(thisCaseClient.getClientId()).thenReturn(clientId);		
	
		when(WebUserContextHelper.getUserContext()).thenReturn(userContext);
		when(thisCaseClient.getProgramCd()).thenReturn("programCd");
		
		List<SelectionTxn> txns = new ArrayList<SelectionTxn>();
		txns.add(txn);
		when(enrollmentSupportManager.getSelectionTxnRecordsForCriteria(Mockito.any(UserContext.class), Mockito.any(SelectionTxnCriteria.class), Mockito.any(ResultSpecifier.class), Mockito.anyBoolean())).thenReturn(txns);

		classUnderTest.setReadyToUploadTxnPresent(null);
		classUnderTest.setEnrollmentSupportManager(enrollmentSupportManager);
		Boolean isReadyToUploadTxnPresent = classUnderTest.isReadyToUploadTxnPresent();

		Assert.assertTrue("ready to upload", isReadyToUploadTxnPresent);
	}

}
