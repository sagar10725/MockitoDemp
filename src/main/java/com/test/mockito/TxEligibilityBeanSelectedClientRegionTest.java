package com.maximus.sdc.webapp.eb.eligibilityTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.eb.address.AddressSearchCriteria;
import com.maximus.sdc.bizservices.eb.address.AddressService;
import com.maximus.sdc.bizservices.eb.countyserviceareamapping.CountyServiceAreaMappingService;
import com.maximus.sdc.bizservices.eb.eligibility.EligibilityService;
import com.maximus.sdc.bizservices.eb.enrollment.utils.EnrollmentUtil;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.model.Address;
import com.maximus.sdc.eb.dm.model.Case;
import com.maximus.sdc.eb.dm.model.CaseClient;
import com.maximus.sdc.eb.dm.model.Client;
import com.maximus.sdc.eb.dm.model.ClientEligStatus;
import com.maximus.sdc.webapp.eb.eligibility.EligibilityUtil;
import com.maximus.sdc.webapp.eb.eligibility.TxEligibilityBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebUserContextHelper.class, EligibilityUtil.class })
public class TxEligibilityBeanSelectedClientRegionTest {

	Case caseObject = Mockito.mock(Case.class);
	Client client = Mockito.mock(Client.class);

	@Before
	public void setUp() {
		PowerMockito.mockStatic(WebUserContextHelper.class);
		PowerMockito.mockStatic(EligibilityUtil.class);
	}

	@Test
	public void givenClientNullThenExpectNoClientRegion() {

		when(EligibilityUtil.getSessionCase()).thenReturn(null);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();
		String clientRegion = classUnderTest.getSelectedClientRegion();

		Assert.assertEquals("ClientRegion is not display", "", clientRegion);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenClientNotNullAddressNullThenExpectNOClientRegion() {

		Long hohClientId = new Long(15);
		EnrollmentUtil enrollmentUtil = Mockito.mock(EnrollmentUtil.class);
		CaseClient cc = Mockito.mock(CaseClient.class);
		ClientEligStatus eligStatus = Mockito.mock(ClientEligStatus.class);
		EligibilityService eligibilityService = Mockito.mock(EligibilityService.class);
		AddressService addressService = Mockito.mock(AddressService.class);
		Address address = Mockito.mock(Address.class);

		CountyServiceAreaMappingService areaMappingService = Mockito.mock(CountyServiceAreaMappingService.class);

		when(EligibilityUtil.getSessionCase()).thenReturn(caseObject);
		when(caseObject.getClientId()).thenReturn(hohClientId);
		when(EligibilityUtil.getSessionClient()).thenReturn(client);
		when(WebUserContextHelper.getUserContext()).thenReturn(new UserContext());
		when(enrollmentUtil.extractCaseClient(caseObject, hohClientId)).thenReturn(cc);
		when(cc.getProgramCd()).thenReturn("MEDICAID");
		when(eligibilityService.getCurrentEligibilityStatusesForClients(isA(UserContext.class), isA(List.class),
				isA(String.class))).thenReturn(Arrays.asList(eligStatus));
		caseObject.setClientId(hohClientId);
		caseObject.setCaseId(new Long(15));
		when(eligStatus.getSubprogramType()).thenReturn("subProgramType");

		when(addressService.getAddresses(isA(UserContext.class), isA(AddressSearchCriteria.class))).thenReturn(null);
		when(address.getTypeCd()).thenReturn("RS");

		TxEligibilityBean classUnderTest = new TxEligibilityBean();

		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		classUnderTest.setSelectedPlanType("MEDICAL");
		classUnderTest.setEligibilityService(eligibilityService);
		classUnderTest.setCountyServiceAreaMappingService(areaMappingService);
		classUnderTest.setAddressService(addressService);
		String region = classUnderTest.getSelectedClientRegion();
		assertEquals("ClientRegion is not display", "", region);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenClientAndAddressNoNullThenExpectNOClientRegion() {

		Long hohClientId = new Long(15);
		Long caseId = new Long(15);
		EnrollmentUtil enrollmentUtil = Mockito.mock(EnrollmentUtil.class);
		CaseClient cc = Mockito.mock(CaseClient.class);
		ClientEligStatus eligStatus = Mockito.mock(ClientEligStatus.class);
		EligibilityService eligibilityService = Mockito.mock(EligibilityService.class);
		AddressService addressService = Mockito.mock(AddressService.class);
		Address address = Mockito.mock(Address.class);

		// AddressSearchCriteria searchCriteria =
		// Mockito.mock(AddressSearchCriteria.class);

		CountyServiceAreaMappingService areaMappingService = Mockito.mock(CountyServiceAreaMappingService.class);

		when(EligibilityUtil.getSessionCase()).thenReturn(caseObject);
		when(caseObject.getClientId()).thenReturn(hohClientId);
		when(caseObject.getCaseId()).thenReturn(caseId);
		when(EligibilityUtil.getSessionClient()).thenReturn(client);
		when(WebUserContextHelper.getUserContext()).thenReturn(new UserContext());
		when(enrollmentUtil.extractCaseClient(caseObject, hohClientId)).thenReturn(cc);
		when(cc.getProgramCd()).thenReturn("MEDICAID");
		when(eligibilityService.getCurrentEligibilityStatusesForClients(isA(UserContext.class), isA(List.class),
				isA(String.class), isA(String.class))).thenReturn(Arrays.asList(eligStatus));
		caseObject.setClientId(hohClientId);
		caseObject.setCaseId(caseId);
		when(eligStatus.getSubprogramType()).thenReturn("subProgramType");

		when(addressService.getAddresses(isA(UserContext.class), isA(AddressSearchCriteria.class))).thenReturn(Arrays.asList(address));
		when(address.getTypeCd()).thenReturn("RS");
		when(address.getCountyCd()).thenReturn("countyCd");
		when(areaMappingService.getServiceAreaForProgramPlanSubProgramCountCd("countyCd", "MEDICAID", "MEDICAL", "subProgramType")).thenReturn(Arrays.asList("DistrictCds"));

		// when(EligibilityUtil.getSessionCaseAddress()).thenReturn(address);

		TxEligibilityBean classUnderTest = new TxEligibilityBean();

		classUnderTest.setEnrollmentUtil(enrollmentUtil);
		classUnderTest.setSelectedPlanType("MEDICAL");
		classUnderTest.setEligibilityService(eligibilityService);
		classUnderTest.setCountyServiceAreaMappingService(areaMappingService);
		classUnderTest.setAddressService(addressService);

		String region = classUnderTest.getSelectedClientRegion();
		assertEquals("DistrictCds", region);

	}

}
