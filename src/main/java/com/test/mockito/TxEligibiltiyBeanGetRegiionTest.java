package com.maximus.sdc.webapp.eb.eligibilityTest;

import static org.junit.Assert.assertEquals;
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
import com.maximus.sdc.bizservices.eb.countyserviceareamapping.CountyServiceAreaMappingService;
import com.maximus.sdc.bizservices.eb.eligibility.EligibilityService;
import com.maximus.sdc.bizservices.eb.enrollment.utils.EnrollmentUtil;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.model.Address;
import com.maximus.sdc.eb.dm.model.Case;
import com.maximus.sdc.eb.dm.model.CaseClient;
import com.maximus.sdc.eb.dm.model.ClientEligStatus;
import com.maximus.sdc.webapp.eb.eligibility.EligibilityUtil;
import com.maximus.sdc.webapp.eb.eligibility.TxEligibilityBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EligibilityUtil.class, WebUserContextHelper.class })
public class TxEligibiltiyBeanGetRegiionTest {

	Case caseOboject = new Case();
	CaseClient cc = mock(CaseClient.class);
	Long clientId = new Long(10);
	ClientEligStatus eligStatus = mock(ClientEligStatus.class);
	
	EligibilityService service = mock(EligibilityService.class);
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(EligibilityUtil.class);
		PowerMockito.mockStatic(WebUserContextHelper.class);
	}
     
	@Test
	public void givenCasewhenNullThenExpectRegionIsEmpty() {
		when(EligibilityUtil.getSessionCase()).thenReturn(null);
		
		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		
		
		String region = classUnderTest.getRegion();
		Assert.assertEquals("region disply not null", "", region);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void givenCaseObjectNotNullThenExpectRegion() {
		EnrollmentUtil enrollmentUtil = mock(EnrollmentUtil.class);
		CountyServiceAreaMappingService mappingService = mock(CountyServiceAreaMappingService.class);
		
		when(EligibilityUtil.getSessionCase()).thenReturn(caseOboject);
		when(WebUserContextHelper.getUserContext()).thenReturn(new UserContext());
		when(enrollmentUtil.extractCaseClient(caseOboject, clientId)).thenReturn(cc);
		
		when(cc.getProgramCd()).thenReturn("MEDICAID");
		when(
			service.getCurrentEligibilityStatusesForClients(isA(UserContext.class), isA(List.class), isA(String.class), isA(String.class)))
			.thenReturn(Arrays.asList(eligStatus));
		caseOboject.setClientId(clientId);
		when(eligStatus.getSubprogramType()).thenReturn("subProgram");
		when(mappingService.getServiceAreaForProgramPlanSubProgramCountCd("county", "MEDICAID", "MEDICAL", "subProgram")).thenReturn(Arrays.asList("districtCd"));
		
		Address address = new Address();
		address.setCountyCd("county");
		when(EligibilityUtil.getSessionCaseAddress()).thenReturn(address);
		
		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		classUnderTest.setSelectedPlanType("MEDICAL");
		classUnderTest.setEligibilityService(service);
		classUnderTest.setCountyServiceAreaMappingService(mappingService);
		
		String region = classUnderTest.getRegion();
		assertEquals("districtCd", region);
	}

}
